<template>
  <div>
    <div id="info" class="info" v-for="info in items" :key="info.id">
      <span>{{ info.name }}</span>
      <div class="text" v-if="info.type === 0">
        <div class="warp" v-for="(value, index) in info.value" :key="index">
          <a-input
            :placeholder="'示例:' + info.sample"
            v-model="info.value[index]"
          />
          <a-icon
            type="minus-circle"
            v-if="info.multiple"
            @click="info.value.splice(index, 1)"
          />
        </div>
        <a-input
          :placeholder="'示例:' + info.sample"
          v-if="info.multiple || !info.value.length"
          @pressEnter="e => info.value.push(e.target._value)"
        />
      </div>
      <div class="array" v-if="info.type === 1">
        <InfoCollectItem class="item" :items="info.items" />
      </div>
      <div class="radio" v-if="info.type === 2">
        <a-checkbox-group
          v-model="info.value"
          v-if="info.multiple"
          :options="info.radio"
        />
        <a-radio-group v-model="info.value[0]" v-else>
          <a-radio-button v-for="v in info.radio" :key="v" :value="v"
            >{{ v }}
          </a-radio-button>
        </a-radio-group>
      </div>
      <div class="file" v-if="info.type === 3">
        <div class="file_list">
          <div class="file" v-for="f in info.value" :key="f">
            <div class="title">{{ f }}</div>
            <a-button class="button" @click="browse(f)">
              <a-icon type="link" />浏览
            </a-button>
            <a-button class="button" type="primary" @click="download(f)">
              <a-icon type="download" />下载
            </a-button>
          </div>
        </div>
        <input type="file" ref="file" multiple />
        <a-button type="primary" @click="upload(info.id)">
          <a-icon type="upload" />上传
        </a-button>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
export default {
  name: "InfoCollectItem",
  props: {
    items: Array
  },
  methods: {
    upload(id) {
      const file = this.$refs.file.files[0];
      const form = new FormData();
      form.append("file", file);
      Axios.post(
        `/info/${id}/upload?name=${file.name}&token=${this.token}`,
        form,
        {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        }
      ).then(res => {
        if (res.data.success) {
          this.files.push(res.data.data);
          this.$refs.file.value = "";
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    },
    browse(path) {
      window.open(path, "_blank");
    },
    download(file) {
      let fs = file.split("/");
      let file_name = fs[fs.length - 1];
      let doc = document.getElementById("info");
      let temp_download = document.createElement("a");
      temp_download.setAttribute("href", file);
      temp_download.setAttribute("download", file_name);
      temp_download.setAttribute("style", "display:none");
      doc.appendChild(temp_download);
      temp_download.click();
      temp_download.remove();
    }
  }
};
</script>

<style lang="less" scoped>
.info {
  margin: 15px 0;
  .warp {
    margin: 5px 0;
    display: flex;
    * {
      margin: auto 2px;
    }
  }
  .array {
    padding: 10px 20px;
    border: 1px solid #888;
    border-radius: 5px;
    .item {
      margin: 10px 0;
    }
  }
  .file {
    margin: 0 0 10px;
    .file_list {
      .file {
        display: flex;
        padding: 3px 0 3px 10px;
        border-radius: 5px;
        &:hover {
          background: #888;
        }
        * {
          margin: 0 5px;
        }
        .title {
          flex: auto;
        }
      }
    }
  }
}
</style>
