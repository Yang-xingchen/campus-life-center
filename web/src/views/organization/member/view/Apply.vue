<template>
  <div class="body">
    <div class="apply" v-for="account in applys" :key="account.signId">
      <div class="text id">{{ account.signId }}</div>
      <div class="text name">{{ account.name }}</div>
      <a-button class="button" type="primary" @click="agree(account.signId)"
        >同意</a-button
      >
    </div>
    <div class="apply not_data" v-show="!applys.length">
      没有新的申请
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Apply",
  data() {
    return {
      applys: []
    };
  },
  computed: {
    ...mapState({
      token: state => state.token,
      ao: state => state.user.organizations
    }),
    id() {
      return +this.$route.params.id;
    },
    applyable() {
      let o = this.ao[this.id];
      if (!o) {
        return false;
      }
      for (let r in o.roles) {
        for (let p in o.roles[r].permissions) {
          if (p === "103") {
            return true;
          }
        }
      }
      return false;
    }
  },
  watch: {
    applyable() {
      this.getApplys();
    }
  },
  methods: {
    getApplys() {
      if (!this.token && !this.applyable) {
        return;
      }
      this.request({
        method: "get",
        url: `/organization/${this.id}/applyList?token=${this.token}`
      }).then(applys => (this.applys = applys));
    },
    agree(aid) {
      this.request({
        method: "post",
        url: `/organization/${this.id}/invite?token=${this.token}`,
        data: [aid]
      }).then(success => {
        if (success) {
          this.$notification["success"]({
            message: "成功"
          });
          this.getApplys();
        }
      });
    }
  },
  created() {
    this.getApplys();
  }
};
</script>

<style lang="less" scoped>
.body {
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  .apply {
    font-size: 24px;
    background: #8882;
    border-radius: 5px;
    margin-bottom: 5px;
    padding: 5px 10px;
    display: flex;
    .text {
      margin: auto 0;
      padding: 0 10px;
    }
    .id {
      width: 300px;
    }
    .button {
      margin-left: auto;
    }
    &:hover {
      background: #8884;
    }
  }
  .not_data {
    color: #888;
    text-align: center;
  }
}
</style>
