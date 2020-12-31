<template>
  <div class="sort">
    <label class="sort_type">排序:</label>
  </div>
</template>

<script>
export default {
  name: "Sort"
};
</script>

<style lang="less">
.sort {
  height: 40px;
  border-top: red solid 1px;
  margin-bottom: 5px;
  .sort_type {
    font-size: 20px;
    line-height: 40px;
    margin-left: 10px;
  }
}
</style>
