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
      <div class="opers">
        <a-button class="remember" @click="clickSignIn(true)"
          >记住我并登录</a-button
        >
        <a-button class="signIn" type="primary" @click="clickSignIn(false)">
          仅登录
        </a-button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapMutations, mapState, mapActions } from "vuex";
import jsencrypt from "jsencrypt";
import { getKey, decode } from "../../util";
import _ from "lodash";

export default {
  name: "SignIn",
  data() {
    return {
      uid: "",
      pwd: "",
      err: "",
      key: getKey()
    };
  },
  computed: {
    ...mapState(["signInId", "pub_key"])
  },
  methods: {
    handleSignInButton(rememberMe) {
      const encode = new jsencrypt();
      encode.setPublicKey(this.pub_key);
      this.request({
        method: "post",
        url: `/account/signIn`,
        data: {
          aid: this.uid,
          password: encode.encrypt(this.pwd),
          signInId: this.signInId,
          key: encode.encrypt(this.key)
        }
      }).then(user => {
        user.token = decode(this.key, user.token);
        this.setKey(this.key);
        if (rememberMe) {
          window.localStorage.setItem("token", user.token);
        }
        this.signIn(user);
        this.$router.back();
      });
    },
    ...mapMutations(["signIn", "setKey"]),
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
  background: #8884;
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
  .opers {
    display: flex;
    margin-top: 30px;
    .remember {
      margin-right: 5px;
      flex: 1;
      overflow: hidden;
      &:hover {
        flex: 4;
      }
    }
    .signIn {
      flex: 1;
      overflow: hidden;
      &:hover {
        flex: 4;
      }
    }
  }
}
</style>
