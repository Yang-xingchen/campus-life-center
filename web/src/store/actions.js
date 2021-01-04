import Axios from "axios";

export default {
  getSignInInfo(context) {
    Axios.get("user_center/account/signInInfo")
      .then(d => {
        context.commit("setSignInId", d.data.signInId);
        context.commit("setPubKey", d.data.pub_key);
      })
      .catch(res => {
        console.log("get key err");
        console.log(res);
      });
  },
  getAccountByToken(context) {
    const token = window.localStorage.getItem("token");
    if (token) {
      Axios.get("user_center/account/info/" + token).then(d => {
        if (d.data.success) {
          context.commit("signIn", d.data.data);
        }
      });
    }
  },
  getNotice(context, id) {
    if (context.state.notice.id === id) {
      return;
    }
    const token = context.state.token;
    Axios.get("notice/notice/" + id + "?token=" + token).then(res => {
      let notice = res.data.data;
      notice.todoList = notice.todoList.sort((a, b) => a.id - b.id);
      context.commit("setNotice", notice);
    });
  }
};
