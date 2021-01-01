<template>
  <div class="item_box">
    <div class="item back" @click="$emit('oper', 'back')">返回</div>
    <div class="item top" @click="$emit('oper', 'top')">
      {{ notice.top ? "取消置顶" : "置顶" }}
    </div>
    <div
      :class="['item', 'content', select === 'content' ? 'select' : '']"
      @click="$emit('oper', 'content')"
    >
      内容
    </div>
    <div
      :class="['item', 'edit', select === 'edit' ? 'select' : '']"
      @click="$emit('oper', 'edit')"
    >
      编辑
    </div>
    <div
      :class="['item', 'todo', select === 'todo' ? 'select' : '']"
      @click="$emit('oper', 'todo')"
      v-if="todo"
    >
      待办
    </div>
    <div
      :class="['item', 'comment', select === 'comment' ? 'select' : '']"
      @click="$emit('oper', 'comment')"
    >
      评论
    </div>
    <div
      :class="['item', 'update_log', select === 'update_log' ? 'select' : '']"
      @click="$emit('oper', 'update_log')"
    >
      更新日记
    </div>
    <div
      :class="['item', 'attribute', select === 'attribute' ? 'select' : '']"
      @click="$emit('oper', 'attribute')"
    >
      属性
    </div>
    <div class="item delete" @click="$emit('oper', 'delete')">删除</div>
  </div>
</template>

<script>
export default {
  name: "Operation",
  props: {
    notice: Object,
    select: String
  },
  computed: {
    todo() {
      return (
        this.notice && this.notice.todoList && this.notice.todoList.length > 0
      );
    },
    edit() {
      return this.notice && this.notice.creator === this.notice.aid;
    }
  }
};
</script>

<style lang="less" scoped>
.item_box {
  padding: 10px 0;
  .item {
    padding: 5px;
    text-align: center;
    font-size: 28px;
    border: rgba(0, 0, 0, 0) 3px solid;
    cursor: pointer;
    &:hover {
      border-top: green 3px solid;
      border-bottom: green 3px solid;
    }
  }
  .select {
    border-top: green 3px solid;
    border-bottom: green 3px solid;
  }
}
</style>
