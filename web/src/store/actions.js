import { request } from "../util";

export default {
  getSignInInfo(context) {
    request({
      method: "get",
      url: `/account/signInInfo`
    }).then(data => {
      context.commit("setSignInId", data.signInId);
      context.commit("setPubKey", data.pubKey);
    });
  },
  getAccountByToken(context) {
    const token = window.localStorage.getItem("token");
    if (token) {
      request({
        method: "get",
        url: `/account/info/${token}`
      }).then(user => context.commit("signIn", user));
      request({
        method: "get",
        url: `/notice/organization/subscribes?token=${token}`
      }).then(subscribes => context.commit("subscribes", subscribes));
    }
  },
  getNotice(context, id) {
    if (context.state.notice.id === id) {
      return;
    }
    const token = context.state.token;
    request({
      method: "get",
      url: `/notice/${id}?token=${token}`
    }).then(notice => {
      notice.todoList = notice.todoList.sort((a, b) => a.id - b.id);
      context.commit("setNotice", notice);
    });
  },
  reloadNotice(context, id) {
    const token = context.state.token;
    request({
      method: "get",
      url: `/notice/${id}?token=${token}`
    }).then(notice => {
      notice.todoList = notice.todoList.sort((a, b) => a.id - b.id);
      context.commit("setNotice", notice);
    });
  }
};
