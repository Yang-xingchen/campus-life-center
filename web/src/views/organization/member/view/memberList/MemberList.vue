<template>
  <div class="body">
    <div class="role" v-for="role in roles" :key="role.id">
      <div class="title">
        {{ role.name }}({{ role.aids.length }})
        <a-button
          class="button"
          v-show="
            (role.add_account || []).length + (role.del_account || []).length
          "
          @click="reset(role.id)"
          >重置</a-button
        >
        <a-button
          type="primary"
          class="button"
          v-show="
            (role.add_account || []).length + (role.del_account || []).length
          "
          >完成</a-button
        >
      </div>
      <Members
        :members="role.aids"
        :allMembers="members"
        :addMembers="role.add_account"
        :delMembers="role.del_account"
        :editable="addable && role.id !== flag"
        @add="roleAddMember(role.id, $event)"
        @del="roleDelMember(role.id, $event)"
      />
    </div>
    <div class="role add_box" v-show="show_addrole">
      <div class="title">
        <a-input v-model="role.name" placeholder="名称" />
        <a-button class="button" @click="role = init_role()">重置</a-button>
        <a-button class="button" @click="show_addrole = false">关闭</a-button>
        <a-button
          type="primary"
          class="button"
          @click="addRole"
          :disabled="role.name === '' || !role.aids.length"
          >完成</a-button
        >
      </div>
      <div class="permission">
        <a-select
          class="select"
          v-model="role._permissions_id"
          mode="tags"
          placeholder="权限"
        >
          <a-select-option v-for="p in permissions" :key="p.id + ''">{{
            permission_map[p.id]
          }}</a-select-option>
        </a-select>
      </div>
      <Members
        :members="[]"
        :allMembers="members"
        :addMembers="role.add_account"
        :delMembers="role.del_account"
        :editable="addable"
        @add="roleAddMember(flag, $event)"
        @del="roleDelMember(flag, $event)"
      />
    </div>
    <div
      class="role add"
      @click="show_addrole = true"
      v-show="addable && !show_addrole"
    >
      <a-icon type="plus" />
    </div>
  </div>
</template>

<script>
import { init_role } from "../../../../../util";
import Axios from "axios";
import { mapState } from "vuex";
import Members from "./components/Members";
const permission_map = {
  1: "系统账户",
  2: "系统管理",
  101: "组织信息",
  102: "下属组织",
  103: "成员管理",
  201: "1星重要度",
  202: "2星重要度",
  203: "3星重要度",
  204: "4星重要度",
  205: "5星重要度"
};
const flag = ~(1 << 31);
export default {
  name: "List",
  components: { Members },
  data() {
    return {
      members: [],
      show_addrole: true,
      role: init_role(),
      show_add: {},
      show_new_add: false,
      permission_map,
      flag
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
    roles() {
      let roles = {};
      this.members.forEach(member => {
        member.id = member.signId;
        if (!member.roles.length) {
          if (roles[this.flag]) {
            roles[this.flag].aids.push({
              id: member.id,
              name: member.name
            });
          } else {
            roles[this.flag] = {
              id: this.flag,
              oid: this.id,
              name: "其他成员",
              aids: [
                {
                  id: member.signId,
                  name: member.name
                }
              ],
              permission: []
            };
          }
        }
        member.roles.forEach(role => {
          let aids = roles[role.id] ? roles[role.id].aids : [];
          aids.push({
            id: member.id,
            name: member.name
          });
          roles[role.id] = {
            ...role,
            add_account: [],
            del_account: [],
            aid: undefined,
            aids
          };
          this.show_add[role.id] = false;
        });
      });
      return Object.values(roles).sort((a, b) => a.id - b.id);
    },
    permissions() {
      if (!this.ao) {
        return [];
      }
      let o = this.ao[this.id];
      let ps = [];
      if (!o) {
        return [];
      }
      for (let r in o.roles) {
        for (let p in o.roles[r].permissions) {
          ps.push(o.roles[r].permissions[p]);
        }
      }
      return [...new Set(ps)];
    },
    addable() {
      for (let p of this.permissions) {
        if (p.id === 102) {
          return true;
        }
      }
      return false;
    }
  },
  watch: {
    id() {
      this.getMembers();
    }
  },
  methods: {
    init_role,
    getMembers() {
      if (!this.id) {
        return;
      }
      Axios.get(`/organization/${this.id}/memberInfo?token=${this.token}`).then(
        res => {
          if (res.data.success) {
            this.members = res.data.data.map(member => {
              return {
                ...member,
                roles: member.organizations[0].roles,
                organizations: undefined
              };
            });
          } else {
            this.$notification["error"]({
              message: res.data.code,
              description: res.data.message
            });
          }
        }
      );
    },
    addRole() {
      this.show_addrole = false;
    },
    roleAddMember(rid, account) {
      if (rid === this.flag) {
        this.role.add_account.push(account);
      } else {
        let role = this.roles.filter(r => r.id === rid)[0];
        role.add_account.push(account);
        role.del_account = role.del_account.filter(m => m.id !== account.id);
      }
      this.$forceUpdate();
    },
    roleDelMember(rid, account) {
      if (rid === this.flag) {
        this.role.add_account = this.role.add_account.filter(
          m => m.id !== account.id
        );
      } else {
        let role = this.roles.filter(r => r.id === rid)[0];
        if (role.aids.filter(m => m.id === account.id).length) {
          if (role.del_account.filter(m => m.id === account.id).length) {
            role.del_account = role.del_account.filter(
              m => m.id !== account.id
            );
          } else {
            role.del_account.push(account);
          }
        } else {
          role.add_account = role.add_account.filter(m => m.id !== account.id);
        }
      }
      this.$forceUpdate();
    },
    reset(rid) {
      let role = this.roles.filter(r => r.id === rid)[0];
      role.add_account = [];
      role.del_account = [];
      this.$forceUpdate();
    }
  },
  created() {
    this.getMembers();
  }
};
</script>

<style lang="less" scoped>
.body {
  display: flex;
  flex-direction: column;
  .role {
    background: #8882;
    border-radius: 5px;
    padding: 10px;
    margin-bottom: 10px;
    .title {
      font-size: 24px;
      display: flex;
      .button {
        margin-left: 10px;
      }
    }
    .permission {
      margin-top: 10px;
      margin-bottom: 5px;
      .select {
        width: 100%;
      }
    }
  }
  .add {
    padding: 10px;
    border: 2px #888 dashed;
    cursor: pointer;
    text-align: center;
    line-height: 50px;
    font-size: 50px;
    color: #888;
    &:hover {
      border: 2px #888 solid;
      background: #8882;
    }
  }
}
</style>
