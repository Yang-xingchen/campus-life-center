import Vue from "vue";
import VueRouter from "vue-router";
import Base from "./base";
import Organization from "./organization";
import Notice from "./notice";
import Publish from "./publish";
import Setting from "./setting";
import NotFound from "../views/NotFound.vue";

Vue.use(VueRouter);
const routes = [
  ...Base,
  Organization,
  Notice,
  Publish,
  Setting,
  {
    path: "/*",
    name: "404",
    component: NotFound,
    meta: {
      title: "404"
    }
  }
];
let route = new VueRouter({
  mode: "hash",
  base: process.env.BASE_URL,
  routes
});
route.beforeEach((to, from, next) => {
  window.document.title = to.meta.title || "通知管理系统";
  next();
});
export default route;
