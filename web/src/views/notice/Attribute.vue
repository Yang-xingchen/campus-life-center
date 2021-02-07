<template>
  <div>
    <div class="attribute">
      <div class="id"><a-icon type="key" />id: {{ notice.id }}</div>
      <div class="creator">
        <a-icon type="user" />发布人: {{ notice.creatorName }}
      </div>
      <div class="organization">
        <a-icon type="team" />发布组织: {{ notice.organizationName }}
        <a-icon class="link" type="link" @click="linkOrganization" />
      </div>
      <div class="time">
        <a-icon type="calendar" />发布日期: {{ format_date(notice.createTime) }}
      </div>
      <div class="importance">
        <a-icon type="warning" />初始重要度:
        <a-rate :default-value="notice.importance" disabled />
      </div>
      <div class="type"><a-icon type="appstore" />发布类型: {{ type }}</div>
      <div class="time" v-if="notice.publicType !== 0">
        <a-icon type="clock-circle" />
        <span v-if="notice.publicType === 1"
          >时间: {{ format_date(notice.startTime) }}</span
        >
        <span v-if="notice.publicType === 2"
          >时间: {{ format_date(notice.startTime) }} 至
          {{ format_date(notice.endTime) }}</span
        >
      </div>
      <div class="tags" v-if="notice.tag.length > 0">
        <a-icon type="tags" />标签:
        <a-tag v-for="t in notice.tag" :key="t" color="#00000040" class="tag">{{
          t
        }}</a-tag>
      </div>
      <a-divider class="divider">用户属性</a-divider>
      <div class="realImportance">
        <a-icon type="warning" />实际重要度:
        <a-rate v-model="realImportance" disabled />
      </div>
      <div class="operation" v-if="notice.looked || notice.top || notice.del">
        <a-icon type="user" />用户属性:
        <span v-if="notice.looked"><a-icon type="read" />已读</span>
        <span v-if="notice.top"><a-icon type="pushpin" />置顶</span>
        <span v-if="notice.del"><a-icon type="delete" />删除</span>
      </div>
      <div class="todo_progress" v-if="notice.todoList.length > 0">
        <span class="todo_info"><a-icon type="bars" />待办事项完成度:</span>
        <a-progress :percent="todoProgress" class="progress" status="active" />
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { format_date } from "../../util";
export default {
  name: "attribute",
  computed: {
    ...mapState({
      notice: state => state.notice
    }),
    type() {
      switch (this.notice.publicType) {
        case 0:
          return "消息";
        case 1:
          return "事件";
        case 2:
          return "活动";
        default:
          return "未知";
      }
    },
    todoProgress() {
      return (
        (100 * this.notice.todoList.filter(t => t.finish).length) /
        this.notice.todoList.length
      );
    },
    realImportance() {
      return this.notice.importance + this.notice.relativeImportance;
    }
  },
  methods: {
    format_date(d) {
      return format_date(d);
    },
    linkOrganization() {
      this.$router.push(`/organization/${this.notice.organization}`);
    }
  }
};
</script>

<style lang="less" scoped>
.attribute {
  padding: 0 10px;
  div {
    margin: 3px 0;
    height: 38px;
    line-height: 38px;
  }
  .organization {
    .link {
      &:hover {
        color: #888;
      }
    }
  }
  .divider {
    color: white;
  }
  .tag {
    padding: 3px 5px;
    margin: 3px;
  }
  .operation {
    span {
      margin: 0 5px;
    }
  }
  .todo_progress {
    height: 70px;
    .todo_info {
      margin-bottom: -5px;
    }
    div {
      padding: 0;
    }
  }
  .progress {
    color: white;
  }
}
</style>
