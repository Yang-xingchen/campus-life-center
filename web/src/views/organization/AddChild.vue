<template>
  <div class="body">
    <div class="back">
      <div class="back_button" @click="back"><a-icon type="left" />返回</div>
    </div>
    <div class="content" v-show="addable">
      <div class="item">
        <div class="title">类型</div>
        <div class="value">
          <a-select
            class="select"
            v-model="selectType"
            mode="tags"
            @blur="changeSelectType"
            @change="changeSelectType"
            :maxTagCount="1"
          >
            <a-select-option v-for="t in types" :key="t">{{
              t
            }}</a-select-option>
          </a-select>
        </div>
      </div>
      <div class="item">
        <div class="title">名称</div>
        <div class="value">
          <a-input v-model="organization.name" />
        </div>
      </div>
      <div class="item">
        <div class="title">是否隐藏</div>
        <div class="value">
          <a-radio-group v-model="organization.hide">
            <a-radio-button :value="true">是</a-radio-button>
            <a-radio-button :value="false">否</a-radio-button>
          </a-radio-group>
        </div>
      </div>
      <div class="item">
        <div class="title" />
        <div class="value">
          <a-button
            type="primary"
            @click="submit"
            :disabled="
              this.organization.type === '' || this.organization.name === ''
            "
            >提交</a-button
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { init_organization } from "../../util";
import { mapState, mapActions } from "vuex";
export default {
  name: "addChild",
  data() {
    return {
      types: [],
      organization: init_organization(),
      selectType: []
    };
  },
  computed: {
    ...mapState({
      token: state => state.token,
      ao: state => state.user.organizations
    }),
    id() {
      return +this.$route.params.id;
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
    ...mapActions(["getAccountByToken"]),
    changeSelectType() {
      if (!this.selectType.length) {
        this.selectType = [this.organization.type];
        return;
      }
      this.organization.type = this.selectType[this.selectType.length - 1];
      this.selectType = [this.organization.type];
    },
    back() {
      this.$router.push(`/organization/${this.id}/child`);
    },
    submit() {
      this.request({
        method: "post",
        url: `/organization/${this.id}/addChild?token=${this.token}`,
        data: this.organization
      }).then(id => {
        this.$notification["success"]({
          message: "成功"
        });
        this.getAccountByToken();
        this.$router.push(`/organization/${id}`);
      });
    },
    getTypes() {
      this.request({
        method: "get",
        url: `/organization/types`
      }).then(types => (this.types = types));
    }
  },
  created() {
    this.getTypes();
  }
};
</script>

<style lang="less" scoped>
.body {
  display: flex;
  height: 625px;
  .back {
    flex: 1;
    .back_button {
      background: #8882;
      border-radius: 5px;
      font-size: 22px;
      padding: 10px 25px;
      margin-right: 10px;
      cursor: pointer;
      &:hover {
        background: #8884;
      }
    }
  }
  .content {
    flex: 4;
    background: #8882;
    border-radius: 5px;
    padding: 100px 120px;
    .item {
      display: flex;
      font-size: 24px;
      height: 50px;
      .title {
        flex: 1;
        margin: auto 0;
        text-align: right;
        margin-right: 15px;
      }
      .value {
        flex: 3;
        margin: auto 0;
        .select {
          width: 100%;
        }
      }
    }
  }
}
</style>
