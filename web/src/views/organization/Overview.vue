<template>
  <div>
    <div class="body">
      <div class="child"><Child :id="id" /></div>
      <div class="right">
        <div class="operation"></div>
        <div class="member"><Member :id="id" /></div>
      </div>
    </div>
  </div>
</template>

<script>
import Child from "./components/ChildInfo";
import Member from "./components/MemberInfo";
export default {
  name: "Overview",
  components: { Child, Member },
  computed: {
    id() {
      return +this.$route.params.id;
    }
  }
};
</script>

<style lang="less" scoped>
.body {
  display: flex;
  height: 505px;
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
</style>
