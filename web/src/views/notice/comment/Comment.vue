<template>
  <div class="comment">
    <div class="head">
      <div class="account">
        {{ comment.accountName }}
      </div>
      <div class="time">{{ format_date(comment.publishTime) }}</div>
    </div>
    <div class="content">
      {{ comment.content }}
    </div>
    <div class="oper">
      {{ comment.children.length }} 条评论
      <span class="divider">|</span>
      <span class="oper" v-show="!show" @click="show = true">展开</span>
      <span class="oper" v-show="show" @click="show = false">收起</span>
    </div>
    <div class="reply" v-show="show">
      <a-textarea
        v-model="content"
        :autosize="autosize"
        placeholder="回复"
        @pressEnter="reply"
      />
      <a-button
        type="primary"
        class="button"
        v-show="content !== ''"
        @click="reply"
        >回复</a-button
      >
    </div>
    <Comment
      v-show="show"
      v-for="child in comment.children"
      :key="child.id"
      :comment="child"
    />
  </div>
</template>

<script>
import { format_date } from "../../../util";
import { mapState } from "vuex";
import Axios from "axios";
export default {
  name: "Comment",
  props: {
    comment: Object
  },
  data() {
    return {
      content: "",
      show: false,
      autosize: {
        minRows: 1
      }
    };
  },
  computed: {
    ...mapState({
      token: state => state.token
    })
  },
  methods: {
    format_date,
    reply() {
      if (this.content === "") {
        return;
      }
      Axios.post(`/comment/${this.comment.id}/reply?token=${this.token}`, {
        content: this.content
      }).then(res => {
        if (res.data.success) {
          this.comment.children.push(res.data.data);
          this.content = "";
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    }
  }
};
</script>

<style lang="less" scoped>
.comment {
  margin: 10px 0 0;
  border: 1px #888 solid;
  border-radius: 5px;
  padding: 1px;
  &:hover {
    border: 1px #8888 solid;
  }
  .head {
    font-size: 16px;
    display: flex;
    color: #888;
    cursor: default;
    .account {
      margin-right: auto;
    }
  }
  .content {
    padding: 0 10px;
  }
  .oper {
    font-size: 16px;
    padding: 0 10px;
    color: #888;
    .divider {
      margin: 0 5px;
    }
    .oper {
      cursor: pointer;
      &:hover {
        color: #88f;
      }
    }
  }
  .reply {
    display: flex;
    padding: 5px 10px;
    .button {
      margin-left: 5px;
      height: auto;
    }
  }
}
</style>
