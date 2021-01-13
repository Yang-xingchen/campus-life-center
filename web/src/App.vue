<template>
  <div :class="[theme, 'main']">
    <top-menu />
    <div id="main">
      <router-view />
    </div>
  </div>
</template>

<script>
import TopMenu from "./components/TopMenu";
import { mapActions, mapState, mapMutations } from "vuex";

export default {
  name: "App",
  components: {
    TopMenu
  },
  mounted() {
    this.changeTheme(window.localStorage.getItem("theme") || "dark");
    this.getAccountByToken();
  },
  methods: {
    ...mapMutations(["changeTheme"]),
    ...mapActions(["getAccountByToken"])
  },
  computed: {
    ...mapState({
      theme: state => state.theme
    })
  }
};
</script>

<style lang="less" scope>
@import "assets/theme.less";
.dark {
  background: @d-bg;
}
.light {
  background: @l-bg;
}
.main {
  padding: 0;
  margin: 0;
  width: 100%;
  position: absolute;
  transition: 0.5s;
}
#topMenu {
  margin: 0;
  padding: 0;
  position: fixed;
  width: 100%;
  height: 60px;
}
#main {
  position: relative;
  padding-top: 65px;
  min-height: calc(~"100vh");
}
</style>
