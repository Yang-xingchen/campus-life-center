import Axios from "axios";

export default {
  getSignId(context) {
    Axios.get("user_center/account/signInId")
      .then(d => {
        context.setSignId(d.data);
      })
      .catch(res => {
        console.log("get key err");
        console.log(res);
      });
  }
};
