<template>
  <div>
    <div class="button_list">
      <div class="add button">发布通知</div>
    </div>
    <Sort />
    <transition-group name="list" tag="div" class="notice_info_list">
      <NoticeCard
        v-for="notice in sortNotice"
        :key="notice.id"
        :notice="notice"
        class="notice_info"
      />
    </transition-group>
  </div>
</template>

<script>
import Sort from "./Sort";
import NoticeCard from "./NoticeCard";
export default {
  name: "NoticeMain",
  components: { Sort, NoticeCard },
  props: {
    notices: Array
  },
  computed: {
    sortNotice() {
      return [
        ...this.notices
          .filter(n => n.top)
          .sort((a, b) => b.accountImportance - a.accountImportance),
        ...this.notices
          .filter(n => !n.top)
          .sort((a, b) => b.accountImportance - a.accountImportance)
      ];
    }
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
