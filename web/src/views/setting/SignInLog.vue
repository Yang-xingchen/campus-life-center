<template>
  <div>
    <div class="item head">
      <div class="icon"></div>
      <div class="inTime">登录时间</div>
      <div class="ip">IP</div>
      <div class="source">登录方式</div>
      <div class="outTime">登出时间</div>
      <div class="opers">操作</div>
    </div>
    <div class="list">
      <div class="item" v-for="log in logs" :key="log.token">
        <div class="icon">
          <a-tooltip v-if="log.token === token" title="当前登录"
            ><a-icon type="info-circle"
          /></a-tooltip>
        </div>
        <div class="inTime">{{ format_date(log.signInTime) }}</div>
        <div class="ip">{{ log.ip }}</div>
        <div class="source">{{ signInType[log.source || 0] }}</div>
        <div class="outTime">
          {{ log.signOutTime ? format_date(log.signOutTime) : "" }}
        </div>
        <div class="opers">
          <span
            class="oper"
            v-show="!log.signOutTime"
            @click="signOutItem(log.token)"
            >下线</span
          >
        </div>
      </div>
    </div>
    <div class="headOper">
      <a-tooltip title="将除了本机外的登录情况全部下线">
        <a-button class="oper" @click="signOutOther">下线其他</a-button>
      </a-tooltip>
      <a-button type="primary" class="oper" @click="signOutAll"
        >全部下线</a-button
      >
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import { format_date } from "../../util";
const signInType = {
  0: "帐号密码",
  1: "已登录信息"
};
export default {
  name: "SignInLog",
  data() {
    return {
      logs: [],
      signInType
    };
  },
  computed: {
    ...mapState({
      token: state => state.token,
      id: state => state.user.id
    })
  },
  watch: {
    token() {
      this.getLogs();
    }
  },
  methods: {
    format_date,
    ...mapMutations(["signOut"]),
    getLogs() {
      if (!this.id || !this.token) {
        return;
      }
      this.request({
        method: "get",
        url: `/account/${this.id}/signInLog?token=${this.token}`
      }).then(logs => (this.logs = logs));
    },
    signOutHandle(url, f) {
      this.request({
        method: "get",
        url
      }).then(success => {
        if (success) {
          f();
          this.$notification.success({
            message: "已成功下线"
          });
        } else {
          this.$notification.error({
            message: "下线失败"
          });
        }
      });
    },
    signOutThisHandle() {
      this.signOut(true);
      this.$router.push("/");
    },
    signOutItem(token) {
      this.signOutHandle(`/account/${this.id}/signOut?token=${token}`, () => {
        if (token === this.token) {
          this.signOutThisHandle();
        } else {
          this.getLogs();
        }
      });
    },
    signOutOther() {
      this.signOutHandle(
        `/account/${this.id}/signOutOther?token=${this.token}`,
        () => this.getLogs()
      );
    },
    signOutAll() {
      this.signOutHandle(
        `/account/${this.id}/signOutAll?token=${this.token}`,
        () => this.signOutThisHandle()
      );
    }
  },
  created() {
    this.getLogs();
  }
};
</script>

<style lang="less" scoped>
.headOper {
  display: flex;
  margin-top: 10px;
  .oper {
    margin: 0 5px;
  }
  :first-of-type {
    margin-left: auto;
  }
}
.item {
  display: flex;
  text-align: center;
  background: #8882;
  border-radius: 5px;
  padding: 5px;
  font-size: 18px;
  margin: 5px;
  cursor: default;
  &:hover {
    background: #8884;
  }
  .icon {
    width: 35px;
    color: #888;
  }
  .inTime {
    flex: 3;
  }
  .ip {
    flex: 2;
  }
  .source {
    flex: 2;
  }
  .outTime {
    flex: 3;
  }
  .opers {
    flex: 3;
    .oper {
      color: #88f;
      cursor: pointer;
      &:hover {
        color: #88f8;
      }
    }
  }
}
.list {
  overflow-y: auto;
  height: 685px;
}
.head {
  background: #8884;
}
</style>
