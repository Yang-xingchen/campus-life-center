<template>
  <div>
    <div :class="['publish', theme]">
      <Menu
        :class="['operation', theme]"
        @change="changeOpeartion($event)"
        @addCollect="addCollect"
        @submit="submit"
        :items="operations"
        :collects="collects"
      />
      <div :class="['content', theme]"><router-view /></div>
    </div>
  </div>
</template>

<script>
import Menu from "../components/PublishMenu";
import { mapState, mapMutations } from "vuex";
import { init_publish, init_collect } from "../util";
let operations = [
  {
    id: "attribute",
    name: "基本属性",
    icon: "carry-out",
    test: () => true
  },
  {
    id: "content",
    name: "内容",
    icon: "file-markdown",
    test: () => true
  },
  {
    id: "account",
    name: "通知列表",
    icon: "user",
    test: publish => publish.notice.organization
  },
  {
    id: "todo",
    name: "待办",
    icon: "bars",
    test: () => true
  }
];
export default {
  name: "Publish",
  components: { Menu },
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
      uid: state => state.user.id
    }),
    collects() {
      return (this.publish.infoCollects || []).map(i => i._id);
    }
  },
  methods: {
    ...mapMutations(["updatePublish"]),
    changeOpeartion(item) {
      this.$router.push(`/publish/${item.id}`);
    },
    addCollect() {
      const index = this.collect_count++;
      this.publish.infoCollects.push(init_collect(index));
      this.changeOpeartion({ id: `collect/${index}` });
    },
    check() {
      if (this.publish.notice.title === "") {
        this.$notification.error({
          message: "标题为空"
        });
        return false;
      }
      if (
        !this.publish.publishConditions.length &&
        this.publish.notice.organization
      ) {
        this.$notification.error({
          message: "发布列表为空"
        });
        return false;
      }
      return true;
    },
    submit() {
      if (!this.check()) {
        return;
      }
      let data = this.publish;
      data.publishConditions = data.publishConditions.map(d => {
        return {
          type: d.cType,
          ref: d.ref
        };
      });
      data.tag = data.notice.tag;
      delete data.notice.tag;
      this.request({
        method: "post",
        url: `/notice/publish/publicNotice`,
        data
      }).then(() => {
        this.$notification["success"]({
          message: "发布成功"
        });
        this.updatePublish(this.initPublish());
        this.$router.push("/notices");
      });
    },
    initPublish() {
      const publish = {
        ...init_publish(),
        token: this.token
      };
      publish.notice.creator = this.uid;
      return publish;
    }
  },
  created() {
    if (this.publish.pid) {
      this.$notification.success({
        message: "已成功加载存储数据"
      });
      return;
    }
    this.request({
      method: "get",
      url: `/notice/publish/publishId?token=${this.token}`
    }).then(pid => this.updatePublish({ ...this.initPublish(), pid }));
  }
};
</script>

<style lang="less" scoped>
@import "../assets/theme.less";
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
