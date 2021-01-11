<template>
  <div>TODO: 信息收集结果 {{ collect }}</div>
</template>

<script>
import { mapState } from "vuex";
import Axios from "axios";

export default {
  name: "infoCollectResult",
  data() {
    return {
      collect: { items: [] }
    };
  },
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
        `info/info/getByRef?ref=${this.$route.params.ref}&token=${this.token}`
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

<style></style>
