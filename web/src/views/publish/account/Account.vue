<template>
  <div>
    <Add />
    <a-divider />
    <Result />
  </div>
</template>

<script>
import Add from "./Add";
import Result from "./Result";
export default {
  name: "Account",
  components: { Add, Result }
};
</script>

<style lang="less" scoped></style>
