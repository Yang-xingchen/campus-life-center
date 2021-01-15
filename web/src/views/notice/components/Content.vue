<template>
  <div>
    <div class="swith" v-show="show_swith">
      当前内容存在脚本，已切换为纯文本形式，可确认后切换。
      <a-switch
        checked-children="HTML"
        un-checked-children="纯文本"
        v-model="swith"
      />
    </div>
    <div :class="['text', 't_content', theme]" v-if="type === 0">
      {{ text }}
    </div>
    <VueMarkdown
      :source="text"
      :class="['markdown', 't_content', theme]"
      v-else-if="type === 1"
    />
    <div
      :class="['html', 't_content', theme]"
      v-html="text"
      v-else-if="type === 2"
    />
    <div :class="['err', 't_content', theme]" v-else>error type:{{ type }}</div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import VueMarkdown from "vue-markdown";
export default {
  name: "content",
  components: { VueMarkdown },
  computed: {
    ...mapState({
      text: state => state.notice.content,
      content_type: state => state.notice.contentType,
      theme: state => state.theme
    })
  },
  data() {
    return {
      type: 0,
      show_swith: false,
      swith: false
    };
  },
  watch: {
    content_type() {
      if (!this.content_type) {
        this.type = 0;
        return;
      }
      if (this.content_type !== 2) {
        this.type = this.content_type;
        return;
      }
      if (this.text.toLowerCase().indexOf("<script") === -1) {
        this.type = 2;
        return;
      }
      this.show_swith = true;
      this.type = 0;
    },
    swith() {
      this.type = this.swith ? 0 : 2;
    }
  }
};
</script>

<style lang="less" scope>
@import "../../../assets/theme.less";
.t_content {
  background: #0000;
  &.dark {
    * {
      color: @d-fg;
    }
  }
  &.light {
    * {
      color: @l-fg;
    }
  }
}
</style>
