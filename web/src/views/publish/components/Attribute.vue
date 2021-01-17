<template>
  <div>
    <div class="title item">
      标题: <a-input v-model="publish.notice.title" placeholder="标题内容" />
    </div>
    <div class="tags">
      标签:
      <a-select
        mode="tags"
        v-model="publish.tag"
        placeholder="标签内容，用于搜索及筛选，可选择现有标签或输入(回车添加)"
        class="select"
      >
        <a-select-option v-for="t in tags" :key="t" :value="t">{{
          t
        }}</a-select-option>
      </a-select>
    </div>
    <div class="visibility item">
      <a-tooltip title="是否可通过链接查看未收到的通知">
        <a-icon type="question-circle" />
      </a-tooltip>
      可见性:
      <a-radio-group v-model="publish.notice.visibility">
        <a-radio-button :value="0">公开</a-radio-button>
        <a-radio-button :value="1">私密</a-radio-button>
      </a-radio-group>
    </div>
    <div class="organiztaion item">
      <a-tooltip title="发布组织来源，主要用于筛选，与接收成员无关">
        <a-icon type="question-circle" />
      </a-tooltip>
      发布组织:
      <a-select v-model="publish.notice.organization" class="select">
        <a-select-option :key="0" value="0">自己</a-select-option>
        <a-select-option
          v-for="o in user.organizations"
          :key="o.oid"
          :value="o.oid"
          >{{ o.organizationName }}</a-select-option
        >
      </a-select>
    </div>
    <div class="importance item">
      <a-tooltip title="默认的重要程度，点击星星更改">
        <a-icon type="question-circle" />
      </a-tooltip>
      重要程度: <a-rate v-model="publish.notice.importance" />
    </div>
    <div class="publish_type">
      发布类型:
      <a-radio-group v-model="publish.notice.publicType" @change="clearnTime">
        <a-radio-button :value="0">消息</a-radio-button>
        <a-radio-button :value="1">事件</a-radio-button>
        <a-radio-button :value="2">活动</a-radio-button>
      </a-radio-group>
      <a-date-picker
        class="date"
        v-if="publish.notice.publicType === 1"
        v-model="publish.notice.startTime"
        :show-time="{ format: 'HH:mm' }"
        format="YYYY-MM-DD HH:mm"
        placeholder="事件发生时间"
        :locale="locale"
      />
      <a-range-picker
        class="date"
        v-if="publish.notice.publicType === 2"
        v-model="range"
        :show-time="{ format: 'HH:mm' }"
        format="YYYY-MM-DD HH:mm"
        @ok="setRangeTime"
        :placeholder="['活动开始时间', '活动结束时间']"
        :locale="locale"
      />
    </div>
  </div>
</template>

<script>
import locale from "ant-design-vue/es/date-picker/locale/zh_CN";
import { mapState } from "vuex";
import Axios from "axios";
export default {
  name: "Attribute",
  computed: {
    ...mapState({
      publish: state => state.publish,
      user: state => state.user,
      theme: state => state.theme
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
      Axios.get(`/notice/notice/tag`).then(res => {
        if (res.data.success) {
          this.tags = res.data.data;
        }
      });
    },
    setRangeTime() {
      this.publish.notice.startTime = this.range[0];
      this.publish.notice.endTime = this.range[1];
    },
    clearnTime() {
      this.publish.notice.startTime = null;
      this.publish.notice.endTime = null;
      this.range = [];
    }
  },
  mounted() {
    this.getTags();
    this.range = [this.publish.notice.startTime, this.publish.notice.endTime];
  }
};
</script>

<style lang="less" scoped>
.item {
  margin: 5px 0;
}
.tags {
  .select {
    width: 80%;
  }
}
.organiztaion {
  .select {
    width: 200px;
  }
}
.publish_type {
  .date {
    margin: 0 10px;
  }
}
</style>
