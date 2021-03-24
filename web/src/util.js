export function init_notice() {
  return {
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
  };
}

export function init_publish() {
  return {
    notice: init_notice(),
    tag: [],
    todo: [],
    infoCollects: [],
    publishConditions: []
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

export function init_organization() {
  return {
    hide: false,
    type: "",
    name: "",
    visibility: 0
  };
}

export function init_role() {
  return {
    oid: "",
    name: "",
    permissions: [],
    permissions_id: [],
    aids: [],
    accounts: []
  };
}

export function format_date(d) {
  d = new Date(d);
  const now = new Date();
  let s = `${
    now.getFullYear() !== d.getFullYear() ? d.getFullYear() + "年" : ""
  }${d.getMonth() + 1}月${d.getDate()}日 ${d.getHours()}:${
    d.getMinutes() > 9 ? "" : "0"
  }${d.getMinutes()}`;
  return s;
}

import axios from "axios";
import notification from "ant-design-vue/es/notification";

export function request(options, err_f, handle) {
  err_f =
    err_f ||
    (res => {
      notification.error({
        message: "网络错误: " + res.response.status,
        description: res.response.messgae
      });
      return Promise.reject(res);
    });
  handle =
    handle ||
    (res => {
      if (res.data.success) {
        return res.data.data;
      } else {
        notification.error({
          message: res.data.code,
          description: res.data.message
        });
        return Promise.reject(res);
      }
    });
  // options.url = `http://localhost:8081${options.url}`;
  return axios(options)
    .catch(err_f)
    .then(handle);
}

import CryptoJS from "crypto-js";

export function getKey() {
  const s = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  let r = "";
  for (let i = 0; i < 16; i++) {
    r += s.charAt((Math.random() * s.length) | 0);
  }
  return r;
}

export function decode(secretKey, code) {
  let key = CryptoJS.enc.Utf8.parse(secretKey);
  let decrypt = CryptoJS.AES.decrypt(code, key, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
  });
  return CryptoJS.enc.Utf8.stringify(decrypt).toString();
}

export function encode(secretKey, message) {
  let key = CryptoJS.enc.Utf8.parse(secretKey);
  let srcs = CryptoJS.enc.Utf8.parse(message);
  let encrypted = CryptoJS.AES.encrypt(srcs, key, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
  });
  return encrypted.toString();
}
