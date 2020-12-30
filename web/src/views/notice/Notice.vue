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
            title: "r1010",
            createTime: "2020-12-30T04:43:20.000+0000",
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
          todoList: [
            {
              noticeTodo: { id: 1, nid: 1, type: 0, value: "n1i1fa" },
              accountNoticeTodo: {
                nid: 1,
                id: 1,
                aid: "root",
                finish: true,
                isTop: false,
                isAdd: true
              }
            },
            {
              noticeTodo: { id: 2, nid: 1, type: 0, value: "n1i2f" },
              accountNoticeTodo: {
                nid: 1,
                id: 2,
                aid: "root",
                finish: true,
                isTop: false,
                isAdd: false
              }
            },
            {
              noticeTodo: { id: 3, nid: 1, type: 0, value: "n1i3a" },
              accountNoticeTodo: {
                nid: 1,
                id: 3,
                aid: "root",
                finish: false,
                isTop: false,
                isAdd: true
              }
            },
            {
              noticeTodo: { id: 4, nid: 1, type: 0, value: "n1i4" },
              accountNoticeTodo: {
                nid: 1,
                id: 4,
                aid: "root",
                finish: false,
                isTop: false,
                isAdd: false
              }
            }
          ]
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
            createTime: "2020-12-30T04:43:20.000+0000",
            startTime: "2020-12-31T04:43:20.000+0000",
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
          todoList: [
            {
              noticeTodo: { id: 1, nid: 2, type: 0, value: "n2i1fa" },
              accountNoticeTodo: {
                nid: 2,
                id: 1,
                aid: "root",
                finish: true,
                isTop: false,
                isAdd: true
              }
            }
          ]
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
            createTime: "2020-12-30T04:43:20.000+0000",
            startTime: "2020-12-31T04:43:20.000+0000",
            endTime: "2021-01-01T04:43:20.000+0000",
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
          tag: ["test2", "test3"],
          todoList: [
            {
              noticeTodo: { id: 1, nid: 3, type: 0, value: "n3i1a" },
              accountNoticeTodo: {
                nid: 3,
                id: 1,
                aid: "root",
                finish: false,
                isTop: false,
                isAdd: true
              }
            },
            {
              noticeTodo: { id: 2, nid: 3, type: 0, value: "n3i2a" },
              accountNoticeTodo: {
                nid: 3,
                id: 2,
                aid: "root",
                finish: false,
                isTop: false,
                isAdd: true
              }
            }
          ]
        }
      ],
      filterFuntion: n => n !== null
    };
  },
  computed: {
    ...mapState(["token"]),
    show_notice() {
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
