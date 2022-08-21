<template>
  <div style="cursor: pointer">
    asdfasfs
    <el-button @click="testssh"> 测试SSH </el-button>
  </div>
</template>

<script>
  import * as monitorApi from '@/api/monitorApi.js'

  export default {
    props: {
      value: {
        type: Boolean,
        required: true
      }
    },
    computed: {

    },
    methods: {
      testssh() {
        monitorApi.SERVER_SSH_TEST({
          username: this.scope.username,
          password: this.scope.password,
          ip: this.scope.ip,
          port: this.scope.port
        }).then(res => {
          console.log("============> res", res)
        })
      }

    }
  }
</script>
