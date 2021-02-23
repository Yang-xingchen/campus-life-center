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
        <a-icon type="link" class="link" v-show="t.link" @click="todoLink(t)" />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Todo",
  props: {
    todo: Array
  },
  methods: {
    todoLink(t) {
      this.$router.push({
        path: t.link,
        query: { back: "/home" }
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
