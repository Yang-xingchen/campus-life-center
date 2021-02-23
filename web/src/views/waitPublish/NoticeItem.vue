<template>
  <div>
    <div class="head" @click="show = !show">
      <div class="h-item organization">{{ notice.organizationName }}</div>
      <div class="h-item creator">{{ notice.creatorName }}</div>
      <a-tooltip class="h-item title" :title="notice.title">{{
        notice.title
      }}</a-tooltip>
      <div class="h-item time">{{ format_date(notice.createTime) }}</div>
      <a-button type="danger" @click="reject">删除</a-button>
      <a-button type="primary" @click="accept">发布</a-button>
    </div>
    <div class="body" v-show="show">
      <div class="item">
        <span class="title">标题</span
        ><span class="content"> {{ notice.title }}</span>
      </div>
      <div class="item">
        <span class="title">发布人</span
        ><span class="content"> {{ notice.creatorName }}</span>
      </div>
      <div class="item">
        <span class="title">发布组织</span
        ><span class="content"> {{ notice.organizationName }}</span>
      </div>
      <div class="item">
        <span class="title">初始重要度</span
        ><span class="content"> {{ notice.importance }}星</span>
      </div>
      <div class="item">
        <span class="title">标签</span
        ><span class="content"> {{ notice.tag.join(", ") }}</span>
      </div>
      <div class="item">
        <span class="title">发布时间</span
        ><span class="content"> {{ format_date(notice.createTime) }}</span>
      </div>
      <div class="item">
        <span class="title">通知类型</span
        ><span class="content">
          {{ ["消息", "事件", "活动"][notice.publicType] }}</span
        >
      </div>
      <div class="item" v-if="notice.startTime">
        <span class="title">开始时间</span
        ><span class="content"> {{ format_date(notice.startTime) }}</span>
      </div>
      <div class="item" v-if="notice.endTime">
        <span class="title">结束时间</span
        ><span class="content"> {{ format_date(notice.endTime) }}</span>
      </div>
      <div class="item">
        <span class="title">待办({{ notice.todoList.length }})</span
        ><span class="content"> </span>
      </div>
      <div class="item" v-for="todo in notice.todoList" :key="todo.id">
        <span class="title"></span><span class="content">{{ todo.value }}</span>
      </div>
      <div class="item">
        <span class="title">信息收集({{ notice.noticeInfos.length }})</span
        ><span class="content"> </span>
      </div>
      <div class="item" v-for="info in notice.noticeInfos" :key="info.id">
        <span class="title"></span><span class="content">{{ info.name }}</span>
      </div>
      <div class="item">
        <span class="title">内容</span><span class="content"> </span>
      </div>
      <div class="noticeContent">
        {{ notice.content }}
      </div>
    </div>
  </div>
</template>

<script>
import { format_date } from "../../util";
import { mapState } from "vuex";
export default {
  name: "NoticeItem",
  props: {
    notice: Object
  },
  data() {
    return {
      show: false
    };
  },
  computed: {
    ...mapState({
      token: state => state.token
    })
  },
  methods: {
    format_date,
    accept() {
      this.request({
        method: "get",
        url: `/notice/publish/${this.notice.id}/acceptPublish?token=${this.token}`
      }).then(success => {
        if (success) {
          this.$emit("update");
        }
      });
    },
    reject() {
      this.request({
        method: "get",
        url: `/notice/publish/${this.notice.id}/rejectPublish?token=${this.token}`
      }).then(success => {
        if (success) {
          this.$emit("update");
        }
      });
    }
  }
};
</script>

<style lang="less" scoped>
.head {
  background: #8884;
  display: flex;
  font-size: 22px;
  padding: 5px;
  .h-item {
    margin: 0 5px;
    text-align: center;
    cursor: pointer;
  }
  .title {
    flex: auto;
  }
}
.body {
  .item {
    padding: 2px 5px;
    display: flex;
    font-size: 18px;
    border-top: #888 1px solid;
    border-bottom: #888 1px solid;
    .title {
      flex: 1;
      padding-left: 20px;
    }
    .content {
      flex: 3;
    }
  }
  .noticeContent {
    padding: 5px 20px;
    font-size: 18px;
  }
}
</style>
