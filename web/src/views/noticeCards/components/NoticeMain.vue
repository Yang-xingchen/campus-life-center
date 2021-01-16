<template>
  <div>
    <a-button-group class="button_list">
      <a-button class="button" type="primary" @click="create"
        ><a-icon type="plus" />发布通知</a-button
      >
      <a-radio-group
        class="show_type button"
        defaultValue="card"
        button-style="solid"
      >
        <a-radio-button value="card"><a-icon type="appstore"/></a-radio-button>
        <a-radio-button value="list"><a-icon type="bars"/></a-radio-button>
      </a-radio-group>
    </a-button-group>
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
  },
  methods: {
    create() {
      this.$router.push(`/publish`);
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
  .button {
    align-self: center;
    padding: 0 10px;
    cursor: pointer;
  }
  .show_type {
    margin-left: auto;
  }
}
.notice_info_list {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  margin-bottom: 100px;
}
</style>
