<template>
  <div>
    <div :class="large ? 'large' : 'small'">
      <div class="head">
        <img
          :src="head"
          v-show="head"
          :width="large ? 150 : 30"
          :height="large ? 150 : 30"
        />
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
      this.request({
        method: "get",
        url: `/organization/${this.id}`
      }).then(organization => {
        this.organization = { ...this.organization, ...organization };
      });
      this.request({
        method: "post",
        url: `/info/getOrganizationSave?id=${this.id}`,
        data: [5, 6]
      }).then(data => {
        let describe = data.filter(i => i.id === 6);
        let head = data.filter(i => i.id === 5);
        describe = (describe || []).length ? describe[0].content : "";
        head = (head || []).length ? head[0].content : "";
        this.organization = {
          ...this.organization,
          describe
        };
        this.head = head;
      });
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
