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
        <Content
          class="content"
          v-show="select === 'content'"
          :text="notice.content"
        />
        <Edit class="content" v-show="select === 'edit'" :notice="notice" />
        <Todo
          class="content"
          v-show="select === 'todo'"
          :todoList="notice.todoList"
        />
        <Comment class="content" v-show="select === 'comment'" />
        <UpdateLog class="content" v-show="select === 'update_log'" />
        <Attribute class="content" v-show="select === 'attribute'" />
        <div>{{ notice }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import { mapState } from "vuex";
import Operation from "./components/Operation";
import Content from "./components/Content";
import Edit from "./components/Edit";
import Todo from "./components/Todo";
import Comment from "./components/Comment";
import UpdateLog from "./components/UpdateLog";
import Attribute from "./components/Attribute";

export default {
  name: "Notice",
  data() {
    return {
      notice: {},
      select: "content"
    };
  },
  components: { Operation, Content, Edit, Todo, Comment, UpdateLog, Attribute },
  computed: {
    ...mapState({
      token: state => state.token
    })
  },
  methods: {
    changeOper(v) {
      switch (v) {
        case "back":
          this.$router.go(-1);
          break;
        case "content":
        case "edit":
        case "todo":
        case "comment":
        case "update_log":
        case "attribute":
          this.select = v;
          break;
        case "delete":
          alert("del?");
          break;
        default:
          break;
      }
    }
  },
  mounted() {
    Axios.get("notice/" + this.$route.params.id + "?token=" + this.token).then(
      res => {
        this.notice = res.data.data;
      }
    );
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
