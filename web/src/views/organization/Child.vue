<template>
  <div class="body">
    <div class="filters">
      <div class="filter">
        <span class="title">名称</span>
        <a-input class="input" v-model="filterName" allowClear />
      </div>
      <div class="filter">
        <span class="title">类型</span>
        <a-select
          class="input"
          mode="tags"
          v-model="filterType"
          :disabled="filterName !== ''"
        >
          <a-select-option v-for="t in types" :key="t">{{ t }}</a-select-option>
        </a-select>
      </div>
    </div>
    <div class="organizations">
      <Organization v-for="o in organizations" :key="o.id" :organization="o" />
      <div class="add" @click="add" v-if="addable"><a-icon type="plus" /></div>
    </div>
  </div>
</template>

<script>
import Organization from "../../components/OrganizationCard";
import { mapState } from "vuex";
export default {
  name: "Child",
  components: { Organization },
  data() {
    return {
      childs: [],
      types: [],
      filterType: [],
      filterName: ""
    };
  },
  computed: {
    ...mapState({
      ao: state => state.user.organizations
    }),
    id() {
      return +this.$route.params.id;
    },
    organizations() {
      let os = this.childs;
      if (this.filterName) {
        os = (os || []).filter(o => o.name.indexOf(this.filterName) !== -1);
      } else {
        os = (os || []).filter(o => this.filterType.indexOf(o.type) !== -1);
      }
      return os;
    },
    addable() {
      let o = this.ao[this.id];
      if (!o) {
        return false;
      }
      for (let r in o.roles) {
        for (let p in o.roles[r].permissions) {
          if (p === "102") {
            return true;
          }
        }
      }
      return false;
    }
  },
  methods: {
    add() {
      this.$router.push(`/organization/${this.id}/addChild`);
    },
    updateTypes() {
      let types = this.childs.map(o => o.type);
      this.types = [...new Set(types)];
      this.filterType = [...new Set(types)];
    },
    getChild() {
      this.request({
        method: "get",
        url: `/organization/${this.id}/child`
      }).then(childs => (this.childs = childs));
    }
  },
  created() {
    this.getChild();
  }
};
</script>

<style lang="less" scoped>
.body {
  height: 625px;
  border-radius: 5px;
  .filters {
    height: 50px;
    width: 100%;
    display: flex;
    font-size: 20px;
    padding-top: 15px;
    margin-bottom: 20px;
    .filter {
      flex: 1;
      display: flex;
      margin: auto 5px;
      .title {
        width: 55px;
        padding: 0 5px;
      }
      .input {
        margin-left: 5px;
        flex: auto;
      }
    }
  }
  .organizations {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    align-content: flex-start;
    height: 550px;
    padding: 0 10px;
    overflow-y: auto;
    .add {
      width: 340px;
      height: 150px;
      border: 2px #888 dashed;
      margin: 5px;
      border-radius: 5px;
      cursor: pointer;
      line-height: 100px;
      font-size: 100px;
      padding: 25px;
      text-align: center;
      color: #888;
      &:hover {
        border: 2px #888 solid;
        background: #8882;
      }
    }
  }
}
</style>
