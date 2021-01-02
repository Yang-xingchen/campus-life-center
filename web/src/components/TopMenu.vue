<template>
  <div id="topMenu">
    <router-link to="/"><a-icon type="home" />主页</router-link>
    <router-link v-if="user && user.signId" to="/notices"
      ><a-icon type="bell" />通知列表</router-link
    >
    <router-link
      v-if="user && user.organizations && user.organizations.indexOf('root')"
      to="/admin"
      ><a-icon type="pie-chart" />管理
    </router-link>
    <router-link v-if="!user || !user.signId" to="/signIn"
      ><a-icon type="user" />登录</router-link
    >
    <router-link v-else to="/home"
      ><a-icon type="user" /> {{ user.name }}</router-link
    >
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "topMenu",
  computed: {
    ...mapState({
      user: state => state.user
    })
  }
};
</script>

<style lang="less" scoped>
#topMenu {
  background: rgba(0, 0, 0, 0.1);
  color: #000;
  box-shadow: 0 3px 5px #333;
  z-index: 999;
  a {
    position: relative;
    font-size: 26px;
    line-height: 60px;
    margin: 0 5px;
    padding: 0 5px;
    float: left;
    &:first-of-type {
      margin-left: 300px;
    }
    &:last-of-type {
      float: right;
      margin-right: 300px;
    }
  }
}
</style>
