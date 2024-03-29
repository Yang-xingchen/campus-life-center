<template>
  <div class="screen">
    <a-input v-model="text" class="input" placeholder="搜索">
      <a-icon slot="prefix" type="search" />
      <a-tooltip
        slot="suffix"
        title="可通过标题、内容、发布者、时间、标签、待办事项等进行搜索"
      >
        <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
      </a-tooltip>
    </a-input>
    <div class="screenTypes">
      <ScreenType name="读取状态" :values="read" />
      <ScreenType name="删除状态" :values="del" />
      <ScreenType name="类型" :values="type" />
      <ScreenType name="重要程度" :values="importance" />
      <ScreenType
        v-for="(value, name) in types"
        :key="name"
        :name="name"
        :values="value"
      />
    </div>
  </div>
</template>

<script>
import ScreenType from "./ScreenType";
export default {
  name: "Screen",
  components: { ScreenType },
  props: {
    notices: Array
  },
  data() {
    return {
      read: [
        { name: "已读", value: true },
        { name: "未读", value: true }
      ],
      del: [
        { name: "删除", value: false },
        { name: "未删除", value: true }
      ],
      type: [
        { name: "消息", value: true },
        { name: "事件", value: true },
        { name: "活动", value: true }
      ],
      importance: [
        { name: "1星", value: true },
        { name: "2星", value: true },
        { name: "3星", value: true },
        { name: "4星", value: true },
        { name: "5星", value: true }
      ],
      types: {},
      text: ""
    };
  },
  watch: {
    notices() {
      this.initTypes();
      this.changeFilter();
    }
  },
  mounted() {
    this.initTypes();
    this.changeFilter();
  },
  methods: {
    changeFilter() {
      const f = n => {
        if (this.text && this.text !== "") {
          const screexText = function(t, j) {
            if (!j) {
              return false;
            }
            for (let i in Object.values(j)) {
              const v = Object.values(j)[i];
              if (typeof v === "object" && screexText(t, v)) {
                return true;
              }
              if (("" + v).indexOf(t) !== -1) {
                return true;
              }
            }
            return false;
          };
          if (!screexText(this.text, n)) {
            return false;
          }
        }
        if (!this.read[n.looked ? 0 : 1].value) {
          return false;
        }
        if (!this.del[n.del ? 0 : 1].value) {
          return false;
        }
        if (!this.type[n.publicType].value) {
          return false;
        }
        if (!this.importance[n.accountImportance - 1].value) {
          return false;
        }
        for (const i in this.types["创建者"]) {
          const v = this.types["创建者"][i];
          if (v.name === n.creatorName) {
            if (!v.value) {
              return false;
            }
            break;
          }
        }
        for (const i in this.types["发布组织"]) {
          const v = this.types["发布组织"][i];
          if (v.name === n.organizationName) {
            if (!v.value) {
              return false;
            }
            break;
          }
        }
        if (n.tag.length) {
          let flag = false;
          for (const i in this.types["标签"]) {
            const v = this.types["标签"][i];
            if (n.tag.indexOf(v.name) !== -1) {
              if (v.value) {
                flag = true;
                break;
              }
            }
          }
          if (!flag) {
            return false;
          }
        }
        return true;
      };
      this.$emit("update-screen", f);
    },
    initTypes() {
      let creators = [...new Set(this.notices.map(n => n.creatorName))].map(
        v => {
          return { name: v, value: true };
        }
      );
      let organization = [
        ...new Set(this.notices.map(n => n.organizationName))
      ].map(v => {
        return { name: v, value: true };
      });
      const flat = a =>
        [].concat(...a.map(x => (Array.isArray(x) ? flat(x) : x)));
      let tag = [...new Set(flat(this.notices.map(n => n.tag)))].map(v => {
        return { name: v, value: true };
      });
      if (creators.length > 1) {
        this.types["创建者"] = creators;
      }
      if (organization.length > 1) {
        this.types["发布组织"] = organization;
      }
      if (tag.length > 1) {
        this.types["标签"] = tag;
      }
      this.types = { ...this.types };
    }
  }
};
</script>
<style lang="less" scoped>
.screen {
  border-radius: 5px;
  .input {
    margin: 15px;
    width: 335px;
  }
  .screenTypes {
    overflow: auto;
    max-height: 700px;
  }
}
</style>
