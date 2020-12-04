<template>
  <div :class="[theme, 'main']">
    <top-menu />
    <router-view id="main" />
  </div>
</template>

<script>
import TopMenu from "./components/TopMenu";

export default {
  name: "App",
  components: {
    TopMenu
  },
  data() {
    return {
      theme: "default"
    };
  }
};
</script>

<style lang="less">
@import "assets/theme/default.less";
.main {
  padding: 0;
  margin: 0;
  height: 100%;
  width: 100%;
  position: absolute;
}
#topMenu {
  margin: 0;
  padding: 0;
  position: fixed;
  width: 100%;
  height: 50px;
}
#main {
  top: 50px;
  position: relative;
}
</style>
