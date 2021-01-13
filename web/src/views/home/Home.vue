<template>
  <div class="home_body">
    <div :class="['home', theme]" v-if="user">
      <div class="name">您好，{{ user.name }}:</div>
      <div class="box">
        <div class="box_left">
          <div :class="['info', theme]">
            <div class="id">登录id: {{ user.signId }}</div>
            <div class="gender">性别: {{ user.gender }}</div>
            <div class="token">
              本次登录令牌
              <a-icon type="eye" v-if="showToken" @click="showToken = false" />
              <a-icon type="eye-invisible" v-else @click="showToken = true" />
              :
              {{
                showToken ? user.token : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
              }}
            </div>
            加入组织：
            <div class="organizations">
              <div
                class="organization"
                v-for="o in user.organizations"
                :key="o.oid"
              >
                {{ o.organizationType || "未知" }}: {{ o.organizationName }}
                {{ "[" + o.roleName + "]" }}
              </div>
            </div>
          </div>
          <div>操作列表：</div>
          <div class="operations">
            <div :class="['notice', 'operation', theme]" @click="notice">
              通知列表
            </div>
            <div
              :class="['admin', 'operation', theme]"
              v-if="user.organizations.indexOf('root')"
              @click="admin"
            >
              管理
            </div>
            <div
              :class="['sign_out', 'operation', theme]"
              @click="signOutHandle"
            >
              登出
            </div>
          </div>
        </div>
        <div :class="['box_right', theme]">
          待办:
          <a-timeline class="todoList">
            <a-timeline-item
              v-for="t in showTodo"
              :key="t.id"
              :color="t.finish ? 'green' : 'blue'"
              :class="['todo', theme]"
              @click="todoLink(t.source)"
              >{{ t.value }} <a-icon type="link" />
            </a-timeline-item>
          </a-timeline>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import Axios from "axios";
export default {
  name: "home",
  data() {
    return {
      todo: [],
      showToken: false
    };
  },
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
  mounted() {
    Axios.get(`todo/todo/AccountAllTodo?token=${this.token}`).then(d => {
      if (!d.data.success) {
        return;
      }
      this.todo = d.data.data;
    });
    window.document.title = this.user.name;
  },
  watch: {
    user() {
      window.document.title = this.user.name;
    }
  },
  methods: {
    ...mapMutations(["signOut"]),
    notice() {
      this.$router.push("/notices");
    },
    admin() {
      this.$router.push("/admin");
    },
    signOutHandle() {
      Axios.get(`user_center/account/${this.user.signId}/signOut`).then(d => {
        this.$router.push("/");
        if (d.data) {
          this.signOut(d.data);
        }
      });
    },
    todoLink(ref) {
      Axios.get(`notice/notice/todoRef?ref=${ref}`).then(res => {
        if (res.data.success) {
          this.$router.push({
            path: `/notice/${res.data.data}/todo`,
            query: { back: "/home" }
          });
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    }
  }
};
</script>
<style lang="less" scoped>
@import "../../assets/theme.less";
.dark {
  color: @d-fg;
  background: @d-bg2;
}
.light {
  color: @l-fg;
  background: @l-bg2;
}
.home_body {
  display: flex;
  width: 100%;
  height: calc(~"100vh - 65px");
  .home {
    width: 1000px;
    margin: auto;
    padding: 30px;
    border-radius: 5px;
    div {
      margin: 5px 0;
    }
    .name {
      font-size: 28px;
    }
    .box {
      display: flex;
      .box_left {
        width: 600px;
        float: left;
        .info {
          padding: 10px;
          border-radius: 5px;
          margin: 15px 0;
          height: 200px;
          overflow-y: auto;
          .organization {
            margin: 5px 10px;
          }
        }
        .operations {
          display: flex;
          margin-bottom: 0;
          .operation {
            width: 100px;
            height: 100px;
            line-height: 100px;
            text-align: center;
            font-size: 18px;
            margin: 5px;
            border-radius: 5px;
            cursor: pointer;
          }
          .light:hover {
            background: @l-bg4;
          }
          .dark:hover {
            background: @d-bg4;
          }
        }
      }
      .box_right {
        width: 360px;
        float: right;
        margin: 20px 15px 0;
        border-radius: 5px;
        padding: 10px;
        height: 355px;
        .todoList {
          overflow-y: auto;
          height: 325px;
          padding: 15px 0;
          .todo {
            padding: 0 0 5px;
            margin: 0 15px;
            background: #0000;
            cursor: pointer;
          }
          .dark:hover {
            color: @d-bg5;
          }
          .light:hover {
            color: @l-bg5;
          }
        }
        .finish {
          text-decoration: line-through;
          color: @l-bg5;
        }
      }
    }
  }
}
</style>
