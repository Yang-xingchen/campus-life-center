<template>
  <div>
    <EditAttribute :notice="notice" />
  </div>
</template>

<script>
import { mapState } from "vuex";
import EditAttribute from "../../components/edit/EditAttribute";
export default {
  name: "Attribute",
  computed: {
    ...mapState({
      notice: state => state.publish.notice
    })
  },
  components: { EditAttribute }
};
</script>
