<template>
  <div>
    <a-divider orientation="right">
      <span
        :class="['divider', 'setting_title', theme]"
        v-show="setting"
        @click="setting = !setting"
        >设置<a-icon type="up"
      /></span>
      <span
        :class="['divider', 'setting_title', theme]"
        v-show="!setting"
        @click="setting = !setting"
        >设置<a-icon type="down"
      /></span>
    </a-divider>
    <div class="setting" v-show="setting">
      显示方式:
      <a-radio-group v-model="showtype" @change="$emit('show', showtype)">
        <a-radio-button value="id">id</a-radio-button>
        <a-radio-button value="name">名称</a-radio-button>
      </a-radio-group>
      分隔符:
      <a-radio-group v-model="divider" @change="$emit('divider', divider)">
        <a-radio-button value=",">英文逗号</a-radio-button>
        <a-radio-button value=" ">空格</a-radio-button>
      </a-radio-group>
      <a-divider />
    </div>
  </div>
</template>
<script>
import { mapState } from "vuex";
export default {
  name: "Setting",
  data() {
    return {
      setting: true,
      showtype: "id",
      divider: ","
    };
  },
  computed: {
    ...mapState({
      theme: state => state.theme
    })
  }
};
</script>

<style lang="less" scoped>
@import "../../../assets/theme.less";
.setting {
  font-size: 16px;
}
.setting_title {
  cursor: pointer;
}
.divider {
  background: #0000;
  &.dark {
    color: @d-fg;
  }
  &.light {
    color: @l-fg;
  }
}
</style>
