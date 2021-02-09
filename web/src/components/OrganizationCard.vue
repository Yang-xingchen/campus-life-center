<template>
  <div
    class="organization"
    :style="'width: ' + width + 'px'"
    @click="click(organization.id)"
  >
    <div class="type">{{ organization.type || "未知" }}</div>
    <div class="name">{{ organization.name }}</div>
    <div class="roles">
      <span v-for="r in organization.roles" :key="r.id">{{ r.name }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "OrganizationCard",
  props: {
    organization: Object,
    width: {
      type: Number,
      required: false,
      default: 340
    }
  },
  methods: {
    click(id) {
      this.$router.push(`/organization/${id}`);
    }
  }
};
</script>

<style lang="less" scoped>
.organization {
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
</style>
