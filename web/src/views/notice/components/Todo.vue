<template>
  <div>
    <a-table :columns="columns" :dataSource="todoList" size="small">
      <span slot="action" slot-scope="text, record">
        <a @click="add(record.id)">添加</a>
        <a-divider type="vertical" />
        <a>置顶</a>
        <a-divider type="vertical" />
        <a>删除</a>
      </span>
    </a-table>
  </div>
</template>

<script>
export default {
  name: "Todo",
  props: {
    todoList: Array
  },
  methods: {
    add(id) {
      console.log(id);
    }
  },
  data() {
    return {
      columns: [
        {
          title: "id",
          dataIndex: "id",
          key: "id",
          align: "center"
        },
        {
          title: "",
          dataIndex: "value",
          key: "value",
          width: "600px"
        },
        {
          title: "Action",
          key: "action",
          scopedSlots: { customRender: "action" }
        }
      ]
    };
  }
};
</script>

<style lang="less" scoped>
.ant-table {
  color: white;
}
</style>
