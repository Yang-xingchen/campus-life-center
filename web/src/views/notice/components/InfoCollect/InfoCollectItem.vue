<template>
  <div>
    <div class="info" v-for="info in items" :key="info.id">
      <span>{{ info.name }}</span>
      <div class="text" v-if="info.type === 0">
        <div class="warp" v-for="(value, index) in info.value" :key="index">
          <a-input
            :placeholder="'示例:' + info.sample"
            v-model="info.value[index]"
          ></a-input>
          <a-icon
            type="minus-circle"
            v-if="info.multiple"
            @click="info.value.splice(index, 1)"
          />
        </div>
        <a-input
          :placeholder="'示例:' + info.sample"
          v-if="info.multiple"
          @pressEnter="e => info.value.push(e.target._value)"
        ></a-input>
      </div>
      <div class="array" v-if="info.type === 1">
        <InfoCollectItem :items="info.items" />
      </div>
      <div class="radio" v-if="info.type === 2">
        <a-checkbox-group
          v-model="info.value"
          v-if="info.multiple"
          :options="info.radio"
        />
        <a-radio-group v-model="info.value[0]" v-else>
          <a-radio-button v-for="v in info.radio" :key="v" :value="v"
            >{{ v }}
          </a-radio-button>
        </a-radio-group>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "InfoCollectItem",
  props: {
    items: Array
  }
};
</script>

<style lang="less" scoped>
.warp {
  margin: 5px 0;
  display: flex;
  * {
    margin: auto 2px;
  }
}
.array {
  padding: 10px 20px;
  border: 1px solid #888;
  border-radius: 5px;
}
</style>
