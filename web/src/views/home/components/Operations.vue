<template>
  <div>
    <div class="title">
      操作列表
    </div>
    <div class="operations">
      <div :class="['notice', 'operation']" @click="notice">
        通知列表
      </div>
      <div :class="['setting', 'operation']" @click="setting">
        帐号设置
      </div>
      <div :class="['sign_out', 'operation']" @click="signOutHandle">
        登出
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  name: "Operation",
  computed: {
    ...mapState({
      user: state => state.user,
      token: state => state.token
    })
  },
  methods: {
    ...mapMutations(["signOut"]),
    notice() {
      this.$router.push("/notices");
    },
    setting() {
      this.$router.push("/setting");
    },
    signOutHandle() {
      this.request({
        method: "get",
        url: `/account/${this.user.id}/signOut?token=${this.token}`
      }).then(success => {
        this.signOut(success);
        this.$router.push("/");
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
