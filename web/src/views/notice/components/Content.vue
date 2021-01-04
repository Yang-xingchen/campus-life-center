<template>
  <div>
    {{ text }}
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "content",
  computed: {
    ...mapState({
      text: state => state.notice.content
    })
  }
};
</script>

<style></style>
