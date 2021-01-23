import Vue from "vue";
import VueRouter from "vue-router";
import Index from "../views/index/Index.vue";
import NotFound from "../views/NotFound.vue";

Vue.use(VueRouter);
const routes = [
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
    component: () => import("../views/noticeCards/NoticeCards.vue"),
    meta: {
      title: "通知列表"
    }
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
        path: "file",
        component: () => import("../views/notice/components/File.vue")
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
      },
      {
        path: "*",
        redirect: "content"
      }
    ]
  },
  {
    path: "/publish",
    name: "publish",
    component: () => import("../views/publish/Publish.vue"),
    children: [
      {
        path: "",
        component: () => import("../views/publish/components/Attribute.vue")
      },
      {
        path: "attribute",
        component: () => import("../views/publish/components/Attribute.vue")
      },
      {
        path: "content",
        component: () => import("../views/publish/components/Content.vue")
      },
      {
        path: "account",
        component: () =>
          import("../views/publish/components/account/Account.vue")
      },
      {
        path: "todo",
        component: () => import("../views/publish/components/Todo.vue")
      },
      {
        path: "collect/:id",
        component: () =>
          import("../views/publish/components/collect/Collect.vue")
      }
    ]
  },
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
