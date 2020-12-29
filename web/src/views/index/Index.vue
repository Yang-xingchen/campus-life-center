<template>
  <div class="index_body">
    <div class="index">
      <div class="title">{{ title }}</div>
      <div class="info">{{ info }}</div>
      <div class="creator">create by: {{ creator }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      title: "通知管理中心",
      info: "基于微服务的校园通知管理系统",
      creator: "杨星辰"
    };
  }
};
</script>

<style lang="less" scoped>
.index_body {
  display: flex;
  width: 100%;
  height: calc(~"100vh - 65px");
  .index {
    width: 1000px;
    margin: auto;
    padding: 30px;
    border-radius: 5px;
    text-align: center;
    padding-bottom: 300px;
    .title {
      font-size: 120px;
      margin: 20px;
    }
    .info {
      font-size: 24px;
    }
    .creator {
      color: rgba(255, 255, 255, 0.5);
    }
  }
}
</style>
