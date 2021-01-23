<template>
  <div>
    <div :class="['publish', theme]">
      <Operation
        :class="['operation', theme]"
        @change="changeOpeartion($event)"
        @addCollect="addCollect"
        @submit="submit"
        :items="operations"
        :collects="collects"
      />
      <div :class="['content', theme]"><router-view />{{ publish }}</div>
    </div>
  </div>
</template>

<script>
import Operation from "./components/Operation";
import { mapState, mapMutations } from "vuex";
import Axios from "axios";
let operations = [
  {
    id: "attribute",
    name: "基本属性",
    icon: "carry-out"
  },
  {
    id: "content",
    name: "内容",
    icon: "file-markdown"
  },
  {
    id: "account",
    name: "通知列表",
    icon: "user"
  },
  {
    id: "todo",
    name: "待办",
    icon: "bars"
  }
];
export default {
  name: "Publish",
  components: { Operation },
  data() {
    return {
      operations,
      collect_count: 0
    };
  },
  computed: {
    ...mapState({
      theme: state => state.theme,
      token: state => state.token,
      publish: state => state.publish,
      uid: state => state.user.signId
    }),
    collects() {
      return (this.publish.publishInfoCollectList || []).map(i => i._id);
    }
  },
  methods: {
    ...mapMutations(["updatePublish"]),
    changeOpeartion(item) {
      this.$router.push(`/publish/${item.id}`);
    },
    addCollect() {
      const index = this.collect_count++;
      this.publish.publishInfoCollectList.push({
        _id: index,
        name: "",
        infos: []
      });
      this.changeOpeartion({ id: `collect/${index}` });
    },
    submit() {
      // Axios.post(`notice/notice/publish/publicNotice`, this.publish).then(
      //   res => {
      //     if (res.data.success) {
      //       console.log(res.data.data);
      //     } else {
      //       this.$notification["error"]({
      //         message: res.data.code,
      //         description: res.data.message
      //       });
      //     }
      //   }
      // );
      this.updatePublish(this.initPublish());
      this.$router.push("/notices");
    },
    initPublish() {
      return {
        token: this.token,
        notice: {
          creator: this.uid,
          organization: 0,
          visibility: 0,
          importance: 3,
          publicType: 0,
          title: "",
          contentType: 0,
          content: "",
          startTime: null,
          endTime: null
        },
        tag: [],
        todo: [],
        publishInfoCollectList: [],
        accountList: [this.uid],
        todoList: [],
        infoList: [],
        organizationList: []
      };
    }
  },
  mounted() {
    if (this.publish.pid) {
      this.$notification.success({
        message: "已成功加载存储数据"
      });
      return;
    }
    Axios.get(`notice/notice/publish/publishId?token=${this.token}`).then(
      res => {
        if (res.data.success) {
          this.updatePublish({ ...this.initPublish(), pid: res.data.data });
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      }
    );
  }
};
</script>

<style lang="less" scoped>
@import "../../assets/theme.less";
.publish {
  width: 1200px;
  margin: 0 auto;
  .operation {
    width: 290px;
    margin: 5px;
    margin-top: 25px;
    float: left;
    position: fixed;
    border-radius: 5px;
    &.dark {
      background: @d-bg2;
    }
    &.light {
      background: @l-bg2;
    }
  }
  .content {
    width: 890px;
    margin: 5px;
    margin-top: 25px;
    float: right;
    padding: 20px 15px;
    border-radius: 5px;
    min-height: 400px;
    font-size: 20px;
    &.dark {
      background: @d-bg2;
    }
    &.light {
      background: @l-bg2;
    }
  }
}
</style>
