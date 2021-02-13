<template>
  <a-tooltip
    :class="['notice_info', 'i' + notice.accountImportance]"
    @click="click"
    :title="notice.title"
  >
    <div class="title_box">
      <div class="title">
        <a-icon type="pushpin" v-if="notice.top" />
        {{ notice.title }}
      </div>
      <a-tooltip class="open_page" title="新建页面打开" @click.stop="openPage"
        ><a-icon type="file-add"
      /></a-tooltip>
    </div>
    <div class="tags">
      <a-tag
        v-for="tag in notice.showTag"
        :key="tag"
        color="#00000040"
        class="tag"
        >{{ tag }}</a-tag
      >
    </div>
    <div class="time" v-if="notice.startTime">
      {{ time }}
    </div>
  </a-tooltip>
</template>

<script>
import { format_date } from "../../util";
export default {
  name: "NoticeCard",
  props: {
    notice: Object
  },
  methods: {
    click() {
      this.$router.push("/notice/" + this.notice.id);
    },
    openPage() {
      window.open("/#/notice/" + this.notice.id, "_blank");
    }
  },
  computed: {
    time() {
      if (this.notice.publicType === 0) {
        return "";
      }
      if (this.notice.publicType === 1) {
        return format_date(this.notice.startTime);
      }
      if (this.notice.publicType === 2) {
        return `${format_date(this.notice.startTime)}  至  ${format_date(
          this.notice.endTime
        )}`;
      }
      return "";
    }
  }
};
</script>

<style lang="less" scope>
.notice_info {
  height: 198px;
  width: 352px;
  margin: 5px 9px;
  border-radius: 3px;
  position: relative;
  cursor: pointer;
  .title {
    font-size: 20px;
    background: #0000;
    position: absolute;
    left: 10px;
    top: 10px;
  }
  .open_page {
    right: 10px;
    top: 10px;
    position: absolute;
    font-size: 16px;
  }
  .tags {
    position: absolute;
    top: 40px;
    left: 7px;
    right: 7px;
    bottom: 35px;
    display: flex;
    flex-wrap: wrap;
    align-content: flex-start;
    overflow: hidden;
    .tag {
      padding: 3px 5px;
      float: left;
      margin: 3px;
    }
  }
  .time {
    font-size: 14px;
    position: absolute;
    left: 10px;
    bottom: 15px;
  }
}
.i1 {
  background: #c667;
  &:hover {
    background: #a665;
  }
}
.i2 {
  background: #c669;
  &:hover {
    background: #a667;
  }
}
.i3 {
  background: #c66b;
  &:hover {
    background: #a669;
  }
}
.i4 {
  background: #c66d;
  &:hover {
    background: #a66b;
  }
}
.i5 {
  background: #c66f;
  &:hover {
    background: #a66d;
  }
}
</style>
