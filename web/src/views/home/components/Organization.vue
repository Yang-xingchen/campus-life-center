<template>
  <div>
    <div class="title">
      组织
      <a-icon type="link" class="link" @click="clickTitle" />
    </div>
    <div class="organizations">
      <OrganizationCard
        v-for="o in organizations"
        :key="o.id"
        :organization="o"
      />
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import OrganizationCard from "../../../components/OrganizationCard";
export default {
  name: "Organization",
  components: { OrganizationCard },
  computed: {
    ...mapState({
      organizations: state => state.user.organizations
    })
  },
  methods: {
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
  max-height: 350px;
  overflow-y: auto;
}
</style>
