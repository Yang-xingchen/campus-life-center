<template>
  <div :class="['notice_info', 'i' + notice.notice.importance]" @click="click">
    <div class="title">{{ notice.notice.title }}</div>
    <div class="tags">
      <div class="tag" v-for="tag in notice.tag" :key="tag">
        {{ tag }}
      </div>
    </div>
    <div class="time" v-if="notice.notice.startTime">
      {{ time }}
    </div>
  </div>
</template>

<script>
export default {
  name: "NoticeInfo",
  props: {
    notice: Object
  },
  methods: {
    click() {
      console.log(this.notice);
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
      if (this.notice.notice.publicType === 0) {
        return "";
      }
      const start = new Date(this.notice.notice.startTime);
      if (this.notice.notice.publicType === 1) {
        return format_date(start);
      }
      const end = new Date(this.notice.notice.endTime);
      if (this.notice.notice.publicType === 2) {
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
    bottom: 30px;
    display: flex;
    .tag {
      padding: 3px 5px;
      float: left;
      margin: 3px;
      background: rgba(0, 0, 0, 0.25);
      height: 24px;
      line-height: 24px;
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
