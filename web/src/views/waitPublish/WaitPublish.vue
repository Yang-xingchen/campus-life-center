<template>
  <div class="box">
    <div class="content">
      <div class="head">
        <div class="back" @click="back"><a-icon type="left" />返回</div>
        等待发布列表
      </div>
      <div class="body">
        <div class="screen">
          <div
            :class="['item', select === -1 ? 'select' : '']"
            @click="select = -1"
          >
            全部
          </div>
          <div
            v-for="(item, id) in organization"
            :key="id"
            :class="['item', select === id ? 'select' : '']"
            @click="select = id"
          >
            {{ item }}
          </div>
        </div>
        <div class="list">
          <NoticeItem
            class="notice-item"
            v-for="notice in showNotices"
            :key="notice.id"
            :notice="notice"
            @update="getNotices"
          />
          <div class="notice-item" v-show="!notices.length">
            暂无等待发布通知
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import NoticeItem from "./NoticeItem";
export default {
  name: "WaitPublish",
  components: { NoticeItem },
  data() {
    return {
      notices: [],
      select: -1
    };
  },
  computed: {
    ...mapState({
      token: state => state.token
    }),
    organization() {
      let os = {};
      this.notices.forEach(n => {
        os[n.organization] = n.organizationName;
      });
      return os;
    },
    showNotices() {
      if (this.select === -1) {
        return this.notices;
      }
      return this.notices.filter(n => n.organization === +this.select);
    }
  },
  watch: {
    token() {
      this.getNotices();
    }
  },
  methods: {
    back() {
      this.$router.go(-1);
    },
    getNotices() {
      if (!this.token) {
        return;
      }
      this.request({
        method: "get",
        url: `/notice/publish/waitList?token=${this.token}`
      }).then(notices => (this.notices = notices));
    }
  },
  created() {
    this.getNotices();
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
    border-radius: 5px;
    background: #8882;
    display: flex;
    flex-direction: column;
    .head {
      background: #8884;
      font-size: 22px;
      padding: 10px;
      text-align: center;
      cursor: default;
      .back {
        float: left;
        cursor: pointer;
        &:hover {
          color: #888;
        }
      }
    }
    .body {
      flex: auto;
      padding: 15px 30px;
      display: flex;
      height: 750px;
      .screen {
        flex: 1;
        background: #8882;
        margin-right: 10px;
        border-radius: 5px;
        overflow-y: auto;
        .item {
          padding: 5px 20px;
          font-size: 22px;
          cursor: pointer;
          &:hover {
            background: #8884;
          }
        }
        .select {
          background: #8884;
        }
      }
      .list {
        flex: 3;
        overflow-y: auto;
        .notice-item {
          background: #8882;
          border-radius: 5px;
          margin-bottom: 5px;
        }
      }
    }
  }
}
</style>
