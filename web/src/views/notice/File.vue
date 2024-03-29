<template>
  <div id="file">
    <div class="upload" v-if="editable">
      <input type="file" ref="file" multiple />
      <a-button type="primary" @click="upload">
        <a-icon type="upload" />上传
      </a-button>
    </div>
    <div v-for="file in show_files" :key="file.name">
      <div :class="['file', theme]">
        <a-tooltip :title="file.name" class="fn">{{ file.name }}</a-tooltip>
        <div class="operation">
          <a-button type="danger" v-if="editable" @click="del(file.name)">
            <a-icon type="delete" />删除
          </a-button>
          <a-button class="button" @click="browse(file.path)">
            <a-icon type="link" />浏览
          </a-button>
          <a-button class="button" type="primary" @click="download(file)">
            <a-icon type="download" />下载
          </a-button>
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
      theme: state => state.theme,
      token: state => state.token,
      ref: state => state.notice.fileRef,
      editable: state => state.user.id === state.notice.creator
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
    upload() {
      const file = this.$refs.file.files[0];
      const data = new FormData();
      data.append("file", file);
      this.request({
        method: "post",
        url: `/notice/publish/upload?ref=${this.ref}&name=${file.name}&token=${this.token}`,
        data,
        headers: { "Content-Type": "multipart/form-data" }
      }).then(file => {
        this.files.push(file);
        this.$refs.file.value = "";
      });
    },
    del(fn) {
      let that = this;
      this.$confirm({
        title: "是否确认删除该文件?",
        content: "文件删除后无法恢复",
        onOk() {
          that
            .request({
              method: "get",
              url: `/notice/publish/deleteFile?ref=${that.ref}&name=${fn}&token=${that.token}`
            })
            .then(success => {
              if (success) {
                let index = that.files
                  .map(f => f.substring(f.lastIndexOf("/") + 1))
                  .indexOf(f => f === fn);
                that.files.splice(index, 1);
              }
            });
        },
        onCancel() {}
      });
    },
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
@import "../../assets/theme.less";
.upload {
  display: flex;
  height: 50px;
  margin: 0 0 10px;
  * {
    margin: auto 10px;
  }
}
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
