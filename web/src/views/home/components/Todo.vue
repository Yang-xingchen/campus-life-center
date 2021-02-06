<template>
  <div class="box">
    <div class="title">待办</div>
    <div class="todoList">
      <div v-for="t in todo" :key="t.id" class="todo">
        <div class="icon">
          <a-tooltip title="已完成" v-show="t.finish"
            ><a-icon type="check-circle"
          /></a-tooltip>
        </div>
        {{ t.value }}
        <a-icon type="link" class="link" @click="todoLink(t.source)" />
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
export default {
  name: "Todo",
  props: {
    todo: Array
  },
  methods: {
    todoLink(ref) {
      Axios.get(`/notice/todoRef?ref=${ref}`).then(res => {
        if (res.data.success) {
          this.$router.push({
            path: `/notice/${res.data.data}/todo`,
            query: { back: "/home" }
          });
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
.box {
  padding: 10px;
  background: #8884;
  border-radius: 5px;
  flex-direction: column;
  .title {
    font-size: 18px;
  }
  .todoList {
    overflow-y: auto;
    padding: 15px 0;
    .todo {
      margin: 0 0 5px;
      margin: 0 15px;
      background: #0000;
      font-size: 18px;
      border-bottom: 1px #0000 solid;
      display: flex;
      cursor: default;
      &:hover {
        border-bottom: 1px #888 solid;
      }
      .link {
        margin-left: 5px;
        cursor: pointer;
        &:hover {
          color: #888;
        }
      }
      .icon {
        width: 20px;
        margin-right: 5px;
      }
    }
  }
}
</style>
