<template>
  <div>
    <div class="notice">
      <Operation
        class="operation"
        @oper="changeOper"
        :notice="notice"
        :select="select"
      />
      <div class="notice_main">
        <div class="title">{{ notice.title }}</div>
        <div class="button_box">
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
          <a-tooltip
            title="删除"
            class="button"
            v-if="!notice.delete"
            @click="del"
            ><a-button type="danger" shape="circle" icon="delete"
          /></a-tooltip>
          <a-tooltip title="恢复" class="button" v-else @click="del"
            ><a-button type="danger" shape="circle" icon="undo"
          /></a-tooltip>
        </div>
        <router-view class="content"></router-view>
        <div>{{ notice }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import Operation from "./components/Operation";
import axios from "axios";

export default {
  name: "Notice",
  data() {
    return {
      select: "content",
      backUrl: ""
    };
  },
  components: {
    Operation
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
      switch (v) {
        case "back":
          this.$router.push("" === this.backUrl ? "/notices" : this.backUrl);
          break;
        case "content":
        case "edit":
        case "todo":
        case "comment":
        case "update_log":
        case "attribute":
        case "analysis":
          this.$router.push("/notice/" + this.notice.id + "/" + v);
          break;
        default:
          break;
      }
    },
    sync() {
      this.reloadNotice(this.$route.params.id);
    },
    top() {
      let data = {
        nid: this.notice.id,
        aid: this.notice.aid,
        isTop: !this.notice.top,
        isRead: this.notice.read,
        isDelete: this.notice.delete,
        relativeImportance: this.notice.relativeImportance
      };
      axios
        .post(
          "notice/notice/" +
            this.notice.id +
            "/updateOperation?token=" +
            this.token,
          data
        )
        .then(res => {
          if (res.data.success && res.data.data) {
            let n = { ...this.notice };
            n.top = data.isTop;
            this.setNotice(n);
          } else {
            this.$notification["error"]({
              message: res.data.code,
              description: res.data.message
            });
          }
        });
    },
    del() {
      let data = {
        nid: this.notice.id,
        aid: this.notice.aid,
        isTop: this.notice.top,
        isRead: this.notice.read,
        isDelete: !this.notice.delete,
        relativeImportance: this.notice.relativeImportance
      };
      axios
        .post(
          "notice/notice/" +
            this.notice.id +
            "/updateOperation?token=" +
            this.token,
          data
        )
        .then(res => {
          if (res.data.success && res.data.data) {
            let n = { ...this.notice };
            n.del = data.isDelete;
            this.setNotice(n);
          } else {
            this.$notification["error"]({
              message: res.data.code,
              description: res.data.message
            });
          }
        });
    }
  },
  watch: {
    $route() {
      this.update();
    }
  },
  mounted() {
    this.update();
  }
};
</script>

<style lang="less" scoped>
.notice {
  width: 1200px;
  margin: 0 auto;
  .operation {
    width: 290px;
    background: rgba(255, 255, 255, 0.25);
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
    .title {
      background: rgba(255, 255, 255, 0.2);
      text-align: center;
      font-size: 36px;
      padding: 10px 0;
      border-radius: 5px;
      margin-bottom: 15px;
    }
    .button_box {
      background: rgba(255, 255, 255, 0.2);
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
      background: rgba(255, 255, 255, 0.2);
      padding: 20px 15px;
      border-radius: 5px;
      min-height: 400px;
      font-size: 20px;
    }
  }
}
</style>
