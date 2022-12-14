<template>
  <d2-container>
    <el-card>
      <d2-crud
        ref="d2Crud"
        :columns="columns"
        :data="data"
        :rowHandle="rowHandle"
        add-title="添加应用"
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
        @form-data-change="formDataChange"
        @row-add="handleRowAdd"
        @custom-emit-detail="showDetail"
        @dialog-cancel="handleDialogCancel">
        <el-button slot="header" style="margin-bottom: 5px" @click="addRow"><i class="fa fa-plus" aria-hidden="true"></i> 新增</el-button>
        <el-input slot="header" style="margin-bottom: 5px" v-model="query.keyword" placeholder="应用名或备注" > </el-input>
        <el-select slot="header" v-model="query.group" clearable filterable placeholder="应用分组">
          <el-option
            v-for="item in groupOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-select slot="header" v-model="query.serverIp" clearable filterable placeholder="服务器">
          <el-option
            v-for="item in SERVER_LIST"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>

        <el-button slot="header" style="margin-bottom: 5px" @click="fetchData"><i class="el-icon-search"></i> 搜索</el-button>
      </d2-crud>
    </el-card>

    <el-dialog
      title="详情"
      :visible.sync="detailDialogVisible"
      width="30%">
      <el-descriptions border :column="2">
        <el-descriptions-item label="应用名称">{{ detailRow.projectName }}</el-descriptions-item>
        <el-descriptions-item label="应用分组">{{ detailRow.projectGroup }}</el-descriptions-item>
        <el-descriptions-item label="部署服务器">{{ detailRow.serverIp }}</el-descriptions-item>
        <el-descriptions-item label="应用端口">{{ detailRow.port }}</el-descriptions-item>
        <el-descriptions-item label="是否自动重启">{{ detailRow.autoRestart > 0 ? '是' : '否' }}</el-descriptions-item>
        <el-descriptions-item label="当前状态">{{ detailRow.status }}</el-descriptions-item>

        <el-descriptions-item label="备注" span="3">{{ detailRow.remark }}</el-descriptions-item>
        <el-descriptions-item label="测试链接" span="3">{{ detailRow.testUrl }}</el-descriptions-item>

        <el-descriptions-item label="应用启动命令" span="3">{{ detailRow.startCmd }}</el-descriptions-item>
        <el-descriptions-item label="应用部署路径" span="3">{{ detailRow.deployPath }}</el-descriptions-item>
        <el-descriptions-item label="应用部署文件名" span="3">{{ detailRow.deployFile }}</el-descriptions-item>
        <el-descriptions-item label="应用进程ID" span="3">{{detailRow.processId }}</el-descriptions-item>

        <el-descriptions-item label="创建时间">{{ columnDateFormatter(1,1,detailRow.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="修改时间">{{ columnDateFormatter(1,1,detailRow.updateTime) }}</el-descriptions-item>
        <el-descriptions-item label="最后通讯时间">{{ columnDateFormatter(1,1,detailRow.lastStartTime) }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </d2-container>
</template>

<script>
  import * as monitorApi from '@/api/monitorApi.js'
  import Vue from "vue";

  export default {
    data () {
      return {
        CLIENT_INFO_LIST : [],
        SERVER_LIST : [],
        detailRow: {},
        detailDialogVisible: false,
        groupOptions: [],
        query: {
          keyword: '',
          group:'',
          serverIp: ''
        },
        addRules: {
          projectName: [{ required: true, message: '必填', trigger: 'blur' }],
          serverIp: [{ required: true, message: '必填', trigger: 'blur' }],
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
          },
          custom: [
            {
              text: "详情",
              emit: "custom-emit-detail"
            }
          ]
        },
        columns: [
          {
            title: '应用名',
            key: 'projectName',
            width: '150'
          },
          {
            title: '应用分组',
            key: 'projectGroup',
            width: '100'
          },
          {
            title: '进程ID',
            key: 'processId',
            width: '80',
          },
          {
            title: '使用端口',
            key: 'port',
            width: '100'
          },
          {
            title: '状态',
            key: 'status',
            formatter: this.statusFormatter,
            width: '80'
          },
          {
            title: '服务器',
            key: 'serverIp',
            width: '150',
            formatter: this.columnIPFormatter
          },
          {
            title: '部署文件',
            key: 'deployFile',
          },
          {
            title: '最新一次通讯',
            key: 'updateTime',
            formatter: this.columnDateFormatter
          }
        ],
        data: [],
        addTemplate: {
          projectName: {
            title: '应用名称',
            value: '',
            component: {
              span: 11
            }
          },
          projectGroup: {
            title: '应用分组',
            value: '',
            component: {
              span: 11,
              placeholder: "定义分组,方便搜索"
            }
          },

          serverIp: {
            title: '应用服务器IP',
            value: '',
            component: {
              name: 'el-select',
              options: [],
              span: 11,
              placeholder: "请选择"
            }
          },
          commType: {
            title: '通讯方式',
            value: 1,
            component: {
              name: 'el-select',
              options: [{
                label: "客户端监听(需要部署Client端)",
                value: 1
              }, {
                  label: "SSH连接(服务器需配置账号密码)",
                  value: 2
                }],
              span: 11,
              placeholder: "请选择"
            }
          },
          scanType: {
            title: '存活检测',
            value: 1,
            component: {
              name: 'el-select',
              options: [
                {
                label: "端口检测",
                value: 1
              },
                {
                  label: "文件名检测",
                  value: 2
                },
                {
                  label: "自定义命令检测",
                  value: 3
                }],
              span: 11,
              placeholder: "请选择"
            }
          },
          port: {
            title: '应用端口',
            value: '',
            component: {
              span: 11,
              placeholder: "可选,通过端口检测必填,多个逗号分隔;如:7512,7513"
            }
          },
          remark: {
            title: '备注',
            value: '',
            component: {
              span: 11
            }
          },
          autoRestart: {
            title: '启动方式',
            value: 1,
            component: {
              name: 'el-select',
              options: [
                {
                  label: "自动重启(停止后立即重启)",
                  value: 1
                },
                {
                  label: "开机启动(仅开机启动一次)",
                  value: 2
                }],
              span: 11,
              placeholder: "请选择"
            }
          },
          deployPath: {
            title: '部署路径',
            value: '',
            component: {
              span: 11,
              placeholder: "可选, 通过文件名检测必填; ; 如: /user/local/service/"
            }
          },
          deployFile: {
            title: '部署文件名',
            value: '',
            component: {
              span: 11,
              placeholder: "可选, 通过文件名检测必填; 如: xxx.jar"
            }
          },
          testCmd: {
            title: '应用测试命令',
            value: '',
            component: {
              placeholder: "可选,通过此Shell命令测试应用存活状态,命令需要返回TRUE或FALSE;TRUE代表存活"
            }
          },
          startCmd: {
            title: '应用启动命令',
            value: '',
            component: {
              placeholder: "必填,启动使用的Shell命令,最好和路径无关; 如: java -jar /usr/local/service/xxx.jar 或者 ./startup.sh"
            }
          },

        },
        formOptions: {
          labelWidth: '110px',
          labelPosition: 'left',
          gutter: 30,
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
      this.init();
      this.fetchData()
    },
    methods: {
      statusFormatter (row, column, cellValue, index) {
        if(cellValue == '0') {
          return "未激活"
        }
        if(cellValue == '1') {
            return "在线"
        }
        return "离线"
      },
      columnIPFormatter (row, column, cellValue, index) {
        if(cellValue) {
          for (let i in this.CLIENT_INFO_LIST) {
            if(this.CLIENT_INFO_LIST[i].ip == cellValue) {
              return this.formatSelectLabel(this.CLIENT_INFO_LIST[i])
            }
          }
        }
      },
      // 表单事件联动
      formDataChange({ key, value }) {
        let self = this;
      },
      showDetail({index, row}) {
        this.detailDialogVisible = true;
        this.detailRow = row;
      },
      init() {
        let self = this;
        monitorApi.APP_INFO_GROUP_LIST({}).then(res => {
          self.groupOptions = [];
          res.data.list.forEach(tmp => {

            self.groupOptions.push({
              label: tmp,
              value: tmp
            })
          })

        })
        monitorApi.CLIENT_INFO_LIST({remark: ''}).then(res => {
          self.addTemplate.serverIp.component.options =[]

          res.data.list.forEach(tmp => {

            let t2 = {
              label: self.formatSelectLabel(tmp),
              value: tmp.ip
            };
            self.SERVER_LIST.push(t2)
            self.addTemplate.serverIp.component.options.push(t2)
          })
          self.CLIENT_INFO_LIST = res.data.list;
        })
      },
      formatSelectLabel(item) {
        if(item.remark) {
          return `${item.ip}(${item.remark})`
        }
        return `${item.ip}`
      },
      isOnline(reportTime) {
        // 上报时间在10分钟内算在线
        var min10 = 10 * 60 * 1000;
        if(reportTime) {
          var diff = new Date().getTime() - new Date(reportTime).getTime()
          return diff > min10 ? false : true;
        }
        return false;
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

        monitorApi.APP_INFO_EDIT(row).then(res => {
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

        monitorApi.APP_INFO_DEL(row).then(res => {
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

        monitorApi.APP_INFO_ADD(row).then(res => {
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

        monitorApi.APP_INFO_LIST(this.query).then(res => {
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
