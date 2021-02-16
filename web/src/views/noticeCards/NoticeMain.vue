<template>
  <div>
    <a-button-group class="button_list">
      <a-button class="button" type="primary" @click="create"
        ><a-icon type="plus" />发布通知</a-button
      >
      <a-button class="button" @click="waitList"
        ><a-icon type="clock-circle" />等待发布列表</a-button
      >
    </a-button-group>
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
import NoticeCard from "./NoticeCard";
export default {
  name: "NoticeMain",
  components: { NoticeCard },
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
  },
  methods: {
    create() {
      this.$router.push(`/publish`);
    },
    waitList() {
      this.$router.push(`/waitPublish`);
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
  padding: 0 10px;
  border-bottom: 1px solid red;
  margin-bottom: 20px;
  .button {
    align-self: center;
    padding: 0 10px;
    margin-right: 5px;
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
