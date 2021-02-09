<template>
  <div class="body">
    <div class="role" v-for="role in roles" :key="role.role">
      <div class="title">{{ role.roleName }}({{ role.aids.length }})</div>
      <div class="members">
        <div class="member" v-for="member in role.aids" :key="member.id">
          {{ member.name }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import { mapState } from "vuex";
export default {
  name: "List",
  data() {
    return {
      members: []
    };
  },
  computed: {
    ...mapState({
      token: state => state.token
    }),
    id() {
      return +this.$route.params.id;
    },
    roles() {
      let roles = {};
      this.members.forEach(member => {
        if (!member.roles.length) {
          let max = ~(1 << 31);
          if (roles[max]) {
            roles[max].push({
              id: member.signId,
              name: member.name
            });
          } else {
            roles[max] = {
              role: max,
              oid: this.id,
              roleName: "其他成员",
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
          let aids = roles[role.role] ? roles[role.role] : [];
          aids.push({
            id: member.signId,
            name: member.name
          });
          roles[role.role] = {
            ...role,
            aid: undefined,
            aids
          };
        });
      });
      return roles;
    }
  },
  watch: {
    id() {
      this.getMembers();
    }
  },
  methods: {
    getMembers() {
      if (!this.id) {
        return;
      }
      Axios.get(`/organization/${this.id}/memberInfo?token=${this.token}`).then(
        res => {
          if (res.data.success) {
            this.members = res.data.data;
            this.members = this.members.map(member => {
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
    }
    .members {
      display: flex;
      flex-wrap: wrap;
      .member {
        width: 100px;
        height: 100px;
        background: #8882;
        text-align: center;
        border-radius: 5px;
        margin: 5px;
        cursor: default;
      }
    }
  }
}
</style>
