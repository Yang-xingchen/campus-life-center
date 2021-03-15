<template>
  <div class="body">
    <a-select v-model="select"
              @blur="addSelect"
              @change="addSelect">
      <a-select-option v-for="account in inviteable"
                       :key="account.id">{{
        account.name
      }}</a-select-option>
    </a-select>
    <a-divider />
    <div class="operation">
      <a-button type="primary"
                @click="invite"
                :disabled="!selectList.length || !hasPermission">确认</a-button>
    </div>
    <div class="select_box">
      <div class="select"
           v-for="s in selectList"
           :key="s">
        <div class="id">{{ inviteable[s].id }}</div>
        <div class="name">{{ inviteable[s].name }}</div>
        <div class="operation">
          <a-icon type="close-circle"
                  class="oper"
                  @click="delSelect(s)" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Invite",
  data () {
    return {
      text: "",
      select: "",
      selectList: [],
      admin: [],
      member: []
    };
  },
  computed: {
    ...mapState({
      token: state => state.token,
      ao: state => state.user.organizations
    }),
    id () {
      return +this.$route.params.id;
    },
    inviteable () {
      let inviteable = {};
      for (let account of this.admin) {
        inviteable[account.id] = account;
      }
      for (let account of this.member) {
        delete inviteable[account.id];
      }
      return inviteable;
    },
    hasPermission () {
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
  methods: {
    addSelect () {
      if (this.select === "") {
        return;
      }
      this.selectList.push(this.select);
      this.selectList = [...new Set(this.selectList)];
      this.select = "";
    },
    delSelect (item) {
      this.selectList = this.selectList.filter(s => s !== item);
    },
    invite () {
      this.request({
        method: "post",
        url: `organization/${this.id}/invite?token=${this.token}`,
        data: this.selectList
      }).then(success => {
        if (success) {
          this.$notification["success"]({
            message: "成功"
          });
          this.selectList = [];
        }
      });
    },
    getAdmin () {
      this.request({
        method: "get",
        url: `/account/adminMember?token=${this.token}`
      }).then(admin => (this.admin = admin));
    },
    getMembers () {
      if (!this.id) {
        return;
      }
      this.request({
        method: "get",
        url: `/organization/${this.id}/memberInfo?token=${this.token}`
      }).then(member => (this.member = member));
    }
  },
  created () {
    this.getMembers();
    this.getAdmin();
  }
};
</script>

<style lang="less" scoped>
.body {
  display: flex;
  flex-direction: column;
  .operation {
    display: flex;
    justify-content: flex-end;
  }
  .select_box {
    overflow-y: auto;
    display: flex;
    flex-direction: column;
    .select {
      background: #8882;
      border-radius: 5px;
      margin-top: 10px;
      padding: 5px 10px;
      font-size: 18px;
      display: flex;
      cursor: default;
      &:hover {
        background: #8884;
      }
      * {
        margin: auto 0;
      }
      .id {
        width: 150px;
      }
      .name {
        flex: auto;
      }
      .operation {
        width: 50px;
        .oper {
          cursor: pointer;
        }
      }
    }
  }
}
</style>
