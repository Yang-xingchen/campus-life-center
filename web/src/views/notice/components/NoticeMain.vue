<template>
  <div>
    <div class="button_list">
      <div class="add button">发布通知</div>
    </div>
    <Sort />
    <transition-group name="list" tag="div" class="notice_info_list">
      <NoticeInfo
        v-for="notice in notices"
        :key="notice.notice.id"
        :notice="notice"
        class="notice_info"
      />
    </transition-group>
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
.notice_info {
  transition: all 0.8s;
}
.list-enter,
.list-leave-to {
  opacity: 0;
}
.list-leave-active {
  position: absolute;
}
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
