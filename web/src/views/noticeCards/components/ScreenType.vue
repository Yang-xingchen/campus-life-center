<template>
  <div class="screen_type">
    <div :class="['type_name', theme]" @click="show = !show">
      <a-icon type="up" v-show="show" />
      <a-icon type="down" v-show="!show" />
      {{ name }}
    </div>
    <div :class="['item_box', theme]" v-show="show">
      <div :class="['item', theme, allAble ? 'button' : 'unable']" @click="all">
        全选
      </div>
      <div
        :class="['item', theme, clearnAble ? 'button' : 'unable']"
        @click="clearn"
      >
        清空
      </div>
      <a-divider type="vertical" />
      <div
        v-for="v in values"
        :key="v.name"
        :class="['item', v.value ? 'show' : 'hide', theme]"
        @click="change(v.name)"
      >
        {{ v.name }}
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
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
    ...mapState(["theme"]),
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
@import "../../../assets/theme.less";
.screen_type {
  display: block;
  .type_name {
    font-size: 28px;
    padding: 5px 15px;
    width: 100%;
    cursor: pointer;
    &.dark {
      background: @d-bg2;
    }
    &.light {
      background: @l-bg2;
    }
  }
  .item_box {
    box-shadow: 0 0 20px #0004 inset;
    padding: 10px 20px;
    &.dark {
      background: @d-bg2;
    }
    &.light {
      background: @l-bg2;
    }
    .item {
      display: inline-block;
      font-size: 18px;
      padding: 3px 5px;
      cursor: default;
      text-decoration: none;
      &.show {
        &.light {
          color: @l-fg;
          &:hover {
            color: @l-bg5;
            text-decoration: line-through;
          }
        }
        &.dark {
          color: @d-fg;
          &:hover {
            color: @d-bg5;
            text-decoration: line-through;
          }
        }
      }
      &.hide {
        text-decoration: line-through;
        &.light {
          color: @l-bg5;
          &:hover {
            color: @l-fg;
            text-decoration: none;
          }
        }
        &.dark {
          color: @d-bg5;
          &:hover {
            color: @d-fg;
            text-decoration: none;
          }
        }
      }
      &.button {
        cursor: pointer;
        &.dark {
          color: @d-fg;
          &:hover {
            color: @d-bg5;
            text-decoration: none !important;
          }
        }
        &.lihgt {
          color: @l-fg;
          &:hover {
            color: @l-bg5;
            text-decoration: none !important;
          }
        }
      }
      &.unable {
        cursor: default;
        &.dark {
          color: @d-bg5;
        }
        &.lihgt {
          color: @l-bg5;
        }
        &:hover {
          text-decoration: none !important;
        }
      }
      background: #0000;
    }
  }
}
</style>
