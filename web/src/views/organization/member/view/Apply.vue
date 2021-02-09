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
import Axios from "axios";
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
      token: state => state.token
    }),
    id() {
      return +this.$route.params.id;
    }
  },
  watch: {
    id() {
      this.getApplys();
    }
  },
  methods: {
    getApplys() {
      if (!this.token) {
        return;
      }
      Axios.get(`/organization/${this.id}/applyList?token=${this.token}`).then(
        res => {
          if (res.data.success) {
            this.applys = res.data.data;
          } else {
            this.$notification["error"]({
              message: res.data.code,
              description: res.data.message
            });
          }
        }
      );
    },
    agree(aid) {
      Axios.post(`/organization/${this.id}/invite?token=${this.token}`, [
        aid
      ]).then(res => {
        if (res.data.success && res.data.data) {
          this.$notification["success"]({
            message: "成功"
          });
          this.getApplys();
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
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
