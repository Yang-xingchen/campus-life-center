import Axios from "axios";
import notification from "ant-design-vue/es/notification";

export default {
  getSignInInfo(context) {
    Axios.get("/account/signInInfo").then(res => {
      if (res.data.success) {
        context.commit("setSignInId", res.data.data.signInId);
        context.commit("setPubKey", res.data.data.pubKey);
      } else {
        notification["error"]({
          message: res.data.code,
          description: "获取登录信息失败"
        });
      }
    });
  },
  getAccountByToken(context) {
    const token = window.localStorage.getItem("token");
    if (token) {
      Axios.get(`/account/info/${token}`).then(res => {
        if (res.data.success) {
          context.commit("signIn", res.data.data);
        } else {
          notification["error"]({
            message: res.data.code,
            description: "自动登录失败"
          });
        }
      });
    }
  },
  getNotice(context, id) {
    if (context.state.notice.id === id) {
      return;
    }
    const token = context.state.token;
    Axios.get(`/notice/${id}?token=${token}`).then(res => {
      if (res.data.success) {
        let notice = res.data.data;
        notice.todoList = notice.todoList.sort((a, b) => a.id - b.id);
        context.commit("setNotice", notice);
      } else {
        context.commit("setNotice", {});
        notification["error"]({
          message: res.data.code,
          description: res.data.message
        });
      }
    });
  },
  reloadNotice(context, id) {
    const token = context.state.token;
    Axios.get(`/notice/${id}?token=${token}`).then(res => {
      if (res.data.success) {
        let notice = res.data.data;
        notice.todoList = notice.todoList.sort((a, b) => a.id - b.id);
        context.commit("setNotice", notice);
      } else {
        notification["error"]({
          message: res.data.code,
          description: res.data.message
        });
      }
    });
  }
};
