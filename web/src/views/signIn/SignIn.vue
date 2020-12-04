<template>
  <div>
    <div id="sign_in_form">
      <label class="title">登录</label>
      <SignInInput text="ID" :type="'text'" v-model:value="uid" />
      <SignInInput text="密码" :type="'password'" v-model:value="pwd" />
      <div class="input">
        <input id="rememberMe" type="checkbox" v-model="rememberMe" />
        <label>记住我</label>
      </div>
      <label class="err">{{ err }}</label>
      <div id="sign_in_button" class="button" @click="handleSignInButton">
        登录
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import SignInInput from "./components/SignInInput";
import { mapMutations, mapState, mapActions } from "vuex";

export default {
  name: "SignIn",
  components: { SignInInput },
  data() {
    return {
      uid: "",
      pwd: "",
      rememberMe: false,
      err: ""
    };
  },
  computed: {
    ...mapState(["signInId"])
  },
  methods: {
    handleSignInButton() {
      Axios.post("user_center/account/signIn", {
        aid: this.uid,
        password: this.pwd,
        cookie: this.signInId
      })
        .then(this.handleSignIn)
        .catch(res => {
          console.log(res);
          alert("err!");
        });
    },
    handleSignIn(res) {
      console.log(res);
      if (!res.data.success) {
        this.err = res.data.message;
        return;
      }
      this.signIn(res.data.data);
      this.$router.back();
    },
    ...mapMutations(["signIn"]),
    ...mapActions(["getSignInInfo"])
  },
  mounted() {
    this.getSignInInfo();
  }
};
</script>

<style lang="less" scoped>
#sign_in_form {
  position: fixed;
  width: 300px;
  height: 400px;
  border: #eee 3px solid;
  border-radius: 5px;
  top: 120px;
  left: 0;
  right: 0;
  display: inline-block;
  margin: 0 auto;
  background: rgba(255, 255, 255, 0.25);
  padding: 20px;
}
.title {
  display: block;
  text-align: center;
  font-size: 60px;
  margin: 30px 0 10px;
}
.err {
  color: red;
  font-size: 24px;
  text-align: center;
  display: block;
  margin-top: 35px;
}
#sign_in_button {
  background: rgba(0, 0, 0, 0.25);
  padding: 20px 0;
  border-radius: 3px;
  font-size: 20px;
  text-align: center;
  position: absolute;
  left: 20px;
  right: 20px;
  bottom: 20px;
  &:hover {
    background: rgba(0, 0, 0, 0.5);
  }
}
</style>
