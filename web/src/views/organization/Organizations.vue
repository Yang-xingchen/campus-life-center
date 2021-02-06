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
      <div class="list">
        <div class="organizations">
          <div
            class="organization"
            v-for="o in organizations"
            :key="o.id"
            @click="click(o.id)"
          >
            <div class="type">{{ o.type || "未知" }}</div>
            <div class="name">{{ o.name }}</div>
            <div class="roles">
              <span v-for="r in o.roles" :key="r.id">{{ r.name }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Organizations",
  data() {
    return {
      select: "join",
      publicOrganization: []
    };
  },
  computed: {
    ...mapState({
      accountOrganization: state => state.user.organizations
    }),
    organizations() {
      return this.select === "join"
        ? this.accountOrganization
        : this.publicOrganization;
    }
  },
  methods: {
    changeSelect(select) {
      this.select = select;
    },
    click(id) {
      this.$router.push(`/organization/${id}`);
    }
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
    .list {
      height: 660px;
      .organization {
        width: 340px;
        height: 150px;
        background: #8882;
        margin: 5px;
        border-radius: 5px;
        padding: 5px 0;
        text-align: center;
        cursor: pointer;
        &:hover {
          background: #8884;
        }
        .type {
          font-size: 16px;
        }
        .name {
          font-size: 28px;
          margin: 15px;
        }
        .roles {
          font-size: 16px;
        }
      }
    }
  }
}
</style>
