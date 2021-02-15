<template>
  <div>
    <a-input class="title" v-model="collect.name" placeholder="标题" />
    <a-divider></a-divider>
    <Item
      v-for="item in collect.compositeInfo"
      :key="item._id"
      :item="item"
      :infos="infos"
      @del="del(item._id)"
    />
    <Add :infos="infos" @add="addInfo" />
  </div>
</template>

<script>
import { init_info } from "../../../util";
import { mapState } from "vuex";
import Item from "./Item";
import Add from "./Add";
export default {
  name: "Collect",
  components: { Item, Add },
  data() {
    return {
      infos: []
    };
  },
  computed: {
    ...mapState({
      collects: state => state.publish.infoCollects
    }),
    collect() {
      if (!this.collects) {
        return { name: "", compositeInfo: [] };
      }
      return this.collects.filter(i => i._id === +this.$route.params.id)[0];
    },
    order() {
      return (this.collect.infos || []).length;
    }
  },
  methods: {
    addInfo(info) {
      const items = this.collect.compositeInfo;
      items.push({ ...init_info(items.length), ...info });
    },
    del(id) {
      this.collect.compositeInfo = this.collect.compositeInfo.filter(
        i => i._id !== id
      );
    },
    getInfos() {
      this.request({
        method: "get",
        url: `/info/getExistInfo`
      }).then(infos => (this.infos = infos));
    }
  },
  created() {
    this.getInfos();
  }
};
</script>

<style lang="less" scoped></style>
