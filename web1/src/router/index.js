import Vue from "vue";
import VueRouter from "vue-router";
import Index from "../views/index/Index.vue";

Vue.use(VueRouter);
const route = [
  {
    path: "/",
    name: "Index",
    component: Index
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue")
  },
  {
    path: "/signIn",
    name: "sign_in",
    component: () => import("../views/signIn/SignIn.vue")
  },
  {
    path: "/home",
    name: "home",
    component: () => import("../views/home/Home.vue")
  },
  {
    path: "/notices",
    name: "notices",
    component: () => import("../views/noticeCards/NoticeCards.vue")
  },
  {
    path: "/notice/:id",
    name: "notice",
    component: () => import("../views/notice/Notice.vue"),
    props: {
      default: true
    }
  }
];

export default new VueRouter({
  mode: "history",
  route
});
