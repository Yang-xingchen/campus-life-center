import { init_publish } from "../util";
export default {
  user: {},
  token: window.localStorage.getItem("token"),
  signInId: "",
  pub_key: "",
  notice: {},
  theme: "dark",
  publish: init_publish()
};
