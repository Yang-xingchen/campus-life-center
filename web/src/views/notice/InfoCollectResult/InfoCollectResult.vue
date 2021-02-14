<template>
  <div>
    <a-button type="primary" icon="download" @click="output" disabled
      >导出</a-button
    >
    <div :class="['collect_result_box', theme]" v-show="items.length">
      <a-table
        v-show="items.length"
        :columns="columns"
        :dataSource="items"
        :pagination="false"
        row-key="aid"
        bordered
      >
      </a-table>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "infoCollectResult",
  data() {
    return {
      collect: { items: [] }
    };
  },
  computed: {
    ...mapState({
      theme: state => state.theme,
      infos: state => state.notice.noticeInfos
    }),
    columns() {
      if (this.collect.items.length === 0) {
        return [];
      }
      let tran = item => {
        if (item.type === 1) {
          return {
            title: item.name,
            children: item.items.map(tran)
          };
        } else {
          return {
            title: item.name,
            dataIndex: item.name
          };
        }
      };
      return [
        { title: "账户ID", dataIndex: "aid" },
        { title: "账户名", dataIndex: "name" },
        ...this.collect.items[0].items.map(tran)
      ];
    },
    items() {
      if (this.collect.items.length === 0) {
        return [];
      }
      let data = [];
      let added = {};
      let addItem = item => {
        let index = added[item.aid];
        if (index === undefined) {
          added[item.aid] = data.length;
          index = data.length;
          data.push({
            aid: item.aid,
            name: item.accountName
          });
        }
        if (item.type !== 1) {
          data[index][item.name] = item.value.join(",");
        } else {
          item.items.forEach(i => {
            i.aid = item.aid;
            i.accountName = item.accountName;
            addItem(i);
          });
        }
      };
      this.collect.items.forEach(addItem);
      return data;
    }
  },
  watch: {
    infos() {
      this.getCollect();
    },
    $route() {
      this.getCollect();
    }
  },
  methods: {
    getCollect() {
      const ref = this.$route.params.ref;
      if (!ref) {
        return;
      }
      const root = (this.infos || []).filter(i => i.ref === ref);
      if (!root.length) {
        return;
      }
      this.request({
        method: "get",
        url: `/info/getAccountSubmit?ref=${ref}`
      }).then(collect => (this.collect = collect));
    },
    output() {
      console.log("todo: output");
    }
  },
  mounted() {
    this.getCollect();
  }
};
</script>

<style lang="less">
@import "../../../assets/theme.less";
.collect_result_box {
  width: 100%;
  height: 100%;
  overflow: auto;
  max-height: 750px;
  margin-top: 10px;
  &.dark {
    .ant-table {
      color: @d-fg;
    }
    .ant-table-thead > tr > th {
      background: @d-bg4;
      color: @d-fg;
      font-size: 16px;
      font-weight: unset;
      text-align: center;
    }
    .ant-table-tbody > tr:hover > td {
      background: @d-bg2 !important;
    }
  }
  &.light {
    .ant-table {
      color: @l-fg;
    }
    .ant-table-thead > tr > th {
      background: @l-bg4;
      color: @l-fg;
      font-size: 16px;
      font-weight: unset;
      text-align: center;
    }
    .ant-table-tbody > tr:hover > td {
      background: @l-bg2 !important;
    }
  }
  .ant-table td {
    white-space: nowrap;
  }
}
</style>
