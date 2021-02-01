<template>
  <div>
    <div class="operation">
      <div
        :class="['item', 'oper', theme, item.id === select ? 'select' : '']"
        v-for="item in items"
        :key="item.id"
        @click="change(item)"
      >
        <a-icon :type="item.icon" v-if="item.icon" />
        {{ item.name }}
      </div>
      <div
        :class="[
          'item',
          'oper',
          theme,
          'collect/' + item === select ? 'select' : ''
        ]"
        v-for="item in collects"
        :key="item"
        @click="changeCollect(item)"
      >
        <a-icon type="form" />
        信息收集: {{ collectList.filter(i => i._id === item)[0].name }}
      </div>
      <div class="item" @click="$emit('addCollect')">
        <a-button class="button"> <a-icon type="plus" />添加信息收集 </a-button>
      </div>
      <div class="item" @click="$emit('submit')">
        <a-button class="button"> <a-icon type="check" />发布 </a-button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "PublishMenu",
  props: {
    items: Array,
    collects: Array
  },
  computed: {
    ...mapState({
      theme: state => state.theme,
      collectList: state => state.publish.infoCollects
    }),
    select() {
      let p = this.$route.path.split("publish")[1];
      if (p === "" || p === "/") {
        return "attribute";
      }
      return p.substring(1);
    }
  },
  methods: {
    change(item) {
      this.$emit("change", item);
    },
    changeCollect(item) {
      this.$emit("change", { id: `collect/${item}` });
    }
  }
};
</script>

<style lang="less" scoped>
@import "../assets/theme.less";
.operation {
  border-radius: 5px;
  padding-top: 5px;
  .item {
    width: 100%;
    padding: 5px;
    cursor: pointer;
    &.dark {
      background: @d-bg2;
      &:hover {
        background: @d-bg4;
      }
    }
    &.light {
      background: @l-bg2;
      &:hover {
        background: @l-bg4;
      }
    }
    .button {
      width: 100%;
    }
  }
  .oper {
    padding: 5px 20px;
    font-size: 18px;
  }
  .select {
    &.dark {
      background: @d-bg4;
    }
    &.light {
      background: @l-bg4;
    }
  }
}
</style>
