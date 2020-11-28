<template>
  <div id="topMenu">
    <router-link to="/">Home</router-link>
    <router-link to="/about">About</router-link>
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
  height: 50px;
  background: #555;
  color: #000;
  padding: 0px 300px 0;
}
a {
  color: white;
  font-size: 30px;
  margin: 0 10px;
  &:hover {
    color: #ccc;
  }
}
</style>
