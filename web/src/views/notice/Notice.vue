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
        <router-view class="content"></router-view>
        <div>{{ notice }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import Operation from "./components/Operation";

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
    ...mapActions(["getNotice"]),
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
        case "delete":
          alert("del?");
          break;
        default:
          break;
      }
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
