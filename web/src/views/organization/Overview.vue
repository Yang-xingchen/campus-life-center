<template>
  <div>
    <div class="body">
      <div class="child"><Child :id="id" /></div>
      <div class="right">
        <div class="operation">
          <div class="opers" v-show="token">
            <a-button
              class="subscribe"
              v-show="!alertSubscribe"
              @click="subscribe"
              >关注</a-button
            >
            <a-button
              class="subscribe"
              type="dashed"
              v-show="alertSubscribe"
              @click="cancelSubscribe"
              >取消关注</a-button
            >
            <a-button
              class="join"
              type="primary"
              v-show="!isInvite && !alertBelong"
              @click="apply"
              >申请加入</a-button
            >
            <a-button
              class="join"
              type="primary"
              v-show="isInvite && !alertBelong"
              @click="apply"
              >同意加入</a-button
            >
            <a-button
              class="join"
              type="danger"
              v-show="alertBelong"
              @click="exit"
              >退出</a-button
            >
          </div>
        </div>
        <div class="member"><Member :id="id" /></div>
      </div>
    </div>
  </div>
</template>

<script>
import Child from "./components/ChildInfo";
import Member from "./components/MemberInfo";
import { mapState, mapMutations, mapActions } from "vuex";
export default {
  name: "Overview",
  components: { Child, Member },
  data() {
    return {
      isInvite: false
    };
  },
  computed: {
    ...mapState({
      belong: state => state.user.organizations,
      subscribeList: state => state.subscribes,
      token: state => state.token
    }),
    id() {
      return +this.$route.params.id;
    },
    alertBelong() {
      if (!this.belong) {
        return false;
      }
      return this.belong[this.id];
    },
    alertSubscribe() {
      if (!this.subscribeList) {
        return false;
      }
      return this.subscribeList.filter(oid => oid === this.id).length;
    }
  },
  methods: {
    ...mapMutations(["subscribes"]),
    ...mapActions(["getAccountByToken"]),
    subscribe() {
      this.request({
        method: "get",
        url: `/notice/organization/${this.id}/subscribe?token=${this.token}`
      }).then(success => {
        if (success) {
          this.request({
            method: "get",
            url: `/notice/organization/subscribes?token=${this.token}`
          }).then(subscribes => this.subscribes(subscribes));
        }
      });
    },
    cancelSubscribe() {
      this.request({
        method: "get",
        url: `/notice/organization/${this.id}/cancelSubscribe?token=${this.token}`
      }).then(success => {
        if (success) {
          this.request({
            method: "get",
            url: `/notice/organization/subscribes?token=${this.token}`
          }).then(subscribes => this.subscribes(subscribes));
        }
      });
    },
    apply() {
      this.request({
        method: "get",
        url: `/organization/${this.id}/apply?token=${this.token}`
      }).then(success => {
        if (success) {
          this.getAccountByToken();
        }
      });
    },
    exit() {
      this.request({
        method: "get",
        url: `/organization/${this.id}/exit?token=${this.token}`
      }).then(success => {
        if (success) {
          this.getAccountByToken();
        }
      });
    },
    getIsInvite() {
      this.request({
        method: "get",
        url: `/organization/${this.id}/isInvite?token=${this.token}`
      }).then(isInvite => (this.isInvite = isInvite));
    }
  },
  created() {
    this.getIsInvite();
  }
};
</script>

<style lang="less" scoped>
.body {
  display: flex;
  height: 505px;
  .child {
    flex: 2;
    background: #8882;
    border-radius: 5px;
    margin-right: 20px;
  }
  .right {
    flex: 1;
    .operation {
      background: #8882;
      border-radius: 5px;
      margin-bottom: 10px;
      padding: 5px 0;
      height: 65px;
      display: flex;
      .opers {
        margin: auto;
        display: flex;
        width: 350px;
        .subscribe {
          flex: 1;
          margin: 0 5px;
          &:hover {
            flex: 9;
          }
        }
        .join {
          flex: 3;
          margin: 0 5px;
        }
      }
    }
    .member {
      background: #8882;
      border-radius: 5px;
      height: 430px;
    }
  }
}
</style>
