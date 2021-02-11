export default {
  path: "/organization/:id",
  name: "organization",
  component: () => import("../layout/Organization.vue"),
  props: {
    default: true
  },
  children: [
    {
      path: "",
      component: () => import("../views/organization/Overview.vue")
    },
    {
      path: "overview",
      component: () => import("../views/organization/Overview.vue")
    },
    {
      path: "member",
      component: () => import("../views/organization/member/Member.vue"),
      children: [
        {
          path: "",
          component: () =>
            import(
              "../views/organization/member/view/memberList/MemberList.vue"
            )
        },
        {
          path: "list",
          component: () =>
            import(
              "../views/organization/member/view/memberList/MemberList.vue"
            )
        },
        {
          path: "invite",
          component: () =>
            import("../views/organization/member/view/Invite.vue")
        },
        {
          path: "apply",
          component: () => import("../views/organization/member/view/Apply.vue")
        },
        {
          path: "add",
          component: () => import("../views/organization/member/view/Add.vue")
        }
      ]
    },
    {
      path: "child",
      component: () => import("../views/organization/Child.vue")
    },
    {
      path: "addChild",
      component: () => import("../views/organization/AddChild.vue")
    }
  ]
};
