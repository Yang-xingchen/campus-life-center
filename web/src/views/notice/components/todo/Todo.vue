<template>
  <div>
    待办列表：
    <TodoItem v-for="todo in todoList" :key="todo.id" :todo="todo" />
  </div>
</template>

<script>
import TodoItem from "./TodoItem";
export default {
  name: "Todo",
  components: { TodoItem },
  props: {
    todoList: Array
  }
};
</script>

<style></style>
