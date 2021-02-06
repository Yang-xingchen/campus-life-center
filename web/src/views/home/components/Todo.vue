<template>
  <div class="box">
    <div>待办:</div>
    <div class="todoList">
      <div
        v-for="t in todo"
        :key="t.id"
        :color="t.finish ? 'green' : 'blue'"
        class="todo"
        @click="todoLink(t.source)"
      >
        {{ t.value }} <a-icon type="link" />
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
  flex-direction: column;
  .todoList {
    overflow-y: auto;
    padding: 15px 0;
    .todo {
      padding: 0 0 5px;
      margin: 0 15px;
      background: #0000;
      cursor: pointer;
      &:hover {
        color: #888;
      }
    }
  }
}
</style>
