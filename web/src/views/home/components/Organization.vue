<template>
  <div>
    <div class="title">
      组织
      <a-icon type="link" class="link" @click="clickTitle" />
    </div>
    <div class="organizations">
      <div
        class="organization"
        v-for="o in organizations"
        :key="o.id"
        @click="click(o.id)"
      >
        <div class="type">{{ o.type || "未知" }}</div>
        <div class="name">{{ o.name }}</div>
        <div class="roles">
          <span v-for="r in o.roles" :key="r.id">{{ r.name }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Organization",
  computed: {
    ...mapState({
      organizations: state => state.user.organizations
    })
  },
  methods: {
    click(id) {
      this.$router.push(`/organization/${id}`);
    },
    clickTitle() {
      this.$router.push(`/organizations`);
    }
  }
};
</script>

<style lang="less" scoped>
.title {
  font-size: 18px;
  .link {
    cursor: pointer;
    &:hover {
      color: #888;
    }
  }
}
.organizations {
  display: flex;
  flex-wrap: wrap;
  max-height: 300px;
  overflow-y: auto;
  .organization {
    width: 340px;
    height: 150px;
    background: #8882;
    margin: 5px;
    border-radius: 5px;
    padding: 5px 0;
    text-align: center;
    cursor: pointer;
    &:hover {
      background: #8884;
    }
    .type {
      font-size: 16px;
    }
    .name {
      font-size: 28px;
      margin: 15px;
    }
    .roles {
      font-size: 16px;
    }
  }
}
</style>
