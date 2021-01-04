<template>
  <div>
    <a-table
      :columns="columns"
      :dataSource="todoList"
      :rowClassName="rowClass"
      rowKey="id"
      :pagination="false"
      :showHeader="false"
      :bordered="true"
    >
      <span slot="action" class="actions" slot-scope="text, record">
        <a-tooltip
          class="action"
          @click="add(record)"
          v-if="!record.isAdd"
          title="加入列表"
          ><a-icon type="plus"
        /></a-tooltip>
        <a-tooltip class="action" @click="add(record)" v-else title="从列表移除"
          ><a-icon type="minus"
        /></a-tooltip>
        <a-divider type="vertical" />
        <a-tooltip
          class="action"
          @click="top(record)"
          v-if="!record.isTop"
          title="列表置顶(自动添加)"
          ><a-icon type="vertical-align-top"
        /></a-tooltip>
        <a-tooltip
          class="action"
          @click="top(record)"
          v-else
          title="列表取消置顶"
          ><a-icon type="vertical-align-bottom"
        /></a-tooltip>
        <a-divider type="vertical" />
        <a-tooltip
          class="action"
          @click="fin(record)"
          v-if="!record.isFinish"
          title="完成"
          ><a-icon type="check-circle"
        /></a-tooltip>
        <a-tooltip class="action" @click="fin(record)" v-else title="取消完成"
          ><a-icon type="close-circle"
        /></a-tooltip>
      </span>
    </a-table>
  </div>
</template>

<script>
import Axios from "axios";
import { mapState, mapMutations } from "vuex";
export default {
  name: "Todo",
  methods: {
    ...mapMutations(["setNotice"]),
    add(v) {
      let data = {
        aid: v.aid,
        nid: v.nid,
        id: v.id,
        isAdd: !v.isAdd,
        isTop: v.isTop,
        isFinish: v.isFinish
      };
      Axios.post("/notice/todo/update/" + this.token, data).then(r => {
        if (r.data.success && r.data.data) {
          let newNotice = { ...this.notice };
          newNotice.todoList = [
            ...this.notice.todoList.filter(t => t.id !== v.id),
            { ...data, type: v.type, value: v.value }
          ].sort((a, b) => a.id - b.id);
          this.setNotice(newNotice);
        }
      });
    },
    top(v) {
      let data = {
        aid: v.aid,
        nid: v.nid,
        id: v.id,
        isAdd: v.isAdd,
        isTop: !v.isTop,
        isFinish: v.isFinish
      };
      Axios.post("/notice/todo/update/" + this.token, data).then(r => {
        if (r.data.success && r.data.data) {
          let newNotice = { ...this.notice };
          newNotice.todoList = [
            ...this.notice.todoList.filter(t => t.id !== v.id),
            { ...data, type: v.type, value: v.value }
          ].sort((a, b) => a.id - b.id);
          this.setNotice(newNotice);
        }
      });
    },
    fin(v) {
      let data = {
        aid: v.aid,
        nid: v.nid,
        id: v.id,
        isAdd: v.isAdd,
        isTop: v.isTop,
        isFinish: !v.isFinish
      };
      Axios.post("/notice/todo/update/" + this.token, data).then(r => {
        if (r.data.success && r.data.data) {
          let newNotice = { ...this.notice };
          newNotice.todoList = [
            ...this.notice.todoList.filter(t => t.id !== v.id),
            { ...data, type: v.type, value: v.value }
          ].sort((a, b) => a.id - b.id);
          this.setNotice(newNotice);
        }
      });
    },
    rowClass() {
      return "row";
    }
  },
  computed: {
    ...mapState({
      token: state => state.token,
      notice: state => state.notice,
      todoList: state => state.notice.todoList
    })
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
          width: "650px"
        },
        {
          title: "",
          key: "action",
          align: "center",
          scopedSlots: { customRender: "action" }
        }
      ]
    };
  }
};
</script>

<style lang="less">
.row {
  color: white;
  background: rgba(0, 0, 0, 0);
  &:hover {
    background: rgba(255, 255, 255, 0.2);
    > td {
      background: rgba(0, 0, 0, 0) !important;
    }
  }
  .actions {
    font-size: 18px;
  }
  .action:hover {
    color: blue;
  }
}
</style>
