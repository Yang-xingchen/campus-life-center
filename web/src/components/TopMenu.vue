<template>
  <div id="topMenu" :class="theme">
    <router-link to="/"><a-icon type="home" />主页</router-link>
    <router-link v-if="user && user.id" to="/notices"
      ><a-icon type="bell" />通知列表</router-link
    >
    <router-link to="/organizations"><a-icon type="team" />组织</router-link>
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
    })
  },
  data() {
    return {
      theme_sw: this.theme === "light"
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
    ...mapMutations(["changeTheme"])
  }
};
</script>

<style lang="less" scoped>
#topMenu {
  box-shadow: 0 3px 5px #333;
  z-index: 999;
  display: flex;
  .theme {
    margin: auto;
    margin-right: 10px;
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
