<template>
  <div class="content_text">
    <EditContent :notice="publish.notice" />
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
            <a-tooltip class="img" :title="file.path">
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
import EditContent from "../../components/edit/EditContent";
export default {
  name: "Content",
  components: { EditContent },
  computed: {
    ...mapState({
      theme: state => state.theme,
      publish: state => state.publish,
      token: state => state.token
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
      const data = new FormData();
      data.append("file", file);
      this.request({
        method: "post",
        url: `/notice/publish/upload?ref=${this.publish.pid}&name=${file.name}&token=${this.token}`,
        data,
        headers: { "Content-Type": "multipart/form-data" }
      }).then(path => {
        this.files.push({
          path,
          name: file.name,
          size: file.size,
          type: file.type
        });
        this.$refs.file.value = "";
      });
    },
    getFiles() {
      if (!this.publish.pid) {
        return;
      }
      const type_map = {
        png: "image",
        jpg: "image",
        gif: "image"
      };
      this.request({
        method: "get",
        url: `/notice/publish/getFileList?ref=${this.publish.pid}`
      }).then(files => {
        this.files = files.map(file => {
          const fns = file.split("/");
          const fts = file.split(".");
          const suffix = fts[fts.length - 1];
          return {
            path: file,
            name: fns[fns.length - 1],
            type: type_map[suffix]
          };
        });
      });
    }
  },
  created() {
    this.getFiles();
  }
};
</script>

<style lang="less" scoped>
@import "../../assets/theme.less";
.content_text {
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
