<template>
  <div>
    <div id="sign_in_form">
      <label class="title">登录</label>
      <a-input
        ref="userNameInput"
        v-model="uid"
        placeholder="登录id"
        class="input"
      >
        <a-icon slot="prefix" type="user" />
        <a-tooltip slot="suffix" :title="err" v-show="err !== ''">
          <a-icon type="info-circle" style="color: rgba(255,0,0,.45)" />
        </a-tooltip>
      </a-input>
      <a-input-password v-model="pwd" placeholder="登录密码" class="input">
        <a-icon slot="prefix" type="key" />
      </a-input-password>
      <a-checkbox @change="changeRememberMe" :checked="rememberMe"
        >记住我</a-checkbox
      >
      <a-button class="button" type="primary" block @click="clickSignIn">
        登录
      </a-button>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import { mapMutations, mapState, mapActions } from "vuex";
import jsencrypt from "jsencrypt";
import _ from "lodash";

export default {
  name: "SignIn",
  data() {
    return {
      uid: "",
      pwd: "",
      rememberMe: false,
      err: ""
    };
  },
  computed: {
    ...mapState(["signInId", "pub_key"])
  },
  methods: {
    changeRememberMe(e) {
      this.rememberMe = e.target.checked;
    },
    handleSignInButton() {
      const encode = new jsencrypt();
      encode.setPublicKey(this.pub_key);
      Axios.post("/account/signIn", {
        aid: this.uid,
        password: encode.encrypt(this.pwd),
        signInId: this.signInId
      })
        .then(res => {
          if (!res.data.success) {
            this.err = res.data.message;
            this.$notification["error"]({
              message: res.data.code,
              description: res.data.message
            });
            return;
          } else {
            this.$notification["error"]({
              message: res.data.code,
              description: res.data.message
            });
          }
          if (this.rememberMe) {
            window.localStorage.setItem("token", res.data.data.token);
          }
          this.signIn(res.data.data);
          this.$router.back();
        })
        .catch(res => {
          this.$notification["error"]({
            message: res.status,
            description: res.statusText
          });
        });
    },
    ...mapMutations(["signIn"]),
    ...mapActions(["getSignInInfo"])
  },
  created() {
    this.clickSignIn = _.debounce(this.handleSignInButton, 1000);
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
  .title {
    display: block;
    text-align: center;
    font-size: 60px;
    margin: 30px 0 10px;
  }
  .input {
    margin: 5px 0;
    border-radius: 5px;
  }
  .button {
    margin-top: 30px;
  }
}
</style>
