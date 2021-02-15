<template>
  <div class="body">
    <div class="role" v-for="role in roles" :key="role.id">
      <div class="title">
        <div class="title" v-show="!role.edit">
          {{ role.name }}({{ role.aids.length }})
          <a-icon
            type="edit"
            class="edit"
            @click="edit(role.id)"
            v-if="role.id !== flag"
          />
        </div>
        <div class="edit" v-show="role.edit">
          <a-input v-model="role.new_name" />
        </div>
        <a-button
          class="button"
          v-show="
            role.add_account.length + role.del_account.length || role.edit
          "
          @click="reset(role.id)"
          >重置</a-button
        >
        <a-button
          type="primary"
          class="button"
          v-show="
            role.add_account.length + role.del_account.length || role.edit
          "
          @click="update(role.id)"
          >完成</a-button
        >
      </div>
      <div class="permission">
        <a-select
          class="select"
          v-model="role.permission_id"
          mode="tags"
          placeholder="权限"
          v-show="role.edit"
          @change="forceUpdate"
        >
          <a-select-option v-for="p in permissions" :key="p + ''">{{
            permission_map[p]
          }}</a-select-option>
        </a-select>
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
          :disabled="role.name === '' || !role.accounts.length"
          >完成</a-button
        >
      </div>
      <div class="permission">
        <a-select
          class="select"
          v-model="role.permissions_id"
          mode="tags"
          placeholder="权限"
        >
          <a-select-option v-for="p in permissions" :key="p + ''">{{
            permission_map[p]
          }}</a-select-option>
        </a-select>
      </div>
      <Members
        :members="[]"
        :allMembers="members"
        :addMembers="role.accounts"
        :delMembers="[]"
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
      show_addrole: false,
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
              new_name: "",
              edit: false,
              add_account: [],
              del_account: [],
              aids: [
                {
                  id: member.signId,
                  name: member.name
                }
              ],
              permission: [],
              permission_id: []
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
            permission_id: role.permissions.map(p => p.id + ""),
            new_name: role.name,
            edit: false,
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
          ps.push(o.roles[r].permissions[p].id);
        }
      }
      if (ps.indexOf(205)) {
        ps.push(204);
      }
      if (ps.indexOf(204)) {
        ps.push(203);
      }
      if (ps.indexOf(203)) {
        ps.push(202);
      }
      if (ps.indexOf(202)) {
        ps.push(201);
      }
      return [...new Set(ps)].sort();
    },
    addable() {
      for (let p of this.permissions) {
        if (p === 102) {
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
    forceUpdate() {
      this.$forceUpdate();
    },
    getMembers() {
      if (!this.id) {
        return;
      }
      this.request({
        method: "get",
        url: `/organization/${this.id}/memberInfo?token=${this.token}`
      }).then(members => {
        this.members = members.map(member => {
          return {
            ...member,
            roles: member.organizations[0].roles,
            organizations: undefined
          };
        });
        this.$forceUpdate();
      });
    },
    update(id) {
      let role = this.roles.filter(r => r.id === id)[0];
      role.edit = false;
      let pids = role.permissions.map(p => p.id + "");
      let addPids = role.permission_id.filter(p => pids.indexOf(p) === -1);
      let delPids = pids.filter(p => role.permission_id.indexOf(p) === -1);
      if (
        !role.add_account.length &&
        !role.del_account.length &&
        !addPids.length &&
        !delPids.length &&
        role.new_name === role.name
      ) {
        this.$forceUpdate();
        return;
      }
      let data = {
        oid: this.id,
        id,
        name: role.new_name === role.name ? undefined : role.new_name,
        addPids,
        delPids,
        addAids: role.add_account.map(a => a.id),
        delAids: role.del_account.map(a => a.id)
      };
      this.request({
        method: "post",
        url: `/role/${this.id}/${id}/update?token=${this.token}`,
        data
      }).then(success => {
        if (success) {
          this.getMembers();
        }
      });
    },
    addRole() {
      this.show_addrole = false;
      this.role.aids = this.role.accounts.map(a => a.id);
      delete this.role.accounts;
      this.role.permissions = this.role.permissions_id.map(id => {
        return { id };
      });
      delete this.role.permissions_id;
      this.request({
        method: "post",
        url: `/role/${this.id}/add?token=${this.token}`,
        data: this.role
      }).then(success => {
        if (success) {
          this.getMembers();
          this.role = init_role();
          this.show_addrole = false;
        }
      });
    },
    roleAddMember(rid, account) {
      if (rid === this.flag) {
        this.role.accounts.push(account);
      } else {
        let role = this.roles.filter(r => r.id === rid)[0];
        if (role.del_account.filter(m => m.id === account.id).length) {
          role.del_account = role.del_account.filter(m => m.id !== account.id);
        } else {
          role.add_account.push(account);
        }
      }
      this.$forceUpdate();
    },
    roleDelMember(rid, account) {
      if (rid === this.flag) {
        this.role.accounts = this.role.accounts.filter(
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
    edit(id) {
      this.roles.filter(r => r.id === id)[0].edit = true;
      this.$forceUpdate();
    },
    reset(rid) {
      let role = this.roles.filter(r => r.id === rid)[0];
      role.add_account = [];
      role.del_account = [];
      role.permission_id = role.permissions.map(p => p.id + "");
      role.edit = false;
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
      .title {
        .edit {
          margin-left: 10px;
          color: #888;
          cursor: pointer;
          &:hover {
            color: #8888;
          }
        }
      }
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
