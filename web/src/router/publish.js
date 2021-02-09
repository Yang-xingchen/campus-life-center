export default {
  path: "/publish",
  name: "publish",
  component: () => import("../layout/Publish.vue"),
  children: [
    {
      path: "",
      component: () => import("../views/publish/Attribute.vue")
    },
    {
      path: "attribute",
      component: () => import("../views/publish/Attribute.vue")
    },
    {
      path: "content",
      component: () => import("../views/publish/Content.vue")
    },
    {
      path: "account",
      component: () => import("../views/publish/account/Account.vue")
    },
    {
      path: "todo",
      component: () => import("../views/publish/Todo.vue")
    },
    {
      path: "collect/:id",
      component: () => import("../views/publish/collect/Collect.vue")
    }
  ]
};
