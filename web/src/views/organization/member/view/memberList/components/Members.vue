<template>
  <div class="members">
    <div class="member" v-for="member in show_members" :key="member.id">
      {{ member.name }}
      <a-tooltip class="del_button" title="删除" v-show="editable">
        <a-icon type="close-circle" @click="delMember(member)" />
      </a-tooltip>
    </div>
    <div class="member add" v-for="member in addMembers" :key="member.id">
      {{ member.name }}
      <a-tooltip class="del_button" title="删除" v-show="editable">
        <a-icon type="close-circle" @click="delMember(member)" />
      </a-tooltip>
    </div>
    <div class="member del" v-for="member in delMembers" :key="member.id">
      {{ member.name }}
    </div>
    <div
      class="member add_button"
      @click="show_add_box = true"
      v-show="editable && !show_add_box"
    >
      <a-icon type="plus" />
    </div>
    <div
      class="member add_button"
      @click="show_add_box = false"
      v-show="editable && show_add_box"
    >
      <a-icon type="close" />
    </div>
    <div class="member add_box" v-show="show_add_box">
      <div
        class="member"
        v-for="member in allMembers"
        :key="member.signId"
        @click="addMember(member)"
      >
        {{ member.name }}
      </div>
    </div>
    <div v-show="ignore" />
  </div>
</template>

<script>
export default {
  name: "Members",
  props: {
    members: Array,
    allMembers: Array,
    addMembers: Array,
    delMembers: Array,
    editable: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  data() {
    return {
      show_add_box: false,
      show_members: []
    };
  },
  watch: {
    delMembers() {
      this.update_member();
    }
  },
  methods: {
    update_member() {
      let dels = (this.delMembers || []).map(m1 => m1.id);
      this.show_members = this.members.filter(m => dels.indexOf(m.id) === -1);
    },
    addMember(member) {
      if (!this.addMembers.filter(m => m.id === member.id).length) {
        if (
          !this.members.filter(m => m.id === member.id).length ||
          this.delMembers.filter(m => m.id === member.id).length
        ) {
          this.$emit("add", member);
        }
      }
      this.show_add_box = false;
    },
    delMember(member) {
      this.$emit("del", member);
      this.update_member();
    }
  },
  created() {
    this.update_member();
  }
};
</script>

<style lang="less" scoped>
.members {
  display: flex;
  flex-wrap: wrap;
  .member {
    width: 100px;
    height: 100px;
    background: #8882;
    text-align: center;
    border-radius: 5px;
    margin: 5px;
    padding-top: 10px;
    cursor: default;
    .del_button {
      display: block;
      padding-top: 20px;
      color: #c888;
      cursor: pointer;
      font-size: 25px;
      &:hover {
        color: #c884;
      }
    }
  }
  .add {
    background: #88f2;
  }
  .del {
    background: #f882;
  }
  .add_box {
    width: auto;
    height: 210px;
    overflow-y: auto;
    display: flex;
    flex-wrap: wrap;
    background: #0000;
    border: 1px #888 solid;
    .member:hover {
      cursor: pointer;
      background: #8884;
    }
  }
  .add_button {
    width: 100px;
    height: 100px;
    border: 2px #888 dashed;
    margin: 5px;
    border-radius: 5px;
    cursor: pointer;
    line-height: 50px;
    font-size: 50px;
    padding: 25px;
    text-align: center;
    color: #888;
    &:hover {
      border: 2px #888 solid;
      background: #8882;
    }
  }
}
</style>
