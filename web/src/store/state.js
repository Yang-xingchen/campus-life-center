import { init_publish } from "../util";
export default {
  user: {},
  token: window.localStorage.getItem("token"),
  id: "",
  pub_key: "",
  notice: {},
  theme: "dark",
  publish: init_publish()
};
