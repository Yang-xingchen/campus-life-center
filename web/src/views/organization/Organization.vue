<template>
  <div class="box">
    <div class="content">
      <Info :id="id" />
      <a-divider />
      <div class="body">
        <div class="child"><Child :id="id" /></div>
        <div class="right">
          <div class="operation"></div>
          <div class="member"><Member :id="id" /></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Info from "./components/Info";
import Child from "./components/Child";
import Member from "./components/Member";
export default {
  name: "Organization",
  components: { Info, Child, Member },
  data() {
    return {};
  },
  computed: {
    id() {
      return +this.$route.params.id;
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
    border-radius: 5px;
    background: #8882;
    .body {
      display: flex;
      height: 555px;
      .child {
        flex: 2;
        background: #8882;
        border-radius: 5px;
        margin-right: 20px;
      }
      .right {
        flex: 1;
        display: flex;
        flex-direction: column;
        .operation {
          flex: 1;
          background: #8882;
          border-radius: 5px;
          margin-bottom: 20px;
        }
        .member {
          flex: 3;
          background: #8882;
          border-radius: 5px;
        }
      }
    }
  }
}
</style>
