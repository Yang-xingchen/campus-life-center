<template>
  <div>
    <a-button type="primary" class="submit" @click="submit">提交</a-button>
    <a-tooltip title="注意: 会覆盖当前填写内容">
      <a-button class="getSave" @click="getSave">获取已保存信息</a-button>
    </a-tooltip>
    <InfoCollectItem
      class="collect_box"
      v-if="collect.items"
      :items="collect.items"
    />
  </div>
</template>

<script>
import { mapState } from "vuex";
import InfoCollectItem from "./InfoCollectItem";

export default {
  name: "InfoCollect",
  data() {
    return {
      collect: {}
    };
  },
  components: { InfoCollectItem },
  computed: {
    ...mapState({
      token: state => state.token,
      aid: state => state.user.signId,
      infos: state => state.notice.noticeInfos
    })
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
    submit() {
      let data = [];
      let source = this.$route.params.ref;
      let getSubmit = item => {
        if (item.type !== 1) {
          if (item.value) {
            for (let i = 0; i < item.value.length; i++) {
              data.push({
                id: item.id,
                content: item.value[i],
                multipleIndex: i
              });
            }
          }
        } else {
          data.push({
            id: item.id,
            content: "",
            multipleIndex: 0
          });
          item.items.forEach(getSubmit);
        }
      };
      this.collect.items.forEach(getSubmit);
      this.request({
        method: "post",
        url: `/info/account/submit?token=${this.token}&ref=${source}`,
        data
      }).then(success => {
        this.$notification[success ? "success" : "error"]({
          message: success ? "成功" : "失败"
        });
      });
    },
    getSave() {
      let data = [];
      let getId = item => {
        if (item.type !== 1) {
          data.push(item.id);
        } else {
          item.items.forEach(getId);
        }
      };
      this.collect.items.forEach(getId);
      if (!data.length) {
        this.$notification["info"]({
          message: "没有需要获取的信息",
          description: "没有需要获取的信息"
        });
        return;
      }
      this.request({
        method: "post",
        url: `/info/account/save?token=${this.token}`,
        data
      }).then(saves => {
        let setValue = item => {
          if (item.type !== 1) {
            if (item.multiple) {
              saves
                .filter(d => d.id === item.id)
                .forEach(v => item.value.push(v.content));
              item.value = [...new Set(item.value.filter(v => v !== ""))];
            } else {
              let datas = saves.filter(d => d.id === item.id);
              if (datas.length) {
                item.value = [datas[0].content];
              }
            }
          } else {
            item.items.forEach(setValue);
          }
        };
        this.collect.items.forEach(setValue);
      });
    },
    getCollect() {
      const ref = this.$route.params.ref;
      if (!(this.token && ref)) {
        return;
      }
      const root = (this.infos || []).filter(i => i.ref === ref);
      if (!root.length) {
        return;
      }
      this.request({
        method: "get",
        url: `/info/get?ref=${ref}&token=${this.token}`
      }).then(collect => (this.collect = collect));
    }
  },
  created() {
    this.getCollect();
  }
};
</script>

<style lang="less" scoped>
.submit,
.getSave {
  margin: 0 10px;
  float: right;
}
.collect_box {
  margin-top: 30px;
}
</style>
