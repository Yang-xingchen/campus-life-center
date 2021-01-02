<template>
  <div :class="['notice_info', 'i' + notice.accountImportance]" @click="click">
    <div class="title">
      <a-icon type="pushpin" v-if="notice.top" />
      {{ notice.title }}
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
export default {
  name: "NoticeCard",
  props: {
    notice: Object
  },
  methods: {
    click() {
      this.$router.push("/notice/" + this.notice.id);
    }
  },
  computed: {
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

<style lang="less">
.notice_info {
  height: 198px;
  width: 352px;
  margin: 5px 9px;
  background: rgba(255, 255, 255, 0.25);
  border-radius: 3px;
  position: relative;
  cursor: default;
  &:hover {
    cursor: pointer;
  }
  .title {
    color: rgb(242, 213, 3);
    position: absolute;
    left: 10px;
    top: 10px;
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
.i1 {
  background: rgba(255, 255, 255, 0.25);
  &:hover {
    background: rgba(255, 255, 255, 0.5);
  }
}
.i2 {
  background: rgba(255, 204, 204, 0.25);
  &:hover {
    background: rgba(255, 204, 204, 0.5);
  }
}
.i3 {
  background: rgba(255, 153, 153, 0.25);
  &:hover {
    background: rgba(255, 153, 153, 0.5);
  }
}
.i4 {
  background: rgba(255, 102, 102, 0.25);
  &:hover {
    background: rgba(255, 102, 102, 0.5);
  }
}
.i5 {
  background: rgba(255, 51, 51, 0.25);
  &:hover {
    background: rgba(255, 51, 51, 0.5);
  }
}
</style>
