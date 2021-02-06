<template>
  <div>
    加入组织：
    <div class="organizations">
      <div class="organization" v-for="o in organizations" :key="o.id">
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
  }
};
</script>

<style lang="less" scoped>
.organizations {
  display: flex;
  flex-wrap: wrap;
  height: 300px;
  overflow-y: auto;
  .organization {
    width: 340px;
    height: 150px;
    background: #8882;
    margin: 5px;
    border-radius: 5px;
    padding: 5px 0;
    text-align: center;
    cursor: default;
    .type {
      font-size: 16px;
    }
    .name {
      font-size: 24px;
      margin: 15px;
    }
    .roles {
      font-size: 16px;
    }
  }
}
</style>
