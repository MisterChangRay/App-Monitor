<template>
  <d2-container>
    <el-card>
      <d2-crud
        ref="d2Crud"
        :columns="columns"
        :data="data"
        :rowHandle="rowHandle"
        add-title="添加服务器"
        :add-rules="addRules"
        :edit-rules="addRules"
        :add-template="addTemplate"
        :form-options="formOptions"
        :pagination="pagination"
        :loading="loading"
        :edit-template="addTemplate"
        @row-remove="handleRowRemove"
        @row-edit="handleRowEdit"
        @pagination-current-change="paginationCurrentChange"
        @dialog-open="handleDialogOpen"
        @row-add="handleRowAdd"
        @dialog-cancel="handleDialogCancel">
        <el-button slot="header" style="margin-bottom: 5px" @click="addRow"><i class="fa fa-plus" aria-hidden="true"></i> 新增</el-button>
        <el-input slot="header" style="margin-bottom: 5px" v-model="query.remark" placeholder="服务器备注" > </el-input>
        <el-button slot="header" style="margin-bottom: 5px" @click="fetchData"><i class="el-icon-search"></i> 搜索</el-button>
      </d2-crud>
    </el-card>
  </d2-container>
</template>

<script>
  import serverSSHTest from '@/views/console/component/serverSshTest.vue'

  import * as monitorApi from '@/api/monitorApi.js'

  export default {
    components: {
      serverSSHTest
    },
    data () {
      return {
        addRules: {
          ip: [{ required: true, message: '必填', trigger: 'blur' }],
        },
        query: {
          remark: ''
        },
        rowHandle: {
          edit: {
            icon: 'el-icon-edit',
            text: '编辑',
            size: 'small',
            show (index, row) {
              if (row.showEditButton) {
                return true
              }
              return false
            },
            disabled (index, row) {
              if (row.forbidEdit) {
                return true
              }
              return false
            }
          },
          remove: {
            icon: 'el-icon-delete',
            size: 'small',
            fixed: 'right',
            confirm: true,
            show (index, row) {
              if (row.showRemoveButton) {
                return true
              }
              return false
            },
            disabled (index, row) {
              if (row.forbidRemove) {
                return true
              }
              return false
            }
          }
        },
        columns: [
          {
            title: '服务器Ip',
            key: 'ip',
          },
          {
            title: '服务器备注',
            key: 'remark'
          },
          {
            title: '状态',
            key: 'status',
            formatter: this.statusFormatter
          },
          {
            title: '创建时间',
            key: 'createTime',
            formatter: this.columnDateFormatter
          },
          {
            title: '最新一次通讯',
            key: 'reportTime',
            formatter: this.columnDateFormatter
          }
        ],
        data: [],
        addTemplate: {
          ip: {
            title: '服务器Ip',
            value: '',
            component: {
              span: 11,
              placeholder: "多个逗号分隔"
            }
          },
          port: {
            title: 'SSH端口',
            value: '22',
            component: {
              span: 11,
              placeholder: "使用SSH启动必填"
            }
          },
          username: {
            title: 'SSH账号',
            value: '',
            component: {
              span: 11,
              placeholder: "使用SSH启动必填"
            }
          },
          password: {
            title: 'SSH密码',
            value: '',
            component: {
              span: 11,
              placeholder: "使用SSH启动必填"
            }
          },
          remark: {
            title: '备注',
            value: '',
          component: {
            span: 11,
            placeholder: ""
          }
          },
        },
        formOptions: {
          labelWidth: '80px',
          labelPosition: 'left',
          saveLoading: false,
          gutter: 50,
        },
        loading: false,
        pagination: {
          currentPage: 1,
          pageSize: 5,
          total: 100
        }
      }
    },
    mounted () {
      this.fetchData()
    },
    methods: {
      isOnline(reportTime) {
        // 上报时间在10分钟内算在线
        var min10 = 10 * 60 * 1000;
        if(reportTime) {
           var diff = new Date().getTime() - new Date(reportTime).getTime()
           return diff > min10 ? false : true;
        }
        return false;
      },
      statusFormatter (row, column, cellValue, index) {
          if(cellValue == '1') {
            return "未激活"
          }
          if(cellValue == '2') {
            if(row.reportTime ) {
              return  this.isOnline(row.reportTime) ? "在线" : "离线"
            }
          }
          return "未知"
      },
      handleDialogOpen ({ mode }) {

      },
      // 普通的新增
      addRow () {
        this.$refs.d2Crud.showDialog({
          mode: 'add'
        })
      },
      handleRowEdit (row, done) {
        row = row.row;
        this.formOptions.saveLoading = true
        let self = this;


        monitorApi.SERVER_SSH_TEST(row).then(res => {

          if(res.code == 0) {
            monitorApi.CLIENT_INFO_EDIT(row).then(res => {
              self.$message({
                message: '修改成功',
                type: 'success'
              });
              self.formOptions.saveLoading = false
              self.fetchData();
              done()
            })
          } else {
            self.$message({
              message: 'SSH链接测试失败,' + res.msg ,
              type: 'error'
            });
            self.formOptions.saveLoading = false
          }
        });

      },
      handleRowRemove ({ index, row }, done) {
        this.formOptions.saveLoading = true
        let self = this;

        monitorApi.CLIENT_INFO_DEL(row).then(res => {
          self.$message({
            message: '删除成功',
            type: 'success'
          });
          self.formOptions.saveLoading = false
          self.fetchData();
          done()
        })
      },
      handleRowAdd (row, done) {
        this.formOptions.saveLoading = true
        let self = this;

        monitorApi.SERVER_SSH_TEST(row).then(res => {

          if(res.code == 0) {
            monitorApi.CLIENT_INFO_ADD(row).then(res => {
              self.$message({
                message: '保存成功',
                type: 'success'
              });
              self.formOptions.saveLoading = false
              self.fetchData();
              done()
            })
          } else {
            self.$message({
              message: 'SSH链接测试失败,' + res.msg ,
              type: 'error'
            });
            this.formOptions.saveLoading = false
          }

        })




      },
      handleDialogCancel (done) {
        this.formOptions.saveLoading = false
        this.$message({
          message: '取消保存',
          type: 'warning'
        });
        done()
      },
      paginationCurrentChange (currentPage) {
        this.pagination.currentPage = currentPage
        this.fetchData()
      },
      fetchData () {
        this.loading = true

        monitorApi.CLIENT_INFO_LIST({remark: this.query.remark}).then(res => {

          this.data = res.data.list
          this.data.forEach(tmp => {
              tmp.showRemoveButton= true;
              tmp.showEditButton = true;
              // 有通讯则不可删除
              if(tmp.reportTime) {
                tmp.forbidRemove= false;
              }
          })
          this.pagination.total = res.total
          this.loading = false
        })

      }
    }
  }
</script>

<style scoped>
  .el-input {
    width: 200px;
    margin-right: 10px;
  }
</style>
