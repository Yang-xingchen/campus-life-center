<template>
  <div>
    <top-menu />
    <router-view id="main" />
  </div>
</template>

<script>
import TopMenu from "./components/TopMenu";
import { mapMutations } from "vuex";
import Axios from "axios";

export default {
  name: "App",
  components: {
    TopMenu
  },
  mounted() {
    Axios.get("user_center/account/signInId")
      .then(d => {
        this.setSignId(d.data);
      })
      .catch(res => {
        console.log("get key err");
        console.log(res);
      });
  },
  methods: {
    ...mapMutations(["setSignId"])
  }
};
</script>

<style lang="less">
div {
  padding: 0;
  margin: 0;
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
