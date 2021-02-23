<template>
  <div>
    <div class="reply">
      <a-textarea
        v-model="content"
        class="content"
        placeholder="回复"
        :disabled="!token"
      />
      <a-button
        type="primary"
        @click="reply"
        class="button"
        :disabled="content === ''"
        >回复</a-button
      >
    </div>
    <Comment v-for="comment in comments" :key="comment.id" :comment="comment" />
  </div>
</template>

<script>
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
      this.request({
        method: "post",
        url: `/comment/ref/${this.ref}/reply?token=${this.token}`,
        data: { content: this.content }
      }).then(comment => {
        this.comments.push(comment);
        this.content = "";
      });
    },
    getComments() {
      if (!this.ref) {
        return;
      }
      this.request({
        method: "get",
        url: `/comment/ref/${this.ref}`
      }).then(comments => (this.comments = comments));
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
