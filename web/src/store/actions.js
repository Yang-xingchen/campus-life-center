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
  }
};
