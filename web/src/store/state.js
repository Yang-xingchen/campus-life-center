import { init_publish } from "../util";
export default {
  user: {},
  subscribes: [],
  token: window.localStorage.getItem("token"),
  signInId: "",
  pub_key: "",
  key: "",
  notice: {},
  theme: "dark",
  publish: init_publish()
};
