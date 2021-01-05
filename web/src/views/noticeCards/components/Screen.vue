<template>
  <div>
    <a-input
      v-model="text"
      class="input"
      @change="chengeFilter"
      placeholder="搜索"
    >
      <a-icon slot="prefix" type="search" />
      <a-tooltip
        slot="suffix"
        title="可通过标题、内容、发布者、时间、标签、待办事项等进行搜索"
      >
        <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
      </a-tooltip>
    </a-input>
    <ScreenType
      v-for="type in types"
      :key="type.name"
      :name="type.name"
      :values="type.value"
      @change="chengeFilter(type.name, $event)"
    />
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
      types: Array,
      text: ""
    };
  },
  watch: {
    notices() {
      this.initTypes();
    }
  },
  mounted() {
    this.initTypes();
  },
  methods: {
    chengeFilter(v, a) {
      this.types = this.types.filter(t => {
        if (t.name === v) {
          t.value.map(tv => {
            if (tv.name === a) {
              tv.value = !tv.value;
            }
            return tv;
          });
        }
        return true;
      });
      this.$emit("update-screen", n => {
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
        for (const index in this.types) {
          const type = this.types[index];
          switch (type.id) {
            case "read":
              if (!type.value[0].value && n.looked) {
                return false;
              }
              if (!type.value[1].value && !n.looked) {
                return false;
              }
              break;
            case "delete":
              if (!type.value[0].value && n.del) {
                return false;
              }
              if (!type.value[1].value && !n.del) {
                return false;
              }
              break;
            case "importance":
              for (const i in type.value) {
                if (
                  !type.value[i].value &&
                  n.accountImportance == type.value[i].name
                ) {
                  return false;
                }
              }
              break;
            case "creator":
              for (const i in type.value) {
                if (
                  !type.value[i].value &&
                  n.creatorName == type.value[i].name
                ) {
                  return false;
                }
              }
              break;
            case "organization":
              for (const i in type.value) {
                if (
                  !type.value[i].value &&
                  n.organizationName == type.value[i].name
                ) {
                  return false;
                }
              }
              break;
            case "tag":
              for (const i in type.value) {
                if (
                  type.value[i].value &&
                  n.tag.indexOf(type.value[i].name) !== -1
                ) {
                  return true;
                }
              }
              return n.tag.length === 0;
            default:
              break;
          }
        }
        return n !== null;
      });
    },
    initTypes() {
      let importance = [...new Set(this.notices.map(n => n.accountImportance))]
        .sort()
        .map(v => {
          return { name: v, value: true };
        });
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
      let t = [
        {
          id: "read",
          name: "阅读状态",
          value: [
            { name: "已读", value: true },
            { name: "未读", value: true }
          ]
        },
        {
          id: "delete",
          name: "删除状态",
          value: [
            { name: "已删除", value: true },
            { name: "未删除", value: true }
          ]
        }
      ];
      if (importance.length > 1) {
        t.push({
          id: "importance",
          name: "重要程度",
          value: importance
        });
      }
      if (creators.length > 1) {
        t.push({
          id: "creator",
          name: "创建者",
          value: creators
        });
      }
      if (organization.length > 1) {
        t.push({
          id: "organization",
          name: "发布组织",
          value: organization
        });
      }
      if (tag.length > 1) {
        t.push({
          id: "tag",
          name: "标签",
          value: tag
        });
      }
      this.types = t;
    }
  }
};
</script>
<style lang="less" scoped>
.input {
  margin: 15px 15px 0;
  width: 335px;
}
</style>
