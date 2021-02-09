<template>
  <div>
    <div :class="large ? 'large' : 'small'">
      <div class="head">
        <img :src="head" v-show="head" />
        <a-icon type="team" v-show="!head" />
      </div>
      <div class="info">
        <div class="id" v-if="large">
          id: {{ id }} <span>|</span> {{ organization.type }}
        </div>
        <div v-else class="type">{{ organization.type }}</div>
        <div class="name">
          {{ organization.name }}
        </div>
        <div class="describe" v-if="large">{{ organization.describe }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
export default {
  name: "Info",
  props: {
    id: Number,
    large: {
      type: Boolean,
      required: false,
      default: true
    }
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
            let describe = res.data.data.filter(i => i.id === 13);
            let head = res.data.data.filter(i => i.id === 12);
            describe = (describe || []).length ? describe[0].text : "";
            head = (head || []).length ? head[0].text : "";
            this.organization = {
              ...this.organization,
              describe
            };
            this.head = head;
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
.large {
  height: 150px;
  display: flex;
  transition: all 0.5s;
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
    transition: all 0.5s;
  }
  .info {
    flex: auto;
    transition: all 0.5s;
    .id {
      margin-left: 15px;
      color: #888;
      span {
        margin: 0 10px;
      }
    }
    .name {
      font-size: 48px;
      transition: all 0.5s;
    }
    .describe {
      background: #8884;
      height: 57px;
      padding: 5px;
      border-radius: 5px;
    }
  }
}
.small {
  height: 30px;
  display: flex;
  transition: all 0.5s;
  .head {
    height: 30px;
    width: 30px;
    background: #8882;
    margin-right: 5px;
    border-radius: 5px;
    font-size: 30px;
    text-align: center;
    color: #888;
    line-height: 30px;
    transition: all 0.5s;
  }
  .info {
    display: flex;
    transition: all 0.5s;
    .type {
      margin: auto 5px;
      font-size: 25px;
      color: #888;
    }
    .name {
      margin: auto 5px;
      font-size: 25px;
      transition: all 0.5s;
    }
  }
}
</style>
