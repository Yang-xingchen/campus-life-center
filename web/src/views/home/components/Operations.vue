<template>
  <div>
    <div class="title">
      操作列表
    </div>
    <div class="operations">
      <div :class="['notice', 'operation']" @click="notice">
        通知列表
      </div>
      <div
        :class="['admin', 'operation']"
        v-if="user.organizations[1] && user.organizations[1].roles[0]"
        @click="admin"
      >
        系统管理
      </div>
      <div :class="['sign_out', 'operation']" @click="signOutHandle">
        登出
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import Axios from "axios";
export default {
  name: "Operation",
  computed: {
    ...mapState({
      user: state => state.user
    })
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
      Axios.get(`/account/${this.user.signId}/signOut`).then(d => {
        this.$router.push("/");
        if (d.data) {
          this.signOut(d.data);
        }
      });
    }
  }
};
</script>

<style lang="less" scoped>
.title {
  font-size: 18px;
}
.operations {
  display: flex;
  .operation {
    width: 100px;
    height: 100px;
    background: #8882;
    line-height: 100px;
    text-align: center;
    font-size: 18px;
    margin: 5px;
    border-radius: 5px;
    cursor: pointer;
    &:hover {
      background: #8886;
    }
  }
}
</style>
