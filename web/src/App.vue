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
import { mapActions } from "vuex";

export default {
  name: "App",
  components: {
    TopMenu
  },
  data() {
    return {
      theme: "default"
    };
  },
  mounted() {
    this.getAccountByToken();
  },
  methods: {
    ...mapActions(["getAccountByToken"])
  }
};
</script>

<style lang="less" scope>
@import "assets/theme/default.less";
.main {
  padding: 0;
  margin: 0;
  width: 100%;
  position: absolute;
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
