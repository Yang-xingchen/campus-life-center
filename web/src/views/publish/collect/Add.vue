<template>
  <div>
    <div class="add">
      <a-select
        class="select"
        mode="tags"
        v-model="name"
        @change="add"
        placeholder="输入或选择现存信息, 按下回车键以添加"
      >
        <a-select-option
          v-for="i in infos"
          :key="i.name"
          :value="i.name + '#exist_info#' + i.id"
          ><a-icon type="save" />{{ i.name }}
        </a-select-option>
      </a-select>
    </div>
  </div>
</template>

<script>
export default {
  name: "Add",
  props: {
    infos: Array
  },
  data() {
    return {
      name: []
    };
  },
  methods: {
    add(id) {
      if (id[0].indexOf("#exist_info#") != -1) {
        const iid = +id[0].split("#exist_info#")[1];
        const info = this.infos.filter(i => i.id === iid)[0];
        let tran = i => {
          i = {
            ...i,
            exist: true,
            order: undefined,
            sample: undefined,
            items: undefined,
            radio: undefined,
            textInfoSample: i.sample,
            compositeInfo: i.items || [],
            radioInfo: i.radio || []
          };
          i.compositeInfo = i.compositeInfo.map(tran);
          return i;
        };
        this.$emit("add", tran(info));
      } else {
        this.$emit("add", { name: this.name[0] });
      }
      this.name = [];
    }
  }
};
</script>
<style lang="less" scoped>
.add {
  margin: 5px 0;
  display: flex;
  .select {
    flex: auto;
  }
}
</style>
