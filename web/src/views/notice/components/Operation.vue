<template>
  <div class="item_box">
    <a-menu
      :default-selected-keys="['content']"
      mode="inline"
      theme="dark"
      @click="handleClick"
      class="menu"
    >
      <a-menu-item key="back">
        <a-icon type="arrow-left" />
        <span>返回</span>
      </a-menu-item>
      <a-menu-item key="content">
        <a-icon type="file-markdown" />
        <span>内容</span>
      </a-menu-item>
      <a-sub-menu key="admin" class="menu" v-if="edit">
        <span slot="title"> <a-icon type="setting" />管理 </span>
        <a-menu-item key="edit"> <a-icon type="edit" />编辑 </a-menu-item>
        <a-menu-item key="analysis">
          <a-icon type="pie-chart" />统计
        </a-menu-item>
      </a-sub-menu>
      <a-menu-item key="todo" v-if="todo">
        <a-icon type="bars" />
        <span>待办</span>
      </a-menu-item>
      <a-menu-item key="comment">
        <a-icon type="message" />
        <span>评论</span>
      </a-menu-item>
      <a-menu-item key="update_log">
        <a-icon type="history" />
        <span>更新日记</span>
      </a-menu-item>
      <a-menu-item key="attribute">
        <a-icon type="carry-out" />
        <span>属性</span>
      </a-menu-item>
    </a-menu>
  </div>
</template>

<script>
export default {
  name: "Operation",
  props: {
    notice: Object,
    select: String
  },
  computed: {
    todo() {
      return (
        this.notice && this.notice.todoList && this.notice.todoList.length > 0
      );
    },
    edit() {
      return this.notice && this.notice.creator === this.notice.aid;
    }
  },
  methods: {
    handleClick(e) {
      this.$emit("oper", e.key);
    }
  }
};
</script>

<style lang="less">
.item_box {
  padding: 10px 0;
  .menu {
    font-size: 28px;
    background: rgba(0, 0, 0, 0) !important;
    .ant-menu-sub {
      background: rgba(0, 0, 0, 0) !important;
    }
  }
}
</style>
