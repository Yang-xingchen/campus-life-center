<template>
  <div
    :class="['notice_info', theme, 'i' + notice.accountImportance]"
    @click="click"
  >
    <div class="title_box">
      <div :class="['title', theme]">
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
  </div>
</template>

<script>
import { mapState } from "vuex";
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
    ...mapState(["theme"]),
    time() {
      const format_date = d => {
        const now = new Date();
        let s = "";
        if (now.getFullYear() !== d.getFullYear()) {
          s += d.getFullYear() + ".";
        }
        s += d.getMonth() + 1 + "月" + d.getDate() + "日";
        s += d.getHours() + ":" + d.getMinutes();
        return s;
      };
      if (this.notice.publicType === 0) {
        return "";
      }
      const start = new Date(this.notice.startTime);
      if (this.notice.publicType === 1) {
        return format_date(start);
      }
      const end = new Date(this.notice.endTime);
      if (this.notice.publicType === 2) {
        return format_date(start) + "  至  " + format_date(end);
      }
      return "";
    }
  }
};
</script>

<style lang="less" scope>
@import "../../assets/theme.less";
.notice_info {
  height: 198px;
  width: 352px;
  margin: 5px 9px;
  border-radius: 3px;
  position: relative;
  cursor: default;
  &:hover {
    cursor: pointer;
  }
  .title {
    &.dark {
      color: @d-afg;
    }
    &.light {
      color: @l-afg;
    }
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
    top: 35px;
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
.dark {
  @bg: #fff;
  @hbg: #fff;
  &.i5 {
    background: fade(@bg - #0dd0, 25%);
    &:hover {
      background: fade(@hbg - #0dd0, 50%);
    }
  }
  &.i4 {
    background: fade(@bg - #0bb0, 25%);
    &:hover {
      background: fade(@hbg - #0bb0, 50%);
    }
  }
  &.i3 {
    background: fade(@bg - #0990, 25%);
    &:hover {
      background: fade(@hbg - #0990, 50%);
    }
  }
  &.i2 {
    background: fade(@bg - #0770, 25%);
    &:hover {
      background: fade(@hbg - #0770, 50%);
    }
  }
  &.i1 {
    background: fade(@bg - #0550, 25%);
    &:hover {
      background: fade(@hbg - #0550, 50%);
    }
  }
}
.light {
  @bg: #000;
  @hbg: #000;
  &.i5 {
    background: fade(@bg + #0dd0, 25%);
    &:hover {
      background: fade(@hbg + #0dd0, 50%);
    }
  }
  &.i4 {
    background: fade(@bg + #0bb0, 25%);
    &:hover {
      background: fade(@hbg + #0bb0, 50%);
    }
  }
  &.i3 {
    background: fade(@bg + #0990, 25%);
    &:hover {
      background: fade(@hbg + #0990, 50%);
    }
  }
  &.i2 {
    background: fade(@bg + #0770, 25%);
    &:hover {
      background: fade(@hbg + #0770, 50%);
    }
  }
  &.i1 {
    background: fade(@bg + #0550, 25%);
    &:hover {
      background: fade(@hbg + #0550, 50%);
    }
  }
}
</style>
