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
    <div
      class="loading"
      v-show="total > loaded"
      :style="
        'background-image: linear-gradient(to right , #FC84 ' +
          (loaded / total) * 100 +
          '%, #0000 ' +
          (loaded / total) * 100 +
          '%);'
      "
    >
      <a-icon type="loading" />加载中 ({{ loaded }} /
      {{ total === maxNum ? "???" : total }})
    </div>
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
    notices: Array,
    total: Number,
    loaded: Number
  },
  data() {
    return {
      maxNum: ~(1 << 31)
    };
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
.loading {
  padding: 5px;
  margin: 5px 20px;
  border-radius: 5px;
  border: 1px #888 solid;
  font-size: 20px;
  text-align: center;
  cursor: default;
}
.notice_info_list {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  margin-bottom: 100px;
}
</style>
