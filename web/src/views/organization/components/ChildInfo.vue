<template>
  <div>
    <div class="child_box">
      <div class="parent" v-if="parent.id">
        <div class="title">从属组织</div>
        <Organization :organization="parent" />
      </div>
      <div class="title">
        下属组织({{ childs.length }})
        <a-icon type="setting" class="link" @click="link('child')" />
      </div>
      <div class="childs">
        <Organization
          v-for="child in childs"
          :key="child.id"
          :organization="child"
          :width="280"
        />
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import Organization from "../../../components/OrganizationCard";
export default {
  name: "Child",
  components: { Organization },
  props: {
    id: Number
  },
  data() {
    return {
      childs: [],
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
      Axios.get(`/organization/${this.id}/child`).then(res => {
        if (res.data.success) {
          this.childs = res.data.data;
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    },
    getParent() {
      Axios.get(`/organization/${this.id}/parent`).then(res => {
        if (res.data.success) {
          this.parent = res.data.data;
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
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
  .childs {
    display: flex;
    flex-wrap: wrap;
    overflow-y: auto;
  }
}
</style>
