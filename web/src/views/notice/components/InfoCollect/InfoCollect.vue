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
import Axios from "axios";
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
      let submit = [];
      let source = this.$route.params.ref;
      let getSubmit = item => {
        if (item.type !== 1) {
          if (item.value) {
            for (let i = 0; i < item.value.length; i++) {
              submit.push({
                id: item.id,
                text: item.value[i],
                multipleIndex: i
              });
            }
          }
        } else {
          submit.push({
            id: item.id,
            text: "",
            multipleIndex: 0
          });
          item.items.forEach(getSubmit);
        }
      };
      this.collect.items.forEach(getSubmit);
      Axios.post(`/info/submit?token=${this.token}&ref=${source}`, submit).then(
        res => {
          this.$notification[
            res.data.success && res.data.data ? "success" : "error"
          ]({
            message: res.data.code,
            description: res.data.message
          });
        }
      );
    },
    getSave() {
      let ids = [];
      let getId = item => {
        if (item.type !== 1) {
          ids.push(item.id);
        } else {
          item.items.forEach(getId);
        }
      };
      this.collect.items.forEach(getId);
      if (!ids.length) {
        this.$notification["info"]({
          message: "没有需要获取的信息",
          description: "没有需要获取的信息"
        });
        return;
      }
      Axios.post(`/info/getAccountSave?token=${this.token}`, ids).then(res => {
        if (res.data.success) {
          let setValue = item => {
            if (item.type !== 1) {
              if (item.multiple) {
                res.data.data
                  .filter(d => d.id === item.id)
                  .forEach(v => item.value.push(v.text));
                item.value = [...new Set(item.value.filter(v => v !== ""))];
              } else {
                item.value = [
                  res.data.data.filter(d => d.id === item.id)[0].text
                ];
              }
            } else {
              item.items.forEach(setValue);
            }
          };
          this.collect.items.forEach(setValue);
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
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
      Axios.get(`/info/get?ref=${ref}&token=${this.token}`).then(res => {
        if (res.data.success) {
          this.collect = res.data.data;
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    }
  },
  mounted() {
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
