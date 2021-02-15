<template>
  <div>
    <Setting @show="showtype = $event" @divider="divider = $event" />
    完成情况
    <Account
      v-for="(t, id) in todos"
      class="account"
      :key="id"
      :showtype="alias[showtype]"
      :divider="divider"
      :accounts="accounts[id] || []"
      :percent="todoFinPer(id)"
    >
      <template v-slot:title>{{ t[0].value }}</template>
      <template v-slot:swith>
        <span class="show_switch">
          完成<a-switch
            v-model="todo[id][true]"
            checked-children="显示"
            un-checked-children="不显示"
          />
          未完成<a-switch
            v-model="todo[id][false]"
            checked-children="显示"
            un-checked-children="不显示"
          />
        </span>
      </template>
    </Account>
  </div>
</template>

<script>
import Setting from "./components/AnalysisSetting";
import Account from "./components/AnalysisAccount";
import { mapState } from "vuex";
export default {
  name: "TodoAnalysis",
  components: { Setting, Account },
  data() {
    return {
      todos: {},
      alias: {
        id: "aid",
        name: "accountName"
      },
      showtype: "id",
      divider: ",",
      todo_show: {},
      todo: {}
    };
  },
  computed: {
    ...mapState({
      ref: state => state.notice.ref
    }),
    accounts() {
      let todo = {};
      for (let id in this.todos) {
        todo[id] = this.todos[id].filter(t => this.todo[id][t.finish]);
      }
      return todo;
    }
  },
  methods: {
    todoFinPer(tid) {
      return (
        (100 * (this.todos[tid] || []).filter(t => t.finish).length) /
        (this.todos[tid] || [1]).length
      );
    },
    getTodos() {
      console.log(this.ref);
      if (!this.ref) {
        return;
      }
      this.request({
        method: "get",
        url: `/todo/NoticeAllTodo?source=${this.ref}`
      }).then(todos => {
        let todo = {};
        todos.forEach(t => {
          todo[t.id] = todo[t.id] || [];
          todo[t.id].push(t);
        });
        this.todos = todo;
      });
    }
  },
  watch: {
    todos() {
      this.todo = Object.fromEntries(
        Object.keys(this.todos).map(t => [t, { true: false, false: true }])
      );
      this.todo_show = Object.fromEntries(
        Object.keys(this.todos).map(t => [t, false])
      );
    },
    ref() {
      this.getTodos();
    }
  },
  created() {
    this.getTodos();
  }
};
</script>

<style lang="less" scoped></style>
