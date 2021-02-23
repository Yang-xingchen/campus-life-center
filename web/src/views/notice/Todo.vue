<template>
  <div>
    <div class="todo" v-for="record in todoList" :key="record.id">
      <div class="text">{{ record.value }}</div>
      <div class="oper" v-show="token">
        <a-tooltip
          class="action"
          @click="add(record)"
          v-if="!record.addList"
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
          v-if="!record.top"
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
          v-if="!record.finish"
          title="完成"
          ><a-icon type="check-circle"
        /></a-tooltip>
        <a-tooltip class="action" @click="fin(record)" v-else title="取消完成"
          ><a-icon type="close-circle"
        /></a-tooltip>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
const columns = [
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
];
export default {
  name: "Todo",
  methods: {
    ...mapMutations(["setNotice"]),
    getUploadData(v) {
      let { aid, nid, id, addList, top, finish } = { ...v };
      return { aid, nid, id, addList, top, finish };
    },
    uploadUpdate(data, v) {
      this.request({
        method: "post",
        url: `/todo/account/update?token=${this.token}`,
        data
      }).then(success => {
        if (success) {
          let newNotice = { ...this.notice };
          newNotice.todoList = [
            ...this.notice.todoList.filter(t => t.id !== v.id),
            { ...data, type: v.type, value: v.value }
          ].sort((a, b) => a.id - b.id);
          this.setNotice(newNotice);
        }
      });
    },
    add(v) {
      let data = this.getUploadData(v);
      data.addList = !data.addList;
      this.uploadUpdate(data, v);
    },
    top(v) {
      let data = this.getUploadData(v);
      data.top = !data.top;
      this.uploadUpdate(data, v);
    },
    fin(v) {
      let data = this.getUploadData(v);
      data.finish = !data.finish;
      this.uploadUpdate(data, v);
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
    return { columns };
  }
};
</script>

<style lang="less">
.todo {
  background: #8884;
  display: flex;
  margin: 5px 0;
  padding: 5px;
  border-radius: 5px;
  font-size: 22px;
  transition: all 0.3s;
  &:hover {
    font-size: 28px;
    background: #8886;
    padding: 5px 15px;
    .oper {
      width: 150px;
    }
  }
  .text {
    padding-left: 15px;
    flex: auto;
    cursor: default;
  }
  .oper {
    width: 120px;
    transition: all 0.3s;
    .action {
      cursor: pointer;
      &:hover {
        color: #888;
      }
    }
  }
}
</style>
