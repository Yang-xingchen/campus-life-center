<template>
  <div class="todo">
    <div :class="['value', todo.isFinish ? 'finish' : '']">
      {{ todo.value }}
    </div>
    <div :class="['checkBox', todo.isFinish ? 'finishCheck' : '']">
      {{ todo.isFinish ? "已完成" : "未完成" }}
    </div>
    <div :class="['checkBox', todo.isAdd ? 'finishCheck' : '']">
      {{ todo.isAdd ? "已添加" : "未添加" }}
    </div>
    <div :class="['checkBox', todo.isTop ? 'finishCheck' : '']">
      {{ todo.isTop ? "已置顶" : "未置顶" }}
    </div>
  </div>
</template>

<script>
export default {
  name: "todoItem",
  props: {
    todo: Object
  }
};
</script>

<style lang="less" scoped>
.todo {
  padding: 3px 10px;
  cursor: pointer;
  height: 30px;
  line-height: 30px;
  margin: 3px 0;
  div {
    float: right;
  }
  .value {
    float: left;
  }
  &:hover {
    padding: 3px 20px;
  }
  .checkBox {
    border: white 1px solid;
    padding: 1px 3px;
    margin: 0 5px;
  }
  .finishCheck {
    background: green;
  }
}
.finish {
  text-decoration: line-through;
}
</style>
