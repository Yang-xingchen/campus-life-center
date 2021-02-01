<template>
  <div>
    <div class="notice">
      <Menu :class="['operation', theme]" @oper="changeOper" :notice="notice" />
      <div class="notice_main">
        <div :class="['title', theme]">{{ notice.title }}</div>
        <div :class="['button_box', theme]">
          <a-tooltip title="更新重要度" class="button"
            ><a-rate v-model="importance" @change="importanceChange"
          /></a-tooltip>
          <a-tooltip title="刷新" class="button" @click="sync"
            ><a-button type="primary" shape="circle" icon="sync"
          /></a-tooltip>
          <a-tooltip title="置顶" class="button" v-if="!notice.top" @click="top"
            ><a-button type="primary" shape="circle" icon="vertical-align-top"
          /></a-tooltip>
          <a-tooltip title="取消置顶" class="button" v-else @click="top"
            ><a-button
              type="primary"
              shape="circle"
              icon="vertical-align-bottom"
          /></a-tooltip>
          <a-tooltip title="删除" class="button" v-if="!notice.del" @click="del"
            ><a-button type="danger" shape="circle" icon="delete"
          /></a-tooltip>
          <a-tooltip title="恢复" class="button" v-else @click="del"
            ><a-button type="danger" shape="circle" icon="undo"
          /></a-tooltip>
        </div>
        <router-view :class="['content', theme]"></router-view>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import Menu from "../components/NoticeMenu";
import axios from "axios";

export default {
  name: "Notice",
  data() {
    return {
      backUrl: "",
      importance: 0
    };
  },
  components: {
    Menu
  },
  computed: {
    ...mapState({
      token: state => state.token,
      notice: state => state.notice,
      theme: state => state.theme
    })
  },
  methods: {
    ...mapActions(["getNotice", "reloadNotice"]),
    ...mapMutations(["setNotice"]),
    update() {
      if (!this.$route.params) {
        return;
      }
      if (this.$route.query.back && this.$route.query.back != "") {
        this.backUrl = this.$route.query.back;
      }
      this.getNotice(this.$route.params.id);
    },
    changeOper(v) {
      if (v === "back") {
        this.$router.push("" === this.backUrl ? "/notices" : this.backUrl);
      } else {
        this.$router.push(`/notice/${this.notice.id}/${v}`);
      }
    },
    sync() {
      this.reloadNotice(this.$route.params.id);
    },
    getUploadData() {
      let { id, aid, top, looked, del, relativeImportance } = {
        ...this.notice
      };
      return { nid: id, aid, top, looked, del, relativeImportance };
    },
    uploadUpdate(data, fn) {
      axios
        .post(
          `/notice/${this.notice.id}/updateOperation?token=${this.token}`,
          data
        )
        .then(res => {
          if (res.data.success && res.data.data) {
            let n = { ...this.notice };
            fn(n);
            this.setNotice(n);
          } else {
            this.$notification["error"]({
              message: res.data.code,
              description: res.data.message
            });
          }
        });
    },
    top() {
      let data = this.getUploadData();
      data.top = !data.top;
      this.uploadUpdate(data, n => (n.top = data.top));
    },
    del() {
      let data = this.getUploadData();
      data.del = !data.del;
      this.uploadUpdate(data, n => (n.del = data.del));
    },
    importanceChange() {
      let data = this.getUploadData();
      data.relativeImportance = this.importance - this.notice.importance;
      this.uploadUpdate(
        data,
        n => (n.relativeImportance = data.relativeImportance)
      );
    }
  },
  watch: {
    $route() {
      this.update();
    },
    notice() {
      if (!this.notice.id) {
        this.$router.push("/notices");
      }
      this.importance = this.notice.importance + this.notice.relativeImportance;
      window.document.title = this.notice.title;
    }
  },
  mounted() {
    this.update();
  }
};
</script>

<style lang="less" scoped>
@import "../assets/theme.less";
.notice {
  width: 1200px;
  margin: 0 auto;
  .dark {
    background: @d-bg2;
  }
  .light {
    background: @l-bg2;
  }
  .operation {
    width: 290px;
    margin: 5px;
    margin-top: 25px;
    float: left;
    position: fixed;
    border-radius: 5px;
  }
  .notice_main {
    width: 890px;
    margin: 5px;
    margin-top: 25px;
    float: right;
    padding-bottom: 25px;
    .title {
      text-align: center;
      font-size: 36px;
      padding: 10px 0;
      border-radius: 5px;
      margin-bottom: 15px;
    }
    .button_box {
      padding: 10px 20px;
      margin-bottom: 15px;
      border-radius: 5px;
      display: flex;
      justify-content: left;
      .button {
        margin: 0 10px;
      }
    }
    .content {
      padding: 20px 15px;
      border-radius: 5px;
      min-height: 400px;
      font-size: 20px;
    }
  }
}
</style>
