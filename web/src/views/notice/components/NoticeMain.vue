<template>
  <div class="notice-main">
    <Sort />
    <NoticeInfo v-for="notice in notices" :key="notice.id" :notice="notice" />
  </div>
</template>

<script>
import Sort from "./Sort";
import NoticeInfo from "./NoticeInfo";
export default {
  name: "NoticeMain",
  components: [Sort, NoticeInfo],
  props: {
    notices: Array
  }
};
</script>

<style></style>
