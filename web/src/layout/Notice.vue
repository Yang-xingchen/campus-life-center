<template>
  <div>
    <div class="notice">
      <Menu class="operation" @oper="changeOper" :notice="notice" />
      <div class="notice_main">
        <div class="title">{{ notice.title }}</div>
        <div class="button_box">
          <a-tooltip title="更新重要度" class="button importance"
            ><a-rate v-model="importance" @change="importanceChange"
          /></a-tooltip>
          <div class="text button space"></div>
          <a-tooltip title="刷新" class="button text" @click="sync"
            ><span>刷新</span></a-tooltip
          >
          <a-tooltip
            title="置顶"
            class="button text"
            v-if="!notice.top"
            @click="top"
            ><span>置顶</span></a-tooltip
          >
          <a-tooltip title="取消置顶" class="button text" v-else @click="top"
            ><span>取消置顶</span></a-tooltip
          >
          <a-tooltip
            title="删除"
            class="button text"
            v-if="!notice.del"
            @click="del"
            ><span>删除</span></a-tooltip
          >
          <a-tooltip title="恢复" class="button text" v-else @click="del"
            ><span>恢复</span></a-tooltip
          >
        </div>
        <router-view class="content"></router-view>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import Menu from "../components/NoticeMenu";

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
      notice: state => state.notice
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
      this.request({
        method: "post",
        url: `/notice/${this.notice.id}/updateOperation?token=${this.token}`,
        data
      }).then(success => {
        if (success) {
          let n = { ...this.notice };
          fn(n);
          this.setNotice(n);
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
  created() {
    this.update();
  }
};
</script>

<style lang="less" scoped>
.notice {
  width: 1200px;
  margin: 0 auto;
  .operation {
    background: #8882;
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
      background: #8882;
      text-align: center;
      font-size: 36px;
      padding: 10px 0;
      border-radius: 5px;
      margin-bottom: 15px;
    }
    .button_box {
      background: #8884;
      padding: 10px 20px;
      margin-bottom: 15px;
      border-radius: 5px;
      display: flex;
      justify-content: left;
      .button {
        flex: 1;
        margin: auto;
        text-align: center;
      }
      .importance {
        flex: 2;
      }
      .text {
        font-size: 18px;
        cursor: pointer;
        &:hover {
          color: #88f8;
        }
      }
      .space {
        border-right: #888 1px solid;
        height: 25px;
        flex: 5;
        cursor: default;
      }
    }
    .content {
      background: #8886;
      padding: 20px 15px;
      border-radius: 5px;
      min-height: 400px;
      font-size: 20px;
    }
  }
}
</style>
