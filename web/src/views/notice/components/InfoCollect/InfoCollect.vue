<template>
  <div>
    <a-button type="primary" class="submit" @click="submit">提交</a-button>
    <a-button class="getSave" @click="submit">获取已填写信息</a-button>
    <InfoCollectItem class="collect_box" :items="collect.items" />
  </div>
</template>

<script>
import { mapState } from "vuex";
import Axios from "axios";
import InfoCollectItem from "./InfoCollectItem";

export default {
  name: "InfoCollect",
  data() {
    return {
      collect: { items: [] }
    };
  },
  components: { InfoCollectItem },
  computed: {
    ...mapState({
      token: state => state.token
    })
  },
  watch: {
    token() {
      this.getCollect();
    },
    $route() {
      this.getCollect();
    }
  },
  methods: {
    submit() {
      console.log();
    },
    getCollect() {
      if (!(this.token && this.$route.params.ref)) {
        console.log(this.token);
        console.log(this.$route.ref);
        return;
      }
      Axios.get(
        `info/info/get?ref=${this.$route.params.ref}&token=${this.token}`
      ).then(res => {
        if (res.data.success) {
          this.collect = res.data.data;
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    }
  },
  mounted() {
    this.getCollect();
  }
};
</script>

<style lang="less" scoped>
.submit,
.getSave {
  margin: 0 10px;
  float: right;
}
.collect_box {
  margin-top: 30px;
}
</style>
