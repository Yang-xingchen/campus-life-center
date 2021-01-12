<template>
  <div>
    <div class="info" v-for="info in items" :key="info.id">
      <span>{{ info.name }}</span>
      <div class="text" v-if="info.type === 0">
        <a-input
          :placeholder="'示例:' + info.sample"
          v-model="info.value"
        ></a-input>
      </div>
      <div class="array" v-if="info.type === 1">
        <InfoCollectItem :items="info.items" />
      </div>
      <div class="radio" v-if="info.type === 2">
        <a-radio-group v-model="info.value">
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
.array {
  padding: 10px 20px;
  border: 1px solid #888;
  border-radius: 5px;
}
</style>
