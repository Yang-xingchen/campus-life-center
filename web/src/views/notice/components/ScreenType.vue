<template>
  <div class="screen_type">
    <label>{{ name }}</label>
    <div v-show="show">
      <div
        v-for="v in value"
        :key="v.name"
        :class="v.value ? 'show' : 'hide'"
        @click="$emit('update:value', $event.target.value)"
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
      show: false
    };
  },
  props: {
    name: String,
    value: Array
  }
};
</script>

<style></style>
