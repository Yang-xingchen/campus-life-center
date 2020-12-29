<template>
  <div id="topMenu">
    <router-link to="/">Home</router-link>
    <router-link to="/about">About</router-link>
    <router-link v-if="user" to="/notice">通知列表</router-link>
    <router-link v-if="user && user.organizations.indexOf('root')" to="/admin">
      管理
    </router-link>
    <router-link v-if="!user" to="/signIn">登录</router-link>
    <router-link v-else to="/home">{{ user.name }}</router-link>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "topMenu",
  computed: mapState({
    user: state => state.user
  })
};
</script>

<style lang="less" scoped>
#topMenu {
  background: rgba(0, 0, 0, 0.1);
  color: #000;
  box-shadow: 0 3px 5px #333;
  z-index: 999;
}
a {
  position: relative;
  top: 10px;
  font-size: 30px;
  margin: 0 10px;
  &:first-of-type {
    margin-left: 300px;
  }
  &:last-of-type {
    float: right;
    margin-right: 300px;
  }
}
</style>
