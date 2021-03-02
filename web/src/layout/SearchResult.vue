<template>
  <div>
    <div class="notice">
      <a-input
        class="search"
        v-model="keyword"
        @pressEnter="search"
        placeholder="搜索"
      />
      <div
        class="loading"
        v-show="total > notices.length"
        :style="
          'background-image: linear-gradient(to right , #FC84 ' +
            (notices.length / total) * 100 +
            '%, #0000 ' +
            (notices.length / total) * 100 +
            '%);'
        "
      >
        <a-icon type="loading" />加载中 ({{ notices.length }} /
        {{ total === maxNum ? "???" : total }})
      </div>
      <transition-group name="list" tag="div" class="notice_info_list">
        <NoticeCard
          v-for="notice in notices"
          :key="notice.id"
          :notice="notice"
          class="notice_info"
        />
      </transition-group>
    </div>
  </div>
</template>

<script>
import NoticeCard from "../views/noticeCards/NoticeCard";
import { mapState } from "vuex";

export default {
  name: "NoticeCards",
  components: { NoticeCard },
  data() {
    return {
      total: ~(1 << 31),
      page: -1,
      notices: [],
      keyword: "",
      filterFuntion: n => n !== null
    };
  },
  computed: {
    ...mapState(["token"]),
    show_notice() {
      return this.notices.filter(this.filterFuntion);
    },
    complete() {
      return this.notices.length >= this.total;
    }
  },
  created() {
    this.keyword = this.$route.query.keyword;
    this.load();
  },
  methods: {
    updateScreen(f) {
      this.filterFuntion = f;
    },
    search() {
      this.total = ~(1 << 31);
      this.page = -1;
      this.notices = [];
      this.load();
    },
    load() {
      if (this.total == -1 || this.total > this.notices.length) {
        this.page++;
        this.request({
          method: "get",
          url: `/search/notice?keyword=${this.keyword}&page=${this.page}`
        }).then(notices => {
          this.total = notices.total;
          notices.items
            .map(n => {
              n.accountImportance = n.importance;
              n.showTag = [
                ...n.tag,
                n.top ? "置顶" : "",
                !n.looked ? "未读" : "",
                n.del ? "已删除" : "",
                `重要度: ${n.importance}星`,
                "发布者: " + n.creatorName,
                "发布组织: " + n.organizationName,
                "类型: " + n.publicType
              ].filter(t => t !== "");
              return n;
            })
            .forEach(n => this.notices.push(n));
          setTimeout(this.load, (500 * notices.items.length) / this.total);
        });
      }
    }
  }
};
</script>

<style lang="less" scope>
.notice {
  width: 1500px;
  margin: 0 auto;
  .search {
    margin: 20px;
    background: #8888 !important;
    color: inherit !important;
  }
  .notice_info {
    transition: all 0.8s;
  }
  .list-enter,
  .list-leave-to {
    opacity: 0;
  }
  .list-leave-active {
    position: absolute;
  }
  .loading {
    padding: 5px;
    margin: 5px 20px;
    border-radius: 5px;
    border: 1px #888 solid;
    font-size: 20px;
    text-align: center;
    cursor: default;
  }
  .notice_info_list {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    margin-bottom: 100px;
  }
}
</style>
