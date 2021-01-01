import Vue from "vue";
import App from "./App";
import store from "./store";
import router from "./router";
// import Antd from "ant-design-vue";
// import "ant-design-vue/dist/antd.css";
Vue.config.productionTip = false;

// Vue.use(Antd);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
