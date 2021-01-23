<template>
  <div>
    <div class="add">
      <div class="switch" v-if="show_switch">
        <a-switch
          v-model="add"
          checkedChildren="添加"
          unCheckedChildren="使用"
        />
        <a-select @change="selectExist" class="select" v-show="!add" showSearch>
          <a-select-option v-for="i in infos" :key="i.id" :value="i.id">{{
            i.name
          }}</a-select-option>
        </a-select>
        <a-button type="primary" @click="confirm">确定</a-button>
      </div>
      <div class="head">
        <a-radio-group class="type" v-model="info.type" :disabled="info.exist">
          <a-radio-button :value="0">文本</a-radio-button>
          <a-radio-button :value="1">组合</a-radio-button>
          <a-radio-button :value="2">单选</a-radio-button>
        </a-radio-group>
        <a-input
          class="name"
          v-model="info.name"
          :disabled="info.exist"
          placeholder="信息名称"
        />
      </div>
      <div class="body">
        <div class="text item" v-show="info.type === 0">
          示例:
          <a-input
            class="sample"
            v-model="info.textInfoSample"
            :disabled="info.exist"
          />
        </div>
        <div class="composite item" v-show="info.type === 1">
          <AddCollect
            v-for="item in info.compositeInfo"
            :default="item"
            :key="item.id"
          />
          <AddCollect v-if="!info.exist && info.type === 1" />
        </div>
        <div class="radio item" v-show="info.type === 2">
          选项:
          <a-select v-model="info.radioInfo" mode="tags" :disabled="info.exist">
          </a-select>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
export default {
  name: "AddCollect",
  data() {
    return {
      infos: [],
      add: true,
      show_switch: true,
      info: {
        exist: false,
        id: 0,
        multiple: false,
        persistent: true,
        defaultVisibility: 0,
        order: 0,
        type: 0,
        name: "",
        textInfoSample: "",
        compositeInfo: [],
        radioInfo: []
      }
    };
  },
  props: {
    default: {
      type: Object,
      required: false
    }
  },
  watch: {
    add() {
      this.create();
    }
  },
  methods: {
    create() {
      this.info = {
        exist: false,
        id: 0,
        multiple: false,
        persistent: true,
        defaultVisibility: 0,
        order: 0,
        type: 0,
        name: "",
        textInfoSample: "",
        compositeInfo: [],
        radioInfo: []
      };
      if (this.default) {
        let { id, type, name, sample, items, radio } = this.default;
        this.info.id = id;
        this.info.type = type;
        this.info.name = name;
        this.info.exist = true;
        this.info.textInfoSample = sample;
        this.info.compositeInfo = items;
        this.info.radioInfo = radio;
        this.add = false;
        this.show_switch = false;
      }
    },
    selectExist(id) {
      const value = this.infos.filter(i => i.id === id)[0];
      const { type, name, multiple } = value;
      this.info.exist = true;
      this.info = { ...this.info, id, type, name, multiple };
      if (type === 0) {
        this.info.textInfoSample = value.sample;
      } else if (type === 1) {
        this.info.compositeInfo = value.items;
      } else if (type === 2) {
        this.info.radioInfo = value.radio;
      }
    },
    confirm() {
      this.$emit("add", this.info);
      this.create();
    },
    getInfos() {
      Axios.get(`/info/info/getExistInfo`).then(res => {
        if (res.data.success) {
          this.infos = res.data.data;
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    }
  },
  mounted() {
    this.create();
    this.getInfos();
  }
};
</script>

<style lang="less" scoped>
.add {
  margin: 5px 0;
  padding: 5px;
  border: #888 1px dashed;
  border-radius: 5px;
  .switch {
    display: flex;
    height: 45px;
    * {
      margin: auto 5px;
    }
    .select {
      flex: auto;
    }
  }
  .head {
    .name {
      margin: 0 5px;
      width: auto;
    }
  }
  .body {
    .item {
      margin: 5px 0;
      .sample {
        width: auto;
      }
    }
  }
}
</style>
