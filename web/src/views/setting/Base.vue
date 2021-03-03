<template>
  <div class="body">
    <div class="item">
      <div class="title">id</div>
      <div class="value">
        {{ user.id }}
      </div>
    </div>
    <div class="item">
      <div class="title">姓名</div>
      <div class="value">
        <a-input v-model="update.name" />
      </div>
    </div>
    <div class="item">
      <div class="title">性别</div>
      <div class="value">
        <a-radio-group v-model="update.gender">
          <a-radio-button :value="1">男</a-radio-button>
          <a-radio-button :value="0">女</a-radio-button>
          <a-radio-button :value="2">保密</a-radio-button>
        </a-radio-group>
      </div>
    </div>
    <div class="item">
      <div class="title">创建日期</div>
      <div class="value">
        {{ format_date(user.createData) }}
      </div>
    </div>
    <div class="item">
      <div class="title">原密码</div>
      <div class="value">
        <a-input-password
          v-model="old_pwd"
          placeholder="请输入原密码以确认身份"
        />
      </div>
    </div>
    <div class="item">
      <div class="title">新密码</div>
      <div class="value">
        <a-input-password v-model="new_pwd" placeholder="如需更新密码请输入" />
      </div>
    </div>
    <div class="item">
      <div class="title">确认新密码</div>
      <div class="value">
        <a-input-password v-model="new_pwd2" placeholder="请重复输入以确认" />
      </div>
    </div>
    <div class="item">
      <div class="title" />
      <div class="value">
        <a-tooltip :title="err"
          ><a-button type="primary" @click="submit" :disabled="err !== ''"
            >提交</a-button
          ></a-tooltip
        >
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import { format_date, getKey, decode } from "../../util";
import jsencrypt from "jsencrypt";
export default {
  name: "base",
  computed: {
    ...mapState({
      user: state => state.user,
      token: state => state.token,
      pub_key: state => state.pub_key,
      signInId: state => state.signInId
    }),
    err() {
      if (this.new_pwd === this.new_pwd2 && this.new_pwd !== "") {
        return this.old_pwd === "" ? "未输入原密码" : "";
      } else if (this.new_pwd !== this.new_pwd2) {
        return "两次密码不一样";
      }
      if (this.old_pwd === this.new_pwd && this.old_pwd !== "") {
        return "新旧密码相同";
      }
      if (this.update.name !== this.user.name && this.update.name !== "") {
        return this.old_pwd === "" ? "未输入原密码" : "";
      } else if (this.update.name === "") {
        return "未输入姓名";
      }
      if (this.update.gender !== this.toUpdateGenderMap[this.user.gender]) {
        return this.old_pwd === "" ? "未输入原密码" : "";
      }
      return "未存在更新";
    }
  },
  watch: {
    user() {
      this.updateUpdate();
    }
  },
  data() {
    return {
      update: {},
      old_pwd: "",
      new_pwd: "",
      new_pwd2: "",
      toUpdateGenderMap: {
        男: 1,
        女: 0,
        保密: 2
      },
      key: getKey()
    };
  },
  methods: {
    format_date,
    ...mapActions(["getSignInInfo"]),
    ...mapMutations(["signIn", "signOut"]),
    updateUpdate() {
      this.update = {
        name: this.user.name
      };
      this.update.gender = this.toUpdateGenderMap[this.user.gender];
    },
    submit() {
      const encode = new jsencrypt();
      encode.setPublicKey(this.pub_key);
      let password = encode.encrypt(this.new_pwd);
      let data = {
        ...this.update,
        password,
        originalPassword: encode.encrypt(this.old_pwd)
      };
      this.request({
        method: "post",
        url: `/account/${this.user.id}/update?token=${this.token}`,
        data
      }).then(success => {
        this.signOut(success);
        if (success) {
          this.$notification.success({
            message: "密码已更改"
          });
          this.request({
            method: "post",
            url: "/account/signIn",
            data: {
              aid: this.user.id,
              password,
              signInId: this.signInId,
              key: encode.encrypt(this.key)
            }
          }).then(user => {
            user.token = decode(this.key, user.token);
            this.setKey(this.key);
            if (window.localStorage.getItem("token")) {
              window.localStorage.setItem("token", user.token);
            }
            this.signIn(user);
            this.$router.push("/home");
          });
        } else {
          this.$notification.error({
            message: "失败"
          });
        }
      });
    }
  },
  created() {
    this.getSignInInfo();
    this.updateUpdate();
  }
};
</script>

<style lang="less" scoped>
.body {
  background: #8882;
  border-radius: 5px;
  padding: 100px 120px;
  .item {
    display: flex;
    font-size: 24px;
    height: 50px;
    .title {
      flex: 1;
      margin: auto 0;
      text-align: right;
      margin-right: 15px;
    }
    .value {
      flex: 3;
      margin: auto 0;
      .select {
        width: 100%;
      }
    }
  }
}
</style>
