<template>
  <div>
    <div class="title item">
      <span class="key">标题:</span>
      <a-input class="value" v-model="notice.title" placeholder="标题内容" />
    </div>
    <div class="tags item">
      <span class="key">标签:</span>
      <a-select
        class="value "
        mode="tags"
        v-model="notice.tag"
        placeholder="标签内容，用于搜索及筛选，可选择现有标签或输入(回车添加)"
      >
        <a-select-option v-for="t in tags" :key="t" :value="t">{{
          t
        }}</a-select-option>
      </a-select>
    </div>
    <div class="visibility item">
      <span class="key">
        <a-tooltip title="是否可通过链接查看未收到的通知">
          <a-icon type="question-circle" />
        </a-tooltip>
        可见性:
      </span>
      <a-radio-group class="value" v-model="notice.visibility">
        <a-radio-button :value="0">公开</a-radio-button>
        <a-radio-button :value="1">仅通知成员</a-radio-button>
        <a-radio-button :value="1">仅自己</a-radio-button>
      </a-radio-group>
    </div>
    <div class="organiztaion item">
      <span class="key">
        <a-tooltip title="发布组织来源，主要用于筛选，与接收成员无关">
          <a-icon type="question-circle" />
        </a-tooltip>
        发布组织:
      </span>
      <a-select class="value" v-model="notice.organization">
        <a-select-option :key="0" :value="0">自己</a-select-option>
        <a-select-option v-for="o in organizations" :key="o.id" :value="o.id">{{
          o.name
        }}</a-select-option>
      </a-select>
    </div>
    <div class="importance item">
      <span class="key">
        <a-tooltip title="默认的重要程度，点击星星更改">
          <a-icon type="question-circle" />
        </a-tooltip>
        重要程度:
      </span>
      <a-rate class="value" v-model="notice.importance" />
    </div>
    <div class="publish_type item">
      <span class="key">
        发布类型:
      </span>
      <div class="value">
        <a-radio-group v-model="notice.publicType" @change="clearnTime">
          <a-radio-button :value="0">消息</a-radio-button>
          <a-radio-button :value="1">事件</a-radio-button>
          <a-radio-button :value="2">活动</a-radio-button>
        </a-radio-group>
        <a-date-picker
          class="date"
          v-if="notice.publicType === 1"
          v-model="notice.startTime"
          :show-time="{ format: 'HH:mm' }"
          format="YYYY-MM-DD HH:mm"
          placeholder="事件发生时间"
          :locale="locale"
        />
        <a-range-picker
          class="date"
          v-if="notice.publicType === 2"
          v-model="range"
          :show-time="{ format: 'HH:mm' }"
          format="YYYY-MM-DD HH:mm"
          @ok="setRangeTime"
          :placeholder="['活动开始时间', '活动结束时间']"
          :locale="locale"
        />
      </div>
    </div>
  </div>
</template>

<script>
import locale from "ant-design-vue/es/date-picker/locale/zh_CN";
import { mapState } from "vuex";
export default {
  name: "EditAttribute",
  props: {
    notice: Object
  },
  computed: {
    ...mapState({
      organizations: state => state.user.organizations
    })
  },
  data() {
    return {
      tags: [],
      range: [],
      locale
    };
  },
  methods: {
    getTags() {
      this.request({
        method: "get",
        url: `/notice/tag`
      }).then(tags => (this.tags = tags));
    },
    setRangeTime() {
      this.notice.startTime = this.range[0];
      this.notice.endTime = this.range[1];
    },
    clearnTime() {
      this.notice.startTime = null;
      this.notice.endTime = null;
      this.range = [];
    }
  },
  created() {
    this.getTags();
    this.range = [this.notice.startTime, this.notice.endTime];
  }
};
</script>

<style lang="less" scoped>
.item {
  margin: 5px 0;
  display: flex;
  .key {
    flex: 1;
  }
  .value {
    flex: 3;
    .date {
      margin: 0 10px;
    }
  }
}
</style>
