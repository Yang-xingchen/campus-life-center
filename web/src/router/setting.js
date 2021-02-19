export default {
  path: "/setting",
  name: "setting",
  component: () => import("../layout/Setting.vue"),
  children: [
    {
      path: "",
      component: () => import("../views/setting/Base.vue")
    },
    {
      path: "base",
      component: () => import("../views/setting/Base.vue")
    }
  ]
};
