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

<style lang="less" scoped>
div {
  padding: 10px 0;
}
label {
  display: block;
  font-size: 20px;
}
input {
  margin-top: 5px;
  width: 100%;
  background: none;
  border: none;
  border-bottom: white 2px solid;
  color: #fff;
  &:focus {
    border-bottom: #00c 2px solid;
  }
}
</style>
