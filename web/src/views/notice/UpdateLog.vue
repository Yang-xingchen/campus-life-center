<template>
  <div>
    <div class="log" v-for="(log, v) in logs" :key="v">
      <div class="head">
        <a-tooltip class="item version" title="更新版本">{{ v }}</a-tooltip>
        <a-tooltip title="变更内容, 点击查看" class="item tags">
          <div class="item tags">
            <span
              class="tag"
              v-for="tag in log.tag"
              :key="tag"
              @click="changeShow(v, tag)"
            >
              {{ tagMap[tag] }}
            </span>
            <span class="tag unknown" v-show="!log.tag.length">
              未记录修改
            </span>
          </div>
        </a-tooltip>
        <a-tooltip class="item updateTime" title="更新时间">{{
          log.updateTime
        }}</a-tooltip>
      </div>
      <div class="diff" v-show="log.show">
        <div class="diff_head">
          <div class="close" @click="changeShow(v, '')">
            <a-icon type="up" />
          </div>
          <div class="diff_version">
            <span>版本: {{ v }}</span>
            <span>当前版本: {{ notice.version }}</span>
          </div>
        </div>
        <div class="box">
          <div class="old">
            {{ log.diff.old }}
          </div>
          <div class="new">
            {{ log.diff.new }}
          </div>
        </div>
      </div>
    </div>
    <a-divider>当前版本</a-divider>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { format_date } from "../../util";
const tagIgnore = ["version", "updateTime", "contentType"];
const tagMap = {
  publicType: "发布类型",
  title: "标题",
  contentType: "文本类型",
  content: "文本",
  importance: "基础重要度",
  startTime: "开始时间",
  endTime: "结束时间"
};
export default {
  name: "UpdateLog",
  data() {
    return {
      logs: {},
      tagIgnore,
      tagMap
    };
  },
  computed: {
    ...mapState({
      notice: state => state.notice
    })
  },
  methods: {
    changeShow(v, k) {
      this.logs[v].show = k != "";
      this.logs[v].diff.old = k ? this.logs[v][k] : "";
      this.logs[v].diff.new = k ? this.notice[k] : "";
      if (k === "startTime" || k === "endTime") {
        this.logs[v].diff.old = format_date(this.logs[v].diff.old);
        this.logs[v].diff.new = format_date(this.logs[v].diff.new);
      }
    },
    getLogs() {
      if (this.notice.version > 1) {
        let handel = data => {
          return Object.fromEntries(
            data.map(log => [
              log.version,
              {
                ...log,
                tag: Object.keys(log).filter(
                  k =>
                    this.tagIgnore.indexOf(k) === -1 &&
                    this.notice[k] !== log[k]
                ),
                updateTime: format_date(log.updateTime),
                show: false,
                diff: { old: "", new: "" }
              }
            ])
          );
        };
        this.request({
          method: "get",
          url: `/notice/${this.notice.id}/updateLog`
        }).then(logs => (this.logs = handel(logs)));
      }
    }
  },
  created() {
    this.getLogs();
  }
};
</script>
<style lang="less">
.log {
  margin-bottom: 10px;
  .head {
    border: 1px #888 solid;
    border-radius: 5px;
    display: flex;
    &:hover {
      border: 1px #8f88 solid;
    }
    .item {
      padding: 5px 10px;
      border-right: 1px #888 solid;
      text-align: center;
    }
    .tags {
      flex: auto;
      .tag {
        margin: 0 5px;
        cursor: pointer;
        &:hover {
          color: #8f8c;
        }
      }
      .unknown {
        color: #888;
        cursor: default;
        &:hover {
          color: #888;
        }
      }
    }
    .updateTime {
      width: 280px;
    }
  }
  .diff {
    margin: 0 10px;
    .diff_head {
      .close {
        cursor: pointer;
        border-left: 1px #888 solid;
        border-right: 1px #888 solid;
        text-align: center;
      }
      .diff_version {
        display: flex;
        span {
          flex: 1;
          border: 1px #888 solid;
          text-align: center;
        }
      }
    }
    .box {
      display: flex;
      .old,
      .new {
        border: 1px #888 solid;
        border-top: 1px #0000 solid;
        flex: 1;
        padding: 3px;
      }
    }
  }
}
</style>
