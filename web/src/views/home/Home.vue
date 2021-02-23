<template>
  <div class="home_body">
    <div class="home" v-if="user.id">
      <div class="name">您好，{{ user.name }}:</div>
      <div class="box">
        <div class="box_left">
          <Info />
          <Organization />
          <Operation />
        </div>
        <div class="box_right">
          <Todo :todo="showTodo" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import Info from "./components/Info";
import Todo from "./components/Todo";
import Organization from "./components/Organization";
import Operation from "./components/Operations";
export default {
  name: "home",
  data() {
    return {
      todo: []
    };
  },
  components: { Info, Todo, Organization, Operation },
  computed: {
    ...mapState({
      user: state => state.user,
      token: state => state.token,
      theme: state => state.theme
    }),
    showTodo() {
      let todos = this.todo.filter(t => {
        return t.addList || t.top;
      });
      return [...todos.filter(t => t.top), ...todos.filter(t => !t.top)];
    }
  },
  created() {
    this.request({
      method: "get",
      url: `/todo/account/all?token=${this.token}`
    }).then(todo => (this.todo = todo));
    window.document.title = this.user.name;
  },
  watch: {
    user() {
      window.document.title = this.user.name;
    }
  }
};
</script>
<style lang="less" scoped>
.home_body {
  display: flex;
  width: 100%;
  height: calc(~"100vh - 65px");
  cursor: default;
  .home {
    width: 1500px;
    height: 800px;
    margin: auto;
    padding: 15px 30px;
    border-radius: 5px;
    background: #8882;
    .name {
      font-size: 28px;
      height: 50px;
      margin-left: 220px;
    }
    .box {
      display: flex;
      height: 700px;
      .box_left {
        flex: 3;
        margin: 0 10px;
        > div {
          margin: 25px 0;
        }
        > div:first-child {
          margin: 0;
        }
        > div:last-child {
          margin: 0;
        }
      }
      .box_right {
        flex: 1;
        margin: 0 10px;
        border-radius: 5px;
      }
    }
  }
}
</style>
