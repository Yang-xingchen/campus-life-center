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
import axios from "axios";
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
      let t = {};
      let deep = (src, dict) => {
        Object.keys(src).forEach(k => {
          if (src[k] && "object" === typeof src[k]) {
            dict[k] = {};
            deep(src[k], dict[k]);
          } else {
            dict[k] = src[k];
          }
        });
      };
      deep(this.notice, t);
      this.update = t;
    },
    complete() {
      axios
        .post(
          `/notice/${this.notice.id}/update?token=${this.token}`,
          this.update
        )
        .then(res => {
          if (res.data.success) {
            this.$notification["success"]({
              message: res.data.code,
              description: res.data.message
            });
          } else {
            this.$notification["error"]({
              message: res.data.code,
              description: res.data.message
            });
          }
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
