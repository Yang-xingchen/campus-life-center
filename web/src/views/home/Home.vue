<template>
  <div class="home_body">
    <div class="home">
      <div class="name">您好，{{ user.name }}:</div>
      <div class="info">
        <div class="gender">性别: {{ user.gender }}</div>
        <div class="token">token: {{ user.token }}</div>
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
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import Axios from "axios";
export default {
  name: "home",
  computed: mapState({
    user: state => state.user
  }),
  methods: {
    ...mapMutations(["signOut"]),
    notice() {
      this.$router.push("/notice");
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
    .info {
      background: rgba(255, 255, 255, 0.2);
      padding: 10px;
      border-radius: 5px;
      margin: 15px 0;
      .organization {
        margin: 5px 10px;
      }
    }
    .operations {
      display: flex;
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
}
</style>
