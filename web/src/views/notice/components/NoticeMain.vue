<template>
  <div>
    <div class="button_list">
      <div class="add button">发布通知</div>
    </div>
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

<style lang="less" scope>
.button_list {
  height: 50px;
  display: flex;
  margin-top: 15px;
  .button {
    background: rgba(0, 255, 0, 0.2);
    padding: 5px 10px;
    margin: 10px;
    cursor: pointer;
  }
}
.notice_info_list {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  margin-bottom: 100px;
}
</style>
