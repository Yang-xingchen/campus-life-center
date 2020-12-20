<template>
  <div class="screen">
    <ScreenType
      v-for="type in types"
      :key="type.name"
      :name="type.name"
      v-model:value="type.value"
    />
  </div>
</template>

<script>
import ScreenType from "./ScreenType";
export default {
  name: "Screen",
  components: [ScreenType],
  props: {
    notices: Array
  },
  // computed: {
  //   types() {
  //     const handler = function(mape) {
  //       return [...new Set(Array.from(this.notices, mape)];
  //     }
  //     let importance = handler(({n}) => n.importance).map(v => {
  //       return { name: v, value: true };
  //     });
  //     let creators = handler(({n}) => n.creators).map(v => {
  //       return { name: v, value: true };
  //     });
  //     let organization = handler(({n}) => n.organization).map(v => {
  //       return { name: v, value: true };
  //     });
  //     let t = [
  //       {
  //         name: "已读",
  //         value: [
  //           { name: "是", value: true },
  //           { name: "否", value: true }
  //         ]
  //       },
  //       {
  //         name: "已删除",
  //         value: [
  //           { name: "是", value: true },
  //           { name: "否", value: true }
  //         ]
  //       }
  //     ];
  //     if (importance.length > 1) {
  //       t.push({
  //         name: "重要程度",
  //         value: importance
  //       });
  //     }
  //     if (creators.length > 1) {
  //       t.push({
  //         name: "创建者",
  //         value: creators
  //       });
  //     }
  //     if (organization.length > 1) {
  //       t.push({
  //         name: "发布来源",
  //         value: organization
  //       });
  //     }
  //     return t;
  //   }
  // },
  methods: {
    chengeFilter() {
      this.$emit("update-screen", n => {
        return n !== null;
      });
    }
  }
};
</script>

<style></style>
