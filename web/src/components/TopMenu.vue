<template>
  <div id="topMenu" :class="theme">
    <router-link to="/"><a-icon type="home" />主页</router-link>
    <router-link v-if="user && user.id" to="/notices"
      ><a-icon type="bell" />通知列表</router-link
    >
    <router-link to="/organizations"><a-icon type="team" />组织</router-link>
    <div class="space"></div>
    <a-input
      class="search"
      v-model="search_text"
      @pressEnter="search"
      placeholder="搜索"
      v-if="show_search"
    />
    <a-switch
      v-model="theme_sw"
      class="theme"
      checked-children="亮色"
      un-checked-children="暗色"
    />
    <router-link v-if="!user || !user.id" to="/signIn"
      ><a-icon type="user" />登录</router-link
    >
    <router-link v-else to="/home"
      ><a-icon type="user" /> {{ user.name }}</router-link
    >
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  name: "topMenu",
  computed: {
    ...mapState({
      user: state => state.user,
      theme: state => state.theme
    }),
    show_search() {
      return this.$route.path !== "/searchResult";
    }
  },
  data() {
    return {
      theme_sw: this.theme === "light",
      search_text: ""
    };
  },
  watch: {
    theme_sw() {
      this.changeTheme(this.theme_sw ? "light" : "dark");
    },
    theme() {
      this.theme_sw = this.theme === "light";
    }
  },
  methods: {
    ...mapMutations(["changeTheme"]),
    search() {
      this.$router.push({
        path: "/searchResult",
        query: {
          keyword: this.search_text
        }
      });
      this.search_text = "";
    }
  }
};
</script>

<style lang="less" scoped>
#topMenu {
  box-shadow: 0 3px 5px #333;
  z-index: 999;
  display: flex;
  .theme {
    margin: auto 10px;
  }
  .space {
    margin: auto;
    height: 10px;
  }
  .search {
    margin: auto 10px;
    width: 200px;
    background: #8888 !important;
    color: inherit !important;
    &:focus {
      width: 500px;
    }
  }
  a {
    position: relative;
    font-size: 26px;
    line-height: 60px;
    margin: 0 5px;
    padding: 0 5px;
    float: left;
    overflow: hidden;
    white-space: nowrap;
    &:first-of-type {
      margin-left: 300px;
    }
    &:last-of-type {
      margin-right: 300px;
    }
  }
}
</style>
