<template>
  <div>
    <div class="reply">
      <a-textarea v-model="content" class="content" placeholder="回复" />
      <a-button type="primary" @click="reply" class="button">回复</a-button>
    </div>
    <Comment v-for="comment in comments" :key="comment.id" :comment="comment" />
  </div>
</template>

<script>
import Axios from "axios";
import { mapState } from "vuex";
import Comment from "./Comment";
export default {
  name: "Comments",
  components: { Comment },
  data() {
    return {
      comments: [],
      content: ""
    };
  },
  computed: {
    ...mapState({
      ref: state => state.notice.ref,
      token: state => state.token
    })
  },
  watch: {
    ref() {
      this.getComments();
    }
  },
  methods: {
    reply() {
      if (!this.ref) {
        return;
      }
      Axios.post(`/comment/ref/${this.ref}/reply?token=${this.token}`, {
        content: this.content
      }).then(res => {
        if (res.data.success) {
          this.comments.push(res.data.data);
          this.content = "";
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    },
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

<style lang="less" scoped>
.reply {
  display: flex;
  .button {
    margin-left: 5px;
    height: auto;
  }
}
</style>
