<template>
  <div class="screen_type">
    <label class="type_name" @click="show = !show">{{ name }}</label>
    <div class="item_box" v-show="show">
      <div
        v-for="v in values"
        :key="v.name"
        :class="['item', v.value ? 'show' : 'hide']"
        @click="$emit('change', v.name)"
      >
        {{ v.name }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ScreenType",
  data() {
    return {
      show: true
    };
  },
  props: {
    name: String,
    values: Array
  }
};
</script>

<style lang="less">
.screen_type {
  display: block;
  margin: 15px 5px;
  .type_name {
    font-size: 28px;
    margin: 5px 0;
  }
  .item_box {
    .item {
      display: inline-block;
      font-size: 18px;
      padding: 3px 5px;
      cursor: default;
    }
    .show {
      color: white;
      text-decoration: none;
      &:hover {
        color: rgba(255, 255, 255, 0.5);
        text-decoration: line-through;
      }
    }
    .hide {
      color: rgba(255, 255, 255, 0.5);
      text-decoration: line-through;
      &:hover {
        color: white;
        text-decoration: none;
      }
    }
  }
}
</style>
