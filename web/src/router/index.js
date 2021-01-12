import Vue from "vue";
import VueRouter from "vue-router";
import Index from "../views/index/Index.vue";
import NotFound from "../views/NotFound.vue";

Vue.use(VueRouter);
const routes = [
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
    },
    children: [
      {
        path: "",
        component: () => import("../views/notice/components/Content.vue")
      },
      {
        path: "content",
        component: () => import("../views/notice/components/Content.vue")
      },
      {
        path: "edit",
        component: () => import("../views/notice/components/Edit.vue")
      },
      {
        path: "analysis",
        component: () => import("../views/notice/components/Analysis.vue")
      },
      {
        path: "todo",
        component: () => import("../views/notice/components/Todo.vue")
      },
      {
        path: "info/:ref",
        component: () =>
          import("../views/notice/components/InfoCollect/InfoCollect.vue")
      },
      {
        path: "info/res/:ref",
        component: () =>
          import(
            "../views/notice/components/InfoCollectResult/InfoCollectResult.vue"
          )
      },
      {
        path: "comment",
        component: () => import("../views/notice/components/Comment.vue")
      },
      {
        path: "update_log",
        component: () => import("../views/notice/components/UpdateLog.vue")
      },
      {
        path: "attribute",
        component: () => import("../views/notice/components/Attribute.vue")
      }
    ]
  },
  {
    path: "/*",
    name: "404",
    component: NotFound
  }
];

export default new VueRouter({
  mode: "hash",
  base: process.env.BASE_URL,
  routes
});
