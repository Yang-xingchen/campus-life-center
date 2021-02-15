<template>
  <div>
    <div class="child_box">
      <div class="parent" v-if="parent.id">
        <div class="title">从属组织</div>
        <Organization :organization="parent" />
      </div>
      <div class="title">
        下属组织({{ children.length }})
        <a-icon type="link" class="link" @click="link('child')" />
      </div>
      <div class="children">
        <Organization
          v-for="child in children"
          :key="child.id"
          :organization="child"
          :width="280"
        />
      </div>
    </div>
  </div>
</template>

<script>
import Organization from "../../../components/OrganizationCard";
export default {
  name: "Child",
  components: { Organization },
  props: {
    id: Number
  },
  data() {
    return {
      children: [],
      parent: {}
    };
  },
  watch: {
    id() {
      this.getParent();
      this.getChild();
    }
  },
  methods: {
    link(item) {
      if (item !== this.select) {
        this.$router.push(`/organization/${this.id}/${item}`);
      }
    },
    getChild() {
      this.request({
        method: "get",
        url: `/organization/${this.id}/child`
      }).then(children => (this.children = children));
    },
    getParent() {
      this.request({
        method: "get",
        url: `/organization/${this.id}/parent`
      }).then(parent => (this.parent = parent));
    }
  },
  created() {
    this.getParent();
    this.getChild();
  }
};
</script>

<style lang="less" scoped>
.child_box {
  padding: 10px;
  display: flex;
  flex-direction: column;
  height: 505px;
  .title {
    font-size: 18px;
    .link {
      margin-left: 5px;
      cursor: pointer;
      &:hover {
        color: #888;
      }
    }
  }
  .children {
    display: flex;
    flex-wrap: wrap;
    overflow-y: auto;
  }
}
</style>
