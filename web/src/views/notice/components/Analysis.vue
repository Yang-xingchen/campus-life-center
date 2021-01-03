<template>
  <div>
    TODO: 统计
    {{ analysis }}
  </div>
</template>

<script>
import { mapState } from "vuex";
import Axios from "axios";
export default {
  name: "analysis",
  props: {
    notice: Object
  },
  computed: {
    ...mapState({
      token: state => state.token
    })
  },
  data() {
    return {
      analysis: []
    };
  },
  watch: {
    notice() {
      console.log(this.notice);
      Axios.get(
        "notice/" + this.notice.id + "/analysis?token=" + this.token
      ).then(res => {
        if (res.data.success) {
          this.analysis = res.data.data;
        }
      });
    }
  }
};
</script>

<style></style>
