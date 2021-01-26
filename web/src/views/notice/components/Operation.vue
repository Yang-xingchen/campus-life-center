<template>
  <div class="item_box">
    <a-menu
      :default-selected-keys="['content']"
      :selected-keys="[selectMenu]"
      :openKeys.sync="openKeys"
      mode="inline"
      :theme="theme"
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
        <a-menu-item v-for="i in infos" :key="'edit:info:' + i.ref">
          <a-icon type="form" /> {{ i.name }}
        </a-menu-item>
      </a-sub-menu>
      <a-menu-item key="todo" v-if="todo">
        <a-icon type="bars" />
        <span>待办</span>
      </a-menu-item>
      <a-menu-item v-for="i in infos" :key="'info:' + i.ref">
        <a-icon type="form" /> {{ i.name }}
      </a-menu-item>
      <a-menu-item key="file" v-if="file">
        <a-icon type="file" />
        <span>文件</span>
      </a-menu-item>
      <a-menu-item key="comment">
        <a-icon type="message" />
        <span>评论</span>
      </a-menu-item>
      <a-menu-item key="update_log" v-if="notice.version > 1">
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
import { mapState } from "vuex";
export default {
  name: "Operation",
  props: {
    notice: Object,
    select: String
  },
  data() {
    return {
      openKeys: ["admin"]
    };
  },
  computed: {
    ...mapState(["theme"]),
    todo() {
      return (
        this.notice && this.notice.todoList && this.notice.todoList.length > 0
      );
    },
    edit() {
      return this.notice && this.notice.creator === this.notice.aid;
    },
    file() {
      return this.notice && this.notice.files;
    },
    infos() {
      if (
        this.notice &&
        this.notice.noticeInfos &&
        this.notice.noticeInfos.length != 0
      ) {
        return this.notice.noticeInfos;
      }
      return [];
    },
    selectMenu() {
      if (this.select === "") {
        return "content";
      }
      if (this.select.startsWith("info/res/")) {
        return "edit:info:" + this.select.substring(9);
      }
      if (this.select.startsWith("info/")) {
        return "info:" + this.select.substring(5);
      }
      return this.select || "content";
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
      box-shadow: 0 0 20px #0004 inset !important;
    }
  }
}
</style>
