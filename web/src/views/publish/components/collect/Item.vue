<template>
  <div>
    <div class="item_box">
      <div class="head">
        <a-radio-group
          class="type"
          v-model="item.type"
          :disabled="item.exist"
          @change="typeChange"
        >
          <a-radio-button :value="0">文本</a-radio-button>
          <a-radio-button :value="1">组合</a-radio-button>
          <a-radio-button :value="2">单选</a-radio-button>
        </a-radio-group>
        <a-input
          class="name"
          v-model="item.name"
          :disabled="item.exist"
          placeholder="信息名称"
        />
        <a-button type="primary" @click="$emit('del')" :disabled="!deleteable"
          >删除</a-button
        >
      </div>
      <div class="body">
        <div class="text item" v-show="item.type === 0">
          <div class="attr">
            <span class="title">示例:</span>
            <a-input
              class="sample content"
              v-model="item.textInfoSample"
              :disabled="item.exist"
            />
          </div>
          <div class="attr">
            <a-switch
              v-model="item.multiple"
              checkedChildren="多值"
              unCheckedChildren="单值"
              :disabled="item.exist"
            />
            <a-select v-model="item.textType" :disabled="item.exist">
              <a-select-option :key="0">文本</a-select-option>
              <a-select-option :key="1">数字</a-select-option>
              <a-select-option :key="2">正则</a-select-option>
            </a-select>
            <a-input
              class="content"
              v-model="item.regular"
              v-show="item.textType === 2"
              placeholder="正则表达式内容"
              :disabled="item.exist"
            />
          </div>
        </div>
        <div class="composite item" v-show="item.type === 1">
          <Item
            v-for="c in item.compositeInfo"
            :key="c._id"
            :item="c"
            :infos="infos"
            :deleteable="!item.exist"
            @del="del(c._id)"
          />
          <Add :infos="infos" @add="addInfo" />
        </div>
        <div class="radio item" v-show="item.type === 2">
          <div class="attr">
            <a-switch
              v-model="item.multiple"
              checkedChildren="多选"
              unCheckedChildren="单选"
              :disabled="item.exist"
            />
            <span class="title">选项:</span>
            <a-select
              class="radio_select content"
              v-model="item.radioInfo"
              mode="tags"
              :disabled="item.exist"
              placeholder="输入以添加，回车键确认"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { init_info } from "../../../../util";
import Add from "./Add";
export default {
  name: "Item",
  props: {
    item: Object,
    infos: Array,
    deleteable: {
      type: Boolean,
      default: true
    }
  },
  components: { Add },
  methods: {
    addInfo(info) {
      const items = this.item.compositeInfo;
      items.push({ ...init_info(items.length), ...info });
    },
    del(id) {
      this.item.compositeInfo = this.item.compositeInfo.filter(
        i => i._id !== id
      );
    },
    typeChange() {
      // 文本
      this.item.sample = "";
      this.item.textType = 0;
      this.item.regular = "";
      // 组合
      this.item.compositeInfo = [];
      // 单选
      this.item.radioInfo = [];
    }
  }
};
</script>

<style lang="less" scoped>
.item_box {
  border: 2px #888 dashed;
  border-radius: 5px;
  padding: 10px;
  margin: 10px 0;
  &:hover {
    border: 2px #888 solid;
  }
  .head {
    display: flex;
    .name {
      flex: auto;
      margin: 0 5px;
      width: auto;
    }
  }
  .body {
    .item {
      margin: 5px 0;
      .attr {
        display: flex;
        margin: 8px 0;
        * {
          margin: auto 3px;
        }
        .content {
          flex: auto;
          width: auto;
        }
      }
      .title {
        margin: auto 5px;
      }
    }
  }
}
</style>
