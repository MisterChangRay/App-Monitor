<template>
  <d2-container type="card" class="page">
    <template slot="header">数字动画组件1</template>

  </d2-container>
</template>

<script>
  export default {
    data () {
      return {
        // 回调函数使用
        className: '',
        // 更新数值用
        end: 50
      }
    },
    methods: {
      update () {
        setTimeout(() => {
          this.end = 100
        }, 1000)
      }
    }
  }
</script>

<style lang="scss" scoped>
  .page {
    .group {
      display: flex;
      justify-content: center;
      align-items: center;
      span {
        font-size: 60px;
        &.end {
          padding: 0px 20px;
          border-radius: 4px;
          background-color: $color-success;
          color: #FFF;
        }
      }
    }
  }
</style>
