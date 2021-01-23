<template>
  <div>
    <a-input class="title" v-model="collect.name" placeholder="标题" />
    <CollectItem v-for="info in collect.infos" :info="info" :key="info.order" />
    <AddCollect @add="add" />
  </div>
</template>

<script>
import { mapState } from "vuex";
import CollectItem from "./CollectItem";
import AddCollect from "./AddCollect";
export default {
  name: "Collect",
  components: { CollectItem, AddCollect },
  computed: {
    ...mapState({
      collects: state => state.publish.publishInfoCollectList
    }),
    collect() {
      if (!this.collects) {
        return { name: "", infos: [] };
      }
      return this.collects.filter(i => i._id === +this.$route.params.id)[0];
    },
    order() {
      return (this.collect.infos || []).length;
    }
  },
  methods: {
    add(info) {
      console.log(info);
      info.order = this.order;
      this.collect.infos.push(info);
    }
  }
};
</script>

<style lang="less" scoped>
.add {
  padding: 5px;
}
</style>
