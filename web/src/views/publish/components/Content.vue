<template>
  <div class="content_text">
    <div class="head">
      <div class="type">
        文本格式:
        <a-radio-group v-model="publish.notice.contentType">
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
      v-model="publish.notice.content"
      :class="['text', theme]"
      :auto-size="{ minRows: 5 }"
      placeholder="通知正文内容"
    />
    <div class="browse" v-show="!edit">
      <a-divider></a-divider>
      <div class="b_text" v-if="publish.notice.contentType === 0">
        {{ publish.notice.content }}
      </div>
      <VueMarkdown
        class="b_md"
        v-else-if="publish.notice.contentType === 1"
        :source="publish.notice.content"
      />
      <div
        class="b_html"
        v-html="publish.notice.content"
        v-else-if="publish.notice.contentType === 2"
      ></div>
    </div>
    <div class="file">
      <div class="head">
        <input type="file" ref="file" multiple />
        <a-button type="primary" @click="upload">
          <a-icon type="upload" />上传
        </a-button>
      </div>
      <div class="filelist">
        <div class="file" v-for="file in files" :key="file.name">
          <div class="imgfile" v-if="file.type.indexOf('image') !== -1">
            <a-tooltip class="img" :title="'路径:' + file.path">
              <img :src="file.path" />
            </a-tooltip>
            <a-tooltip class="text" :title="file.name" placement="topLeft">
              {{ file.name }}
            </a-tooltip>
          </div>
          <div class="file" v-else>
            <a-tooltip class="text" :title="file.name" placement="topLeft">
              {{ file.name }}
            </a-tooltip>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import VueMarkdown from "vue-markdown";
import Axios from "axios";
export default {
  name: "Content",
  components: { VueMarkdown },
  computed: {
    ...mapState({
      theme: state => state.theme,
      publish: state => state.publish
    })
  },
  data() {
    return {
      edit: true,
      files: []
    };
  },
  methods: {
    upload() {
      const file = this.$refs.file.files[0];
      const form = new FormData();
      form.append("file", file);
      Axios.post(
        `notice/notice/publish/upload?ref=${this.publish.pid}&name=${file.name}`,
        form,
        {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        }
      ).then(res => {
        if (res.data.success) {
          this.files.push({
            path: res.data.data,
            name: file.name,
            size: file.size,
            type: file.type
          });
          this.$refs.file.value = "";
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    }
  }
};
</script>

<style lang="less" scoped>
@import "../../../assets/theme.less";
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
  .file {
    margin: 20px 0;
    .filelist {
      display: flex;
      margin: 5px;
      .file {
        width: 200px;
        height: 200px;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
        .text {
          display: block;
          margin: 0;
          text-align: center;
        }
        .imgfile {
          .img {
            width: 170px;
            height: 170px;
            margin: 0 15px;
          }
        }
      }
    }
  }
}
</style>
