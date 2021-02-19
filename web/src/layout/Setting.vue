<template>
  <div class="box">
    <div class="content">
      <div class="operation">
        <div :class="['item', select === 'base' ? 'select' : '']" @click="base">
          基础信息
        </div>
      </div>
      <router-view class="body" />
    </div>
  </div>
</template>

<script>
export default {
  name: "Setting",
  computed: {
    select() {
      let path = this.$route.path.split("setting")[1];
      if (path.startsWith("/")) {
        path = path.substring(1);
      }
      path = path.split("/")[0];
      return path || "base";
    }
  },
  methods: {
    base() {
      this.$router.push("/setting/base");
    }
  }
};
</script>

<style lang="less" scoped>
.box {
  display: flex;
  width: 100%;
  height: calc(~"100vh - 65px");
  .content {
    width: 1500px;
    height: 800px;
    margin: auto;
    padding: 15px 30px;
    display: flex;
    .operation {
      flex: 1;
      background: #8882;
      border-radius: 5px;
      margin-right: 10px;
      .item {
        background: #8882;
        font-size: 24px;
        padding: 5px 10px;
        cursor: pointer;
        &:hover {
          background: #8884;
        }
      }
      .select {
        background: #8884;
      }
    }
    .body {
      flex: 3;
    }
  }
}
</style>
