<template>
  <div>
    <a-button type="primary" class="submit" @click="submit">提交</a-button>
    <a-button class="getSave" @click="getSave">获取已填写信息</a-button>
    <InfoCollectItem class="collect_box" :items="collect.items" />
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
      collect: { items: [] }
    };
  },
  components: { InfoCollectItem },
  computed: {
    ...mapState({
      token: state => state.token,
      aid: state => state.user.signId
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
            submit.push({
              id: item.id,
              text: item.value
            });
          }
        } else {
          submit.push({
            id: item.id,
            text: ""
          });
          item.items.forEach(getSubmit);
        }
      };
      this.collect.items.forEach(getSubmit);
      Axios.post(
        `info/info/submit?token=${this.token}&ref=${source}`,
        submit
      ).then(res => {
        this.$notification[
          res.data.success && res.data.data ? "success" : "error"
        ]({
          message: res.data.code,
          description: res.data.message
        });
      });
    },
    getSave() {
      let ids = [];
      let getId = item => {
        if (item.type !== 1) {
          if (!item.value) {
            ids.push(item.id);
          }
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
      Axios.post(`info/info/getAccountSave?token=${this.token}`, ids).then(
        res => {
          if (res.data.success) {
            let setValue = item => {
              if (!item.value) {
                if (item.type !== 1) {
                  let vs = res.data.data.filter(d => d.id === item.id);
                  if (vs.length == 1) {
                    item.value = vs[0].text;
                  }
                } else {
                  item.items.forEach(setValue);
                }
              }
            };
            this.collect.items.forEach(setValue);
          } else {
            this.$notification["error"]({
              message: res.data.code,
              description: res.data.message
            });
          }
        }
      );
    },
    getCollect() {
      if (!(this.token && this.$route.params.ref)) {
        return;
      }
      Axios.get(
        `info/info/get?ref=${this.$route.params.ref}&token=${this.token}`
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
