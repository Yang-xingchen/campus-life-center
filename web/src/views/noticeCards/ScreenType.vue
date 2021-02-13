<template>
  <div class="screen_type">
    <div class="type_name" @click="show = !show">
      <a-icon type="up" v-show="show" />
      <a-icon type="down" v-show="!show" />
      {{ name }}
    </div>
    <div class="item_box" v-show="show">
      <div :class="['item', allAble ? 'button' : 'unable']" @click="all">
        全选
      </div>
      <div :class="['item', clearnAble ? 'button' : 'unable']" @click="clearn">
        清空
      </div>
      <a-divider type="vertical" />
      <div
        v-for="v in values"
        :key="v.name"
        :class="['item', v.value ? 'show' : 'hide']"
        @click="change(v.name)"
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
  },
  computed: {
    allAble() {
      for (let v of this.values) {
        if (!v.value) {
          return true;
        }
      }
      return false;
    },
    clearnAble() {
      for (let v of this.values) {
        if (v.value) {
          return true;
        }
      }
      return false;
    }
  },
  methods: {
    all() {
      this.values.forEach(value => {
        value.value = true;
      });
      this.$emit("change");
    },
    clearn() {
      this.values.forEach(value => {
        value.value = false;
      });
      this.$emit("change");
    },
    change(v) {
      this.values.forEach(value => {
        if (value.name === v) {
          value.value = !value.value;
        }
      });
      this.$emit("change");
    }
  }
};
</script>

<style lang="less" scope>
.screen_type {
  display: block;
  .type_name {
    font-size: 24px;
    padding: 8px 20px;
    width: 100%;
    cursor: pointer;
    background: #8884;
    border-top: #8888 1px solid;
    border-bottom: #8888 1px solid;
  }
  .item_box {
    box-shadow: 0 0 20px #0004 inset;
    padding: 10px 20px;
    background: #8882;
    .item {
      display: inline-block;
      font-size: 18px;
      padding: 3px 5px;
      cursor: pointer;
      text-decoration: none;
      &.show {
        &:hover {
          color: #888;
          text-decoration: line-through;
        }
      }
      &.hide {
        text-decoration: line-through;
        color: #888;
        &:hover {
          text-decoration: none;
          color: inherit;
        }
      }
      &.button {
        &:hover {
          color: #888;
          text-decoration: none !important;
        }
      }
      &.unable {
        cursor: default;
        color: #888;
        &:hover {
          text-decoration: none !important;
        }
      }
      background: #0000;
    }
  }
}
</style>
