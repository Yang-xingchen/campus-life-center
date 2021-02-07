<template>
  <div>
    <div class="head">
      <div class="head">
        <img :src="head" v-show="head" />
        <a-icon type="team" v-show="!head" />
      </div>
      <div class="info">
        <div class="id">
          id: {{ id }} <span>|</span> {{ organization.type }}
        </div>
        <div class="name">
          {{ organization.name }}
        </div>
        <div class="describe">{{ organization.describe }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
export default {
  name: "head",
  props: {
    id: Number
  },
  data() {
    return {
      head: "",
      organization: {}
    };
  },
  watch: {
    id() {
      this.getBaseInfo();
    }
  },
  methods: {
    getBaseInfo() {
      Axios.get(`/organization/${this.id}`).then(res => {
        if (res.data.success) {
          this.organization = { ...this.organization, ...res.data.data };
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
      Axios.post(`/info/getOrganizationSave?id=${this.id}`, [12, 13]).then(
        res => {
          if (res.data.success) {
            this.organization = {
              ...this.organization,
              describe: res.data.data.filter(i => i.id === 13)[0].text
            };
            this.head = res.data.data.filter(i => i.id === 12)[0].text;
          } else {
            this.$notification["error"]({
              message: res.data.code,
              description: res.data.message
            });
          }
        }
      );
    }
  },
  created() {
    this.getBaseInfo();
  }
};
</script>

<style lang="less" scoped>
.head {
  height: 150px;
  display: flex;
  .head {
    height: 150px;
    width: 150px;
    background: #8882;
    margin-right: 20px;
    border-radius: 5px;
    font-size: 150px;
    text-align: center;
    color: #888;
    line-height: 150px;
  }
  .info {
    flex: auto;
    .id {
      margin-left: 15px;
      color: #888;
      span {
        margin: 0 10px;
      }
    }
    .name {
      font-size: 48px;
    }
    .describe {
      background: #8884;
      height: 57px;
      padding: 5px;
      border-radius: 5px;
    }
  }
}
</style>
