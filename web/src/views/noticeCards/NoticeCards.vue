<template>
  <div>
    <div class="notice">
      <Screen id="screen" :notices="notices" @update-screen="updateScreen" />
      <NoticeMain id="notice_main" :notices="show_notice" />
    </div>
  </div>
</template>

<script>
import Screen from "./components/Screen";
import NoticeMain from "./components/NoticeMain";
import Axios from "axios";
import { mapState } from "vuex";

export default {
  name: "NoticeCards",
  components: { Screen, NoticeMain },
  data() {
    return {
      notices: [],
      filterFuntion: n => n !== null
    };
  },
  computed: {
    ...mapState(["token"]),
    show_notice() {
      return this.notices
        .map(n => {
          n.accountImportance = n.importance + n.relativeImportance;
          n.showTag = [
            ...n.tag,
            n.top ? "top" : "",
            !n.read ? "unread" : "",
            n.delete ? "delete" : "",
            "importance:" +
              n.accountImportance +
              "(" +
              (n.relativeImportance > 0
                ? "+" + n.relativeImportance
                : n.relativeImportance) +
              ")",
            n.todoList.filter(t => !t.finish).length > 0 ? "todo:unfinish" : "",
            "creator:" + n.creator,
            "organization:" + n.organizationName
          ].filter(t => t !== "");
          return n;
        })
        .filter(this.filterFuntion);
    }
  },
  mounted() {
    Axios.get("notice/notice/get/" + this.token).then(res => {
      if (res.data.success) {
        this.notices = res.data.data;
      } else {
        this.$notification["error"]({
          message: res.data.code,
          description: res.data.message
        });
      }
    });
  },
  methods: {
    updateScreen(f) {
      this.filterFuntion = f;
    }
  }
};
</script>

<style lang="less" scope>
.notice {
  width: 1500px;
  margin: 0 auto;
  #screen {
    width: 365px;
    background: rgba(255, 255, 255, 0.25);
    margin: 5px;
    margin-top: 25px;
    float: left;
    position: fixed;
  }
  #notice_main {
    width: 1115px;
    margin: 5px;
    float: right;
  }
}
</style>
