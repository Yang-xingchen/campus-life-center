<template>
  <div class="add-account">
    <a-textarea
      class="input"
      v-model="text"
      placeholder="输入或粘贴账户ID、姓名、性别、初始密码, 系统将自动进行识别"
    />
    <div class="account-box">
      <div class="oper item">
        <a-button
          class="button"
          type="primary"
          @click="submit"
          :disabled="!submitable"
          >提交</a-button
        >
      </div>
      <div class="head account">
        <div class="id">ID</div>
        <div class="name">名称</div>
        <div class="gender">性别</div>
        <div class="pwd">初始密码</div>
      </div>
      <div class="list">
        <div
          :class="['account', account.error ? 'err' : '']"
          v-for="account in accounts"
          :key="account.id"
        >
          <div class="id">{{ account.id }}</div>
          <div class="name">{{ account.name }}</div>
          <div class="gender">{{ account.gender }}</div>
          <div class="pwd">{{ account.password }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import _ from "lodash";
export default {
  name: "Apply",
  data() {
    return {
      text: "",
      divider: [" ", "\t", ",", "，"],
      genderMap: {
        男: "男",
        女: "女",
        保密: "保密",
        0: "女",
        1: "男",
        2: "保密"
      }
    };
  },
  computed: {
    ...mapState({
      token: state => state.token
    }),
    accounts() {
      let accounts = this.text.split("\n").map(this.handelLine);
      accounts = accounts.filter(a => a.id !== "");
      let data = {};
      accounts.forEach(a => {
        if (data[a.id]) {
          a.error = true;
        }
        data[a.id] = a;
        if (!a.id || !a.name || !a.password) {
          a.error = true;
        }
      });
      return accounts;
    },
    submitable() {
      if (!this.accounts.length) {
        return false;
      }
      for (let account of this.accounts) {
        if (account.error) {
          return false;
        }
      }
      return true;
    }
  },
  methods: {
    handelLine(t) {
      t = t.trim();
      if (t === "") {
        return { id: "" };
      }
      let ds = [""];
      let i = 0;
      do {
        ds = t.split(this.divider[i++]);
      } while (ds.length < 4 && i < this.divider.length);
      if (ds.length === 1) {
        return { id: "" };
      }
      return {
        id: ds[0].trim(),
        name: ds[1].trim(),
        gender: this.genderMap[ds[2].trim()]
          ? this.genderMap[ds[2].trim()]
          : "保密",
        password: ds[3].trim(),
        error: ds[4] && true
      };
    },
    handleSubmit() {
      const genderMap = {
        女: 0,
        男: 1,
        保密: 2
      };
      let data = this.accounts
        .filter(a => !a.error)
        .map(a => {
          return {
            id: a.id,
            name: a.name,
            gender: genderMap[a.gender],
            password: a.password
          };
        });
      this.request({
        method: "post",
        url: `/admin/addAccount?token=${this.token}`,
        data
      }).then(data => {
        let fail = Object.fromEntries(data[false].map(a => [a.id, a]));
        this.text = this.text
          .split("\n")
          .map(t => {
            if (fail[this.handelLine(t).id]) {
              let ds = [""];
              let i = 0;
              do {
                ds = t.split(this.divider[i++]);
              } while (ds.length < 4 && i < this.divider.length);
              return `${t}${this.divider[i - 1]}[错误]`;
            }
            return t;
          })
          .join("\n");
        if (!data[false].length) {
          this.$notification.success({
            message: "添加成功"
          });
        } else {
          this.$notification.error({
            message: "添加失败, 请确认数据后重新提交",
            description: "已标记有问题的数据"
          });
        }
      });
    }
  },
  created() {
    this.submit = _.debounce(this.handleSubmit, 3000, {
      leading: true,
      trailing: false
    });
  }
};
</script>

<style lang="less" scoped>
.add-account {
  display: flex;
  flex-direction: column;
  .input {
    height: 80px;
  }
  .account-box {
    flex: auto;
    display: flex;
    overflow-y: auto;
    flex-direction: column;
    height: 550px;
    .oper {
      margin: 5px;
      .button {
        float: right;
      }
    }
    .account {
      display: flex;
      text-align: center;
      background: #8882;
      border-radius: 5px;
      padding: 5px;
      font-size: 18px;
      margin: 5px;
      cursor: default;
      &:hover {
        background: #8884;
      }
      .id {
        flex: 1;
      }
      .name {
        flex: 2;
      }
      .gender {
        flex: 1;
      }
      .pwd {
        flex: 2;
      }
    }
    .err {
      background: #f882;
      &:hover {
        background: #f884;
      }
    }
    .list {
      overflow-y: auto;
    }
  }
}
</style>
