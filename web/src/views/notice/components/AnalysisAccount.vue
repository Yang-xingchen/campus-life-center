<template>
  <div>
    <div class="box">
      <div class="head">
        <span class="title"><slot name="title"/></span>
        <span class="swith" v-show="open"><slot name="swith"/></span>
      </div>
      <a-progress :percent="percent"></a-progress>
      <span class="open" @click="open = true" v-show="!open"
        >显示用户列表<a-icon type="down"
      /></span>
      <span class="open" @click="open = false" v-show="open"
        >关闭用户列表<a-icon type="up"
      /></span>
      <div class="account_content" v-show="open" v-if="accounts.length">
        {{ show }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Account",
  props: {
    showtype: String,
    divider: String,
    percent: Number,
    accounts: {
      type: Array,
      required: false,
      default: () => []
    }
  },
  data() {
    return {
      open: false
    };
  },
  computed: {
    show() {
      return (this.accounts || [])
        .map(a => a[this.showtype])
        .join(this.divider);
    }
  }
};
</script>

<style lang="less" scoped>
.box {
  background: #8884;
  margin: 10px 0;
  border-radius: 5px;
  padding: 0 5px;
  .head {
    display: flex;
    padding-top: 5px;
    height: 25px;
    .title {
      margin-left: 10px;
      margin-right: auto;
      cursor: default;
    }
    .swith {
      margin-right: 20px;
      margin-left: auto;
    }
  }
  .open {
    cursor: pointer;
    display: block;
    text-align: right;
  }
  .account_content {
    border-top: 1px #888 solid;
    display: flex;
    flex-wrap: wrap;
    align-content: flex-start;
    word-break: keep-all;
    padding-left: 30px;
  }
}
</style>
