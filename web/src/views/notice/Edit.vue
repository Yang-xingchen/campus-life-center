<template>
  <div>
    <div class="head">
      <a-button type="default" @click="reset">重置</a-button>
      <a-button type="primary" @click="complete">完成</a-button>
    </div>
    <a-divider></a-divider>
    <EditAttribute :notice="update" />
    <a-divider></a-divider>
    <EditContent :notice="update" />
    <a-divider></a-divider>
    <File />
  </div>
</template>

<script>
import { mapState } from "vuex";
import EditAttribute from "../../components/edit/EditAttribute";
import EditContent from "../../components/edit/EditContent";
import File from "./File";
export default {
  name: "Edit",
  components: { EditAttribute, EditContent, File },
  computed: {
    ...mapState({
      notice: state => state.notice,
      token: state => state.token
    })
  },
  data() {
    return {
      update: {}
    };
  },
  methods: {
    reset() {
      let copy = v => {
        if (v instanceof Array) {
          return v.map(copy);
        } else if (v instanceof Object) {
          let ret = {};
          for (let k in v) {
            ret[k] = copy(v[k]);
          }
          return ret;
        } else {
          return v;
        }
      };
      this.update = copy(this.notice);
    },
    complete() {
      this.request({
        method: "post",
        url: `/notice/${this.notice.id}/update?token=${this.token}`,
        data: this.update
      }).then(success => {
        this.$notification[success ? "success" : "error"]({
          message: success ? "成功" : "失败"
        });
      });
    }
  },
  created() {
    this.reset();
  }
};
</script>

<style lang="less" scoped>
.head {
  display: flex;
  justify-content: flex-end;
  * {
    margin: 0 5px;
  }
}
</style>
