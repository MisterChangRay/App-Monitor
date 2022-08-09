<template>
  <d2-container>
    <el-card>
      <d2-crud
        ref="d2Crud"
        :columns="columns"
        :data="data"
        :rowHandle="rowHandle"
        add-title="添加服务器"
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
  import * as monitorApi from '@/api/monitorApi.js'

  export default {
    data () {
      return {
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
            key: 'ip'
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
          },
          port: {
            title: 'SSH端口',
            value: '22',
          },
          username: {
            title: 'SSH账号',
            value: '',
          },
          password: {
            title: 'SSH密码',
            value: '',
          },
          remark: {
            title: '备注',
            value: '',
          },
        },
        formOptions: {
          labelWidth: '80px',
          labelPosition: 'left',
          saveLoading: false
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
        this.$message({
          message: '打开模态框，模式为：' + mode,
          type: 'success'
        })
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

        monitorApi.CLIENT_INFO_EDIT(row).then(res => {
          self.$message({
            message: '修改成功',
            type: 'success'
          });
          self.formOptions.saveLoading = false
          self.fetchData();
          done()
        })


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

        monitorApi.CLIENT_INFO_ADD(row).then(res => {
          self.$message({
            message: '保存成功',
            type: 'success'
          });
          self.formOptions.saveLoading = false
          self.fetchData();
          done()
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
          this.data = res.list
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
