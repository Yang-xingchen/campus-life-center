<template>
  <div class="content_text">
    <div class="head">
      <div class="type">
        文本格式:
        <a-radio-group v-model="notice.contentType">
          <a-radio-button :value="0">纯文本</a-radio-button>
          <a-radio-button :value="1">Markdown</a-radio-button>
          <a-radio-button :value="2">HTML</a-radio-button>
        </a-radio-group>
      </div>
      <div class="swith">
        模式:
        <a-switch
          checked-children="编辑"
          un-checked-children="浏览"
          v-model="edit"
        />
      </div>
    </div>
    <a-textarea
      v-show="edit"
      v-model="notice.content"
      :class="['text', theme]"
      :auto-size="{ minRows: 5 }"
      placeholder="通知正文内容"
    />
    <div class="browse" v-show="!edit">
      <a-divider></a-divider>
      <div class="b_text" v-if="notice.contentType === 0">
        {{ notice.content }}
      </div>
      <VueMarkdown
        class="b_md"
        v-else-if="notice.contentType === 1"
        :source="notice.content"
      />
      <div
        class="b_html"
        v-html="notice.content"
        v-else-if="notice.contentType === 2"
      ></div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import VueMarkdown from "vue-markdown";
export default {
  name: "Content",
  components: { VueMarkdown },
  props: {
    notice: Object
  },
  computed: {
    ...mapState({
      theme: state => state.theme,
      token: state => state.token
    })
  },
  data() {
    return {
      edit: true
    };
  }
};
</script>

<style lang="less" scoped>
@import "../../assets/theme.less";
.content_text {
  .head {
    display: flex;
    .type {
      margin: auto 10px auto 0;
    }
    .swith {
      margin: auto 10px;
    }
  }
  .text {
    margin-top: 20px;
    font-size: 22px;
    &.dark {
      color: @d-fg;
      background: @d-bg4;
    }
    &.light {
      color: @l-fg;
      background: @l-bg4;
    }
  }
}
</style>
