<template>
  <div>
    <div class="button_list"></div>
    <Sort />
    <div class="notice_info_list">
      <NoticeInfo v-for="notice in notices" :key="notice.id" :notice="notice" />
    </div>
  </div>
</template>

<script>
import Sort from "./Sort";
import NoticeInfo from "./NoticeInfo";
export default {
  name: "NoticeMain",
  components: { Sort, NoticeInfo },
  props: {
    notices: Array
  }
};
</script>

<style lang="less">
.button_list {
  height: 70px;
}
.notice_info_list {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  margin-bottom: 100px;
}
</style>
