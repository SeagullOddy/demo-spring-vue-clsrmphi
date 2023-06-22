import axios from "axios";
import {ElMessage} from "element-plus";

// 请求时的默认配置
const defaultConfig = {
  onJudge: (data) => {
    return data.success
  },
  onSuccess: (data) => ElMessage.success(data.message),
  onFailure: (data) => ElMessage.warning(data.message),
  onError: (data) => ElMessage.error(
      '发生了一些错误，请联系管理员：' + data.message),
  contentType: "application/json;charset=UTF-8"
}

/**
 * 向别人的服务器发送 post 请求
 *
 * @param baseUrl 其他服务器的域名
 * @param url 请求地址
 * @param data 请求数据
 * @param config 配置，包含 onJudge, onSuccess, onFailure, onError, contentType
 */
function postOthers(baseUrl, url, data, config = {}) {
  config = Object.assign({}, defaultConfig, config);

  axios.post(url, data, {
    headers: {
      "Content-Type": config.contentType,
    }, baseURL: baseUrl, // 请求其他服务器需要配置 baseURL，覆盖 main.js 中设置的 defaults.baseURL
  }).then(({data}) => {
    if (config.onJudge(data)) {
      config.onSuccess(data);
    } else {
      config.onFailure(data);
    }
  }).catch(config.onError);
}

/**
 * 向自己的后端发送 post 请求
 *
 * @param url 请求地址
 * @param data 请求数据
 * @param config 配置，包含 onJudge, onSuccess, onFailure, onError, contentType
 */
function post(url, data, config = {},) {
  // 利用 Object.assign() 方法将 config 和 defaultConfig 合并，作为新的 config
  config = Object.assign({}, defaultConfig, config); // 深拷贝合并配置，避免修改了 defaultConfig

  axios.post(url, data, {
    headers: {
      "Content-Type": config.contentType // 使用配置中的 contentType
    }, withCredentials: true, // 允许携带 cookie，因为后端没有使用 JWT
  }).then(({data}) => { // 解构赋值，data 是响应体，其中包含 status, success, data 三个属性
    console.log(data);
    if (config.onJudge(data)) { // 调用配置中的 onJudge 方法，判断请求否成功
      config.onSuccess(data); // 如果成功，调用配置中的 onSuccess 方法
    } else {
      config.onFailure(data); // 如果失败，调用配置中的 onFailure 方法
    }
  }).catch(config.onError); // 如果发生错误，调用配置中的 onError 方法
}

/**
 * 向自己的后端发送 get 请求
 *
 * @param url 请求地址
 * @param config 配置，包含 onJudge, onSuccess, onFailure, onError
 */
function get(url, config = {}) {
  config = Object.assign({}, defaultConfig, config);

  axios.get(url, { // get 请求没有请求体，所以不需要配置 content-type
    withCredentials: true, // 允许携带 cookie，没有使用 JWT
  }).then(({data}) => {
    if (config.onJudge(data)) {
      config.onSuccess(data);
    } else {
      config.onFailure(data);
    }
  }).catch(config.onError);
}

export {postOthers, post, get};
