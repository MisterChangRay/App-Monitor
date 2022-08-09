<template>
  <d2-container>
    <el-card>
      <d2-crud
        ref="d2Crud"
        :columns="columns"
        :data="data"
        :rowHandle="rowHandle"
        add-title="添加数据"
        :add-template="addTemplate"
        :form-options="formOptions"
        :pagination="pagination"
        :loading="loading"
        @pagination-current-change="paginationCurrentChange"
        @dialog-open="handleDialogOpen"
        @row-add="handleRowAdd"
        @dialog-cancel="handleDialogCancel">
        <el-button slot="header" style="margin-bottom: 5px" @click="addRow"><i class="fa fa-plus" aria-hidden="true"></i> 新增</el-button>
        <el-button slot="header" style="margin-bottom: 5px" @click="addRowWithNewTemplate">使用自定义模板新增</el-button>
        <el-input slot="header" style="margin-bottom: 5px" placeholder="商品名称" suffix-icon="el-icon-search"> </el-input>
        <el-input slot="header" style="margin-bottom: 5px" placeholder="最低价格" suffix-icon="el-icon-caret-bottom"> </el-input>
        <el-input slot="header" style="margin-bottom: 5px" placeholder="最高价格" suffix-icon="el-icon-caret-top"> </el-input>
        <el-button slot="header" style="margin-bottom: 5px"><i class="el-icon-search"></i> 搜索</el-button>
      </d2-crud>
    </el-card>
  </d2-container>
</template>

<script>
  export default {
    data () {
      return {
        rowHandle: {
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
            title: '日期',
            key: 'date'
          },
          {
            title: '姓名',
            key: 'name'
          },
          {
            title: '地址',
            key: 'address'
          }
        ],
        data: [
          {
            date: '2016-05-02',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄',
            showRemoveButton: true
          },
          {
            date: '2016-05-04',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1517 弄'
          },
          {
            date: '2016-05-01',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1519 弄'
          },
          {
            date: '2016-05-03',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1516 弄'
          }
        ],
        addTemplate: {
          date: {
            title: '日期',
            value: '2016-05-05'
          },
          name: {
            title: '姓名',
            value: '王小虎'
          },
          address: {
            title: '地址',
            value: '上海市普陀区金沙江路 1520 弄'
          }
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
      // 传入自定义模板的新增
      addRowWithNewTemplate () {
        this.$refs.d2Crud.showDialog({
          mode: 'add',
          template: {
            name: {
              title: '姓名',
              value: ''
            },
            value1: {
              title: '新属性1',
              value: ''
            },
            value2: {
              title: '新属性2',
              value: ''
            }
          }
        })
      },
      handleRowAdd (row, done) {
        this.formOptions.saveLoading = true
        setTimeout(() => {
          console.log(row)
          this.$message({
            message: '保存成功',
            type: 'success'
          });

          // done可以传入一个对象来修改提交的某个字段
          done({
            address: '我是通过done事件传入的数据！'
          })
          this.formOptions.saveLoading = false
        }, 300)
      },
      handleDialogCancel (done) {
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
        this.loading = false
        // BusinessTable1List({
        //   ...this.pagination
        // }).then(res => {
        //   this.data = res.list
        //   this.pagination.total = res.page.total
        //   this.loading = false
        // }).catch(err => {
        //   console.log('err', err)
        //
        // })
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
