<template>
  <div>
    <div class="box">
      <div class="head">
        <img :src="head" height="200px" width="200px" v-show="head" />
        <a-icon type="user" v-show="!head" />
      </div>
      <div class="info">
        <div class="id">登录id: {{ user.signId }}</div>
        <div class="gender">性别: {{ user.gender }}</div>
        <div class="token">
          本次登录令牌
          <a-icon
            class="link"
            type="eye"
            v-if="showToken"
            @click="showToken = false"
          />
          <a-icon
            class="link"
            type="eye-invisible"
            v-else
            @click="showToken = true"
          />
          :
          {{ showToken ? user.token : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx" }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Info",
  computed: {
    ...mapState({
      user: state => state.user,
      token: state => state.token
    })
  },
  data() {
    return {
      showToken: false,
      head: ""
    };
  },
  methods: {
    getHead() {
      this.request({
        method: "post",
        url: `/info/getAccountSave?token=${this.token}`,
        data: [5]
      }).then(head => {
        let infos = head.filter(info => info.id === 5);
        if (infos.length) {
          this.head = infos[0].content;
        }
      });
    }
  },
  created() {
    this.getHead();
  }
};
</script>

<style lang="less" scoped>
.box {
  height: 150px;
  display: flex;
  .head {
    height: 200px;
    width: 200px;
    margin-top: -50px;
    background: #8882;
    margin-right: 10px;
    border-radius: 5px;
    font-size: 180px;
    text-align: center;
    color: #888;
    line-height: 200px;
  }
  .info {
    flex: auto;
    height: 100%;
    background: #8882;
    padding: 10px;
    border-radius: 5px;
    overflow-y: auto;
    font-size: 18px;
    .token {
      .link {
        &:hover {
          color: #888;
        }
      }
    }
  }
}
</style>
