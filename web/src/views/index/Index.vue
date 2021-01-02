<template>
  <div class="index_body">
    <div class="index">
      <div class="title">{{ title }}</div>
      <div class="info">{{ info }}</div>
      <a class="creator" :href="creator"><a-icon type="github"/></a>
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
      creator: "https://github.com/Yang-xingchen/campus-life-center"
    };
  }
};
</script>

<style lang="less" scoped>
.index_body {
  display: flex;
  width: 100%;
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
      color: white;
      font-size: 28px;
      border: none;
    }
  }
}
</style>
