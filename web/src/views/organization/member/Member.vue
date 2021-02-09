<template>
  <div>
    <div class="body">
      <div class="menu">
        <div
          :class="['item', select === 'list' ? 'select' : '']"
          @click="link('list')"
        >
          成员列表
        </div>
        <div
          :class="['item', select === 'invite' ? 'select' : '']"
          v-if="permissions.indexOf('member') != -1"
          @click="link('invite')"
        >
          邀请成员
        </div>
        <div
          :class="['item', select === 'apply' ? 'select' : '']"
          v-if="permissions.indexOf('member') != -1"
          @click="link('apply')"
        >
          处理申请
        </div>
        <div
          :class="['item', select === 'add' ? 'select' : '']"
          v-if="permissions.indexOf('account') != -1"
          @click="link('add')"
        >
          注册成员
        </div>
      </div>
      <router-view class="body" />
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Member",
  data() {
    return {
      permissions: []
    };
  },
  computed: {
    ...mapState({
      organizations: state => state.user.organizations
    }),
    id() {
      return +this.$route.params.id;
    },
    select() {
      let path = this.$route.path.split("member")[1];
      if (path.startsWith("/")) {
        path = path.substring(1);
      }
      path = path.split("/")[0];
      return path || "list";
    }
  },
  watch: {
    id() {
      this.getPermission();
    },
    organizations() {
      this.getPermission();
    }
  },
  methods: {
    link(item) {
      this.$router.push(`/organization/${this.id}/member/${item}`);
    },
    getPermission() {
      let organization = (this.organizations || []).filter(
        o => o.id === this.id
      );
      if (!organization.length) {
        return [];
      }
      let permission = [];
      organization[0].roles.forEach(r => {
        permission = [...permission, ...r.permissions];
      });
      this.permissions = permission.map(p => p.name);
    }
  },
  created() {
    this.getPermission();
  }
};
</script>

<style lang="less" scoped>
.body {
  height: 625px;
  display: flex;
  .menu {
    flex: 1;
    background: #8882;
    border-radius: 5px;
    margin-right: 10px;
    .item {
      background: #8882;
      font-size: 24px;
      padding: 5px 10px;
      cursor: pointer;
      &:hover {
        background: #8884;
      }
    }
    .select {
      background: #8884;
    }
  }
  .body {
    flex: 4;
    overflow-y: auto;
  }
}
</style>
