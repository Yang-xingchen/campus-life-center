<template>
  <div class="home_body">
    <div class="home" v-if="user">
      <div class="name">您好，{{ user.name }}:</div>
      <div class="box">
        <div class="box_left">
          <div class="info">
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
            <div class="notice operation" @click="notice">通知列表</div>
            <div
              class="admin operation"
              v-if="user.organizations.indexOf('root')"
              @click="admin"
            >
              管理
            </div>
            <div class="sign_out operation" @click="signOutHandle">登出</div>
          </div>
        </div>
        <div class="box_right">
          TODO:
          <a-timeline class="todoList">
            <a-timeline-item
              v-for="t in showTodo"
              :key="t.id + ' ' + t.nid"
              :color="t.isFinish ? 'green' : 'blue'"
              class="todo"
              >{{ t.value }}
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
      token: state => state.token
    }),
    showTodo() {
      let todos = this.todo.filter(t => {
        return t.isAdd || t.isTop;
      });
      return [...todos.filter(t => t.isTop), ...todos.filter(t => !t.isTop)];
    }
  },
  mounted() {
    Axios.get("notice/todo/todoByToken/" + this.token).then(d => {
      if (!d.data.success) {
        return;
      }
      this.todo = d.data.data;
    });
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
      Axios.get("user_center/account/" + this.user.signId + "/signOut").then(
        d => {
          this.$router.push("/");
          if (d.data) {
            this.signOut(d.data);
          }
        }
      );
    }
  }
};
</script>
<style lang="less" scoped>
.home_body {
  display: flex;
  width: 100%;
  height: calc(~"100vh - 65px");
  .home {
    width: 1000px;
    background: rgba(255, 255, 255, 0.2);
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
          background: rgba(255, 255, 255, 0.2);
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
            background: rgba(255, 255, 255, 0.2);
            cursor: pointer;
            &:hover {
              background: rgba(255, 255, 255, 0.4);
            }
          }
        }
      }
      .box_right {
        width: 360px;
        float: right;
        margin: 20px 15px 0;
        border-radius: 5px;
        padding: 10px;
        background: rgba(255, 255, 255, 0.2);
        height: 355px;
        .todoList {
          overflow-y: auto;
          height: 325px;
          padding: 15px 0;
          .todo {
            padding: 0 0 5px;
            margin: 0 15px;
            color: #fff;
          }
        }
        .finish {
          text-decoration: line-through;
          color: rgba(255, 255, 255, 0.5);
        }
      }
    }
  }
}
</style>
