<template>
  <div>
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
      types: Array
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
        for (const index in this.types) {
          const type = this.types[index];
          switch (type.id) {
            case "read":
              if (!type.value[0].value && n.accountOperation.isRead) {
                return false;
              }
              if (!type.value[1].value && !n.accountOperation.isRead) {
                return false;
              }
              break;
            case "delete":
              if (!type.value[0].value && n.accountOperation.isDelete) {
                return false;
              }
              if (!type.value[1].value && !n.accountOperation.isDelete) {
                return false;
              }
              break;
            case "importance":
              for (const i in type.value) {
                if (
                  !type.value[i].value &&
                  n.notice.importance == type.value[i].name
                ) {
                  return false;
                }
              }
              break;
            case "creator":
              for (const i in type.value) {
                if (
                  !type.value[i].value &&
                  n.notice.creator == type.value[i].name
                ) {
                  return false;
                }
              }
              break;
            case "organization":
              for (const i in type.value) {
                if (
                  !type.value[i].value &&
                  n.notice.organization == type.value[i].name
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
      let importance = [
        ...new Set(this.notices.map(n => n.notice.importance))
      ].map(v => {
        return { name: v, value: true };
      });
      let creators = [...new Set(this.notices.map(n => n.notice.creator))].map(
        v => {
          return { name: v, value: true };
        }
      );
      let organization = [
        ...new Set(this.notices.map(n => n.notice.organization))
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
          name: "已读",
          value: [
            { name: "是", value: true },
            { name: "否", value: true }
          ]
        },
        {
          id: "delete",
          name: "已删除",
          value: [
            { name: "是", value: true },
            { name: "否", value: true }
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
          name: "发布来源",
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

<style></style>
