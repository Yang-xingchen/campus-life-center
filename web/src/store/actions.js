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
  }
};
