<template>
  <div>
    <div class="todos">
      <div class="todo" v-for="(todo, index) in publish.todo" :key="index">
        <a-input class="todo_input" v-model="publish.todo[index]" />
        <a-icon class="todo_del" type="minus-circle" @click="remove(index)" />
      </div>
      <div class="todo">
        <a-textarea
          class="todo_input"
          placeholder="输入以添加"
          v-model="text"
          auto-size
          @blur="add"
          @pressEnter="add"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Todo",
  computed: {
    ...mapState({
      publish: state => state.publish
    })
  },
  data() {
    return {
      text: ""
    };
  },
  methods: {
    add() {
      this.text.split("\n").forEach(text => {
        if (this.text === "") {
          return;
        }
        if (this.publish.todo.indexOf(text) !== -1) {
          this.$notification["error"]({
            message: "该项已存在",
            description: text
          });
          return;
        }
        this.publish.todo.push(text);
      });
      setTimeout(() => {
        this.text = "";
      }, 50);
    },
    remove(index) {
      this.publish.todo.splice(index, 1);
      this.publish.todo = [...new Set(this.publish.todo.filter(t => t !== ""))];
    }
  }
};
</script>

<style lang="less" scoped>
.todos {
  padding: 0 20px;
  .todo {
    display: flex;
    min-height: 45px;
    .todo_input {
      width: 750px;
      margin: auto;
      margin-left: 0;
    }
    .todo_del {
      color: red;
      margin: auto;
      margin-left: 5px;
    }
  }
}
</style>
