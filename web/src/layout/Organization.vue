<template>
  <div class="box">
    <div class="content">
      <Info :id="id" :large="info_large" />
      <div class="menu">
        <span
          :class="select === 'overview' ? 'select' : 'selectable'"
          @click="menu('overview')"
          >总览</span
        >
        <span
          :class="select === 'member' ? 'select' : 'selectable'"
          @click="menu('member')"
          >成员</span
        >
        <span
          :class="select === 'child' ? 'select' : 'selectable'"
          @click="menu('child')"
          >下属组织</span
        >
      </div>
      <a-divider />
      <router-view />
    </div>
  </div>
</template>

<script>
import Info from "../components/OrganizationInfo";
export default {
  name: "Organization",
  components: { Info },
  data() {
    return {};
  },
  computed: {
    id() {
      return +this.$route.params.id;
    },
    info_large() {
      return this.select === "overview";
    },
    select() {
      let path = this.$route.path.split("/" + this.id)[1];
      if (path.startsWith("/")) {
        path = path.substring(1);
      }
      path = path.split("/")[0];
      return path || "overview";
    }
  },
  methods: {
    menu(item) {
      if (item !== this.select) {
        this.$router.push(`/organization/${this.id}/${item}`);
      }
    }
  }
};
</script>

<style lang="less" scoped>
.box {
  display: flex;
  width: 100%;
  height: calc(~"100vh - 65px");
  .content {
    width: 1500px;
    height: 800px;
    margin: auto;
    padding: 15px 30px;
    border-radius: 5px;
    background: #8882;
    .menu {
      height: 40px;
      background: #8882;
      margin: 20px 0 0;
      border-radius: 5px;
      display: flex;
      span {
        margin: auto 0;
        font-size: 20px;
        border-right: #888 1px solid;
        padding: 0 15px;
        cursor: default;
        &:last-child {
          border-right: #0000 1px solid;
        }
      }
      .select {
        color: #88f;
      }
      .selectable {
        cursor: pointer;
        &:hover {
          color: #888;
        }
      }
    }
  }
}
</style>
