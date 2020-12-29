<template>
  <div class="notice">
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
  name: "Notice",
  components: { Screen, NoticeMain },
  data() {
    return {
      notices: [
        {
          notice: {
            id: 1,
            creator: "root",
            organization: 1,
            visibility: false,
            importance: 1,
            publicType: 0,
            title: "r1000",
            createTime: "2020-12-26T07:16:22.000+0000",
            startTime: null,
            endTime: null,
            content: "content"
          },
          accountOperation: {
            nid: 1,
            aid: "root",
            isRead: false,
            isTop: false,
            isDelete: false,
            relativeImportance: 0
          },
          tag: ["test1"],
          todoList: []
        },
        {
          notice: {
            id: 2,
            creator: "root",
            organization: 1,
            visibility: false,
            importance: 3,
            publicType: 1,
            title: "r1031",
            createTime: "2020-12-26T07:16:22.000+0000",
            startTime: "2020-12-27T07:16:22.000+0000",
            endTime: null,
            content: "content"
          },
          accountOperation: {
            nid: 2,
            aid: "root",
            isRead: false,
            isTop: false,
            isDelete: false,
            relativeImportance: 0
          },
          tag: ["test1", "test2"],
          todoList: []
        },
        {
          notice: {
            id: 3,
            creator: "root",
            organization: 1,
            visibility: false,
            importance: 5,
            publicType: 2,
            title: "r1052",
            createTime: "2020-12-26T07:16:22.000+0000",
            startTime: "2020-12-27T07:16:22.000+0000",
            endTime: "2020-12-28T07:16:22.000+0000",
            content: "content"
          },
          accountOperation: {
            nid: 3,
            aid: "root",
            isRead: false,
            isTop: false,
            isDelete: false,
            relativeImportance: -1
          },
          tag: ["test2"],
          todoList: []
        }
      ],
      filterFuntion: n => n !== null
    };
  },
  computed: {
    ...mapState(["token"]),
    show_notice() {
      console.log(this.notices.filter(this.filterFuntion));
      return this.notices.filter(this.filterFuntion);
    }
  },
  mounted() {
    Axios.get("notice/notice/get/" + this.token + "").then(res => {
      console.log(res);
      this.notices = res.data.data;
    });
  },
  methods: {
    updateScreen(f) {
      console.log("update");
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
