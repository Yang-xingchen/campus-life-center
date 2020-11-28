<template>
  <div>
    <label>{{ text }}</label>
    <input
      :type="type"
      :value="value"
      @input="$emit('update:value', $event.target.value)"
    />
  </div>
</template>

<script>
export default {
  name: "SignInInput",
  props: {
    type: String,
    text: String,
    value: String
  }
};
</script>

<style scoped>
div {
  padding: 10px 0;
}
label {
  display: block;
}
input {
  width: 100%;
  background: none;
  border: none;
  border-bottom: white 2px solid;
}
</style>
