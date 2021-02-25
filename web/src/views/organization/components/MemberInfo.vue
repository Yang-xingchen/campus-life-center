<template>
  <div class="member_box">
    <div class="title">
      成员列表({{ members.length }})
      <a-icon type="link" class="link" @click="link('member')" />
    </div>
    <div class="members">
      <div class="member" v-for="member in members" :key="member.signId">
        {{ member.name }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Member",
  props: {
    id: Number
  },
  data() {
    return {
      members: []
    };
  },
  watch: {
    id() {
      this.getMembers();
    }
  },
  methods: {
    link(item) {
      if (item !== this.select) {
        this.$router.push(`/organization/${this.id}/${item}`);
      }
    },
    getMembers() {
      if (!this.id) {
        return;
      }
      this.request({
        method: "get",
        url: `/organization/${this.id}/member`
      }).then(members => {
        this.members = members.map(member => {
          return {
            ...member,
            roles: member.organizations ? member.organizations[0].roles : [],
            organizations: undefined
          };
        });
      });
    }
  },
  created() {
    this.getMembers();
  }
};
</script>

<style lang="less" scoped>
.member_box {
  padding: 10px;
  display: flex;
  flex-direction: column;
  height: 430px;
  .title {
    font-size: 18px;
    .link {
      margin-left: 5px;
      cursor: pointer;
      &:hover {
        color: #888;
      }
    }
  }
  .members {
    display: flex;
    flex-wrap: wrap;
    overflow-y: auto;
    flex: auto;
    .member {
      background: #8882;
      width: 75px;
      height: 75px;
      margin: 5px;
      text-align: center;
      cursor: default;
    }
  }
}
</style>
