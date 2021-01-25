export function init_publish() {
  return {
    notice: {
      creator: "",
      organization: 0,
      visibility: 0,
      importance: 3,
      publicType: 0,
      title: "",
      contentType: 0,
      content: "",
      startTime: null,
      endTime: null
    },
    tag: [],
    todo: [],
    infoCollects: [],
    accountList: [],
    todoList: [],
    infoList: [],
    organizationList: []
  };
}
export function init_collect(id) {
  return {
    _id: id,
    exist: false,
    multiple: false,
    type: 1,
    name: "",
    compositeInfo: []
  };
}

export function init_info(id) {
  return {
    _id: id,
    exist: false,
    multiple: false,
    type: 0,
    order: id,
    name: "",
    // 文本
    sample: "",
    textType: 0,
    regular: "",
    // 组合
    compositeInfo: [],
    // 单选
    radioInfo: []
  };
}
