<template>
  <div>
    <div class="member_box">
      <div class="title">成员列表({{ members.length }})</div>
      <div class="members">
        <div class="member" v-for="member in members" :key="member.signId">
          {{ member.name }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
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
    getMembers() {
      Axios.get(`/organization/${this.id}/member`).then(res => {
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
  .title {
    font-size: 18px;
  }
  .members {
    display: flex;
    flex-wrap: wrap;
    overflow-y: auto;
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
