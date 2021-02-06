<template>
  <div>
    <div class="info">
      <div class="id">登录id: {{ user.signId }}</div>
      <div class="gender">性别: {{ user.gender }}</div>
      <div class="token">
        本次登录令牌
        <a-icon type="eye" v-if="showToken" @click="showToken = false" />
        <a-icon type="eye-invisible" v-else @click="showToken = true" />
        :
        {{ showToken ? user.token : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx" }}
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
      user: state => state.user
    })
  },
  data() {
    return {
      showToken: false
    };
  }
};
</script>

<style lang="less" scoped>
.info {
  background: #8882;
  padding: 10px;
  border-radius: 5px;
  height: 200px;
  overflow-y: auto;
}
</style>
