import Index from "../views/index/Index.vue";
export default [
  {
    path: "/",
    name: "Index",
    component: Index,
    meta: {
      title: "通知管理系统"
    }
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
    component: () => import("../views/signIn/SignIn.vue"),
    meta: {
      title: "登录"
    }
  },
  {
    path: "/home",
    name: "home",
    component: () => import("../views/home/Home.vue")
  },
  {
    path: "/notices",
    name: "notices",
    component: () => import("../layout/NoticeCards.vue"),
    meta: {
      title: "通知列表"
    }
  },
  {
    path: "/organizations",
    name: "organizations",
    component: () => import("../views/organizations/Organizations.vue"),
    meta: {
      title: "组织列表"
    }
  },
  {
    path: "/waitPublish",
    name: "waitPublish",
    component: () => import("../views/waitPublish/WaitPublish.vue")
  }
];
