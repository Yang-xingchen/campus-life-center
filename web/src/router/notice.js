export default {
  path: "/notice/:id",
  name: "notice",
  component: () => import("../layout/Notice.vue"),
  props: {
    default: true
  },
  children: [
    {
      path: "",
      component: () => import("../views/notice/Content.vue")
    },
    {
      path: "content",
      component: () => import("../views/notice/Content.vue")
    },
    {
      path: "edit",
      component: () => import("../views/notice/Edit.vue")
    },
    {
      path: "accountAnalysis",
      component: () => import("../views/notice/AccountAnalysis.vue")
    },
    {
      path: "todoAnalysis",
      component: () => import("../views/notice/TodoAnalysis.vue")
    },
    {
      path: "todo",
      component: () => import("../views/notice/Todo.vue")
    },
    {
      path: "info/:ref",
      component: () => import("../views/notice/InfoCollect/InfoCollect.vue")
    },
    {
      path: "info/res/:ref",
      component: () =>
        import("../views/notice/InfoCollectResult/InfoCollectResult.vue")
    },
    {
      path: "file",
      component: () => import("../views/notice/File.vue")
    },
    {
      path: "comment",
      component: () => import("../views/notice/Comment.vue")
    },
    {
      path: "update_log",
      component: () => import("../views/notice/UpdateLog.vue")
    },
    {
      path: "attribute",
      component: () => import("../views/notice/Attribute.vue")
    },
    {
      path: "*",
      redirect: "content"
    }
  ]
};
