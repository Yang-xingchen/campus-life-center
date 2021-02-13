<template>
  <div>
    TODO: 评论
    {{ comments }}
  </div>
</template>

<script>
import Axios from "axios";
import { mapState } from "vuex";
export default {
  name: "comment",
  data() {
    return {
      comments: []
    };
  },
  computed: {
    ...mapState({
      ref: state => state.notice.ref
    })
  },
  watch: {
    ref() {
      this.getComments();
    }
  },
  methods: {
    getComments() {
      if (!this.ref) {
        return;
      }
      Axios.get(`/comment/ref/${this.ref}`).then(res => {
        if (res.data.success) {
          this.comments = res.data.data;
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    }
  },
  created() {
    this.getComments();
  }
};
</script>

<style></style>
