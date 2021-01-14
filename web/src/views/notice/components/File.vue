<template>
  <div id="file">
    <div v-for="file in show_files" :key="file.name">
      <div :class="['file', theme]">
        <a-tooltip :title="file.name" class="fn">{{ file.name }}</a-tooltip>
        <div class="operation">
          <a-button class="button" @click="browse(file.path)"
            ><a-icon type="link"></a-icon>浏览</a-button
          >
          <a-button class="button" type="primary" @click="download(file)"
            ><a-icon type="download"></a-icon>下载</a-button
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "File",
  computed: {
    ...mapState({
      files: state => state.notice.files,
      theme: state => state.theme
    }),
    show_files() {
      if (!this.files) {
        return [];
      }
      return this.files.map(file => {
        return {
          name: file.substring(file.lastIndexOf("/") + 1),
          path: file,
          type: file.substring(file.lastIndexOf(".") + 1)
        };
      });
    }
  },
  methods: {
    browse(path) {
      window.open(path, "_blank");
    },
    download(file) {
      let doc = document.getElementById("file");
      let temp_download = document.createElement("a");
      temp_download.setAttribute("href", file.path);
      temp_download.setAttribute("download", file.name);
      temp_download.setAttribute("style", "display:none");
      doc.appendChild(temp_download);
      temp_download.click();
      temp_download.remove();
    }
  }
};
</script>

<style lang="less" scoped>
@import "../../../assets/theme.less";
.file {
  padding: 10px;
  border-radius: 5px;
  display: flex;
  margin-bottom: 10px;
  .fn {
    margin: auto;
    margin-left: 10px;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
  }
  .operation {
    margin: auto;
    margin-right: 0;
    .button {
      margin-left: 10px;
    }
  }
  &.dark {
    background: @d-bg2;
    &:hover {
      background: @d-bg4;
    }
  }
  &.light {
    background: @l-bg2;
    &:hover {
      background: @d-bg4;
    }
  }
}
</style>
