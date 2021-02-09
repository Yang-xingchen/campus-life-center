<template>
  <div class="box">
    <div class="content">
      <div class="head">
        <div
          :class="['item', select === 'join' ? 'select' : '']"
          @click="changeSelect('join')"
        >
          我加入的组织
        </div>
        <div
          :class="['item', select === 'public' ? 'select' : '']"
          @click="changeSelect('public')"
        >
          公开的组织
        </div>
      </div>
      <a-divider />
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
            <a-select-option v-for="t in types" :key="t">{{
              t
            }}</a-select-option>
          </a-select>
        </div>
      </div>
      <div class="organizations">
        <Organization
          v-for="o in organizations"
          :key="o.id"
          :organization="o"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import Axios from "axios";
import Organization from "../../components/OrganizationCard";
export default {
  name: "Organizations",
  components: { Organization },
  data() {
    return {
      select: "join",
      publicOrganization: [],
      types: [],
      filterType: [],
      filterName: ""
    };
  },
  computed: {
    ...mapState({
      accountOrganization: state => state.user.organizations
    }),
    organizations() {
      let os =
        this.select === "join"
          ? this.accountOrganization
          : this.publicOrganization;
      if (this.filterName) {
        os = (os || []).filter(o => o.name.indexOf(this.filterName) !== -1);
      } else {
        os = (os || []).filter(o => this.filterType.indexOf(o.type) !== -1);
      }
      return os;
    }
  },
  watch: {
    accountOrganization() {
      this.updateTypes();
    },
    publicOrganization() {
      this.updateTypes();
    }
  },
  methods: {
    updateTypes() {
      let types = [
        ...(this.accountOrganization || []),
        ...(this.publicOrganization || [])
      ].map(o => o.type);
      this.types = [...new Set(types)];
      this.filterType = [...new Set(types)];
    },
    changeSelect(select) {
      this.select = select;
    },
    getPublic() {
      Axios.get(`/organization/public`).then(res => {
        if (res.data.success) {
          this.publicOrganization = res.data.data.map(o => {
            return { ...o, roles: [] };
          });
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
    this.getPublic();
  }
};
</script>

<style lang="less" scoped>
.box {
  display: flex;
  width: 100%;
  height: calc(~"100vh - 65px");
  .content {
    width: 1500px;
    height: 800px;
    margin: auto;
    padding: 15px 30px;
    border-radius: 5px;
    background: #8882;
    .head {
      display: flex;
      .item {
        font-size: 24px;
        margin: 0 10px;
        padding: 5px 15px;
        border-radius: 10px;
        cursor: pointer;
        &:hover {
          color: #888;
        }
      }
      .item:first-child {
        margin-left: auto;
      }
      .item:last-child {
        margin-right: auto;
      }
      .select {
        background: #88f8;
      }
    }
    .filters {
      height: 40px;
      width: 100%;
      display: flex;
      font-size: 20px;
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
      height: 600px;
      overflow-y: auto;
    }
  }
}
</style>
