<template>
  <div class="notice">
    <Screen id="screen" />
    <NoticeMain id="main" :notices="show_notice" />
  </div>
</template>

<script>
import Screen from "./components/Screen";
import NoticeMain from "./components/NoticeMain";
import Axios from "axios";
import { mapState } from "vuex";

export default {
  name: "Notice",
  components: { Screen, NoticeMain },
  data() {
    return {
      notices: [],
      filterFuntion: n => n
    };
  },
  computed: {
    ...mapState("token"),
    show_notice() {
      return this.notices.filter(this.filterFuntion);
    }
  },
  mounted() {
    Axios.post("notice/notice/get", this.token).then(res => {
      console.log(res);
      this.notices = res.data;
    });
  },
  methods: {
    updateScreen(f) {
      this.filterFuntion = f;
    }
  }
};
</script>

<style></style>
