<template>
  <div>
    <div class="swith" v-show="has_script">
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
      v-else-if="type === 2 && (swith || !has_script)"
    />
    <div
      :class="['text', 't_content', theme]"
      v-else-if="type === 2 && has_script && !swith"
    >
      {{ text }}
    </div>
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
      text: state => state.notice.content || "",
      type: state => state.notice.contentType || 0,
      theme: state => state.theme
    }),
    has_script() {
      return this.text.toLowerCase().indexOf("<script") !== -1;
    }
  },
  data() {
    return {
      swith: false
    };
  }
};
</script>

<style lang="less" scope>
@import "../../assets/theme.less";
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
