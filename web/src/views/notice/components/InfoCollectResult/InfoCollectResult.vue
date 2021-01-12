<template>
  <div>
    <a-button type="primary" icon="download" @click="output" disabled
      >导出</a-button
    >
    <div class="collect_result_box" v-show="items.length">
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
import Axios from "axios";
export default {
  name: "infoCollectResult",
  data() {
    return {
      collect: { items: [] }
    };
  },
  computed: {
    ...mapState({
      token: state => state.token
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
        ...this.collect.items.map(tran)
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
          data[index][item.name] = item.value;
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
    token() {
      this.getCollect();
    },
    $route() {
      this.getCollect();
    }
  },
  methods: {
    getCollect() {
      if (!(this.token && this.$route.params.ref)) {
        return;
      }
      Axios.get(
        `info/info/getAccountSubmit?ref=${this.$route.params.ref}&token=${this.token}`
      ).then(res => {
        if (res.data.success) {
          this.collect = res.data.data;
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
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

<style>
.collect_result_box {
  width: 100%;
  height: 100%;
  overflow: auto;
  max-height: 750px;
  margin-top: 10px;
}
.ant-table {
  color: white;
}
.ant-table-thead > tr > th {
  background: rgba(255, 255, 255, 0.2);
  color: white;
  font-size: 16px;
  font-weight: unset;
  text-align: center;
}
.ant-table-tbody > tr:hover > td {
  background: rgba(255, 255, 255, 0.1) !important;
}
.ant-table td {
  white-space: nowrap;
}
</style>
