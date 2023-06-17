import axios from "axios";
import {ElMessage} from "element-plus";

const defaultError = (result) => ElMessage.error(
    '发生了一些错误，请联系管理员：' + result);
const defaultFailure = (result) => ElMessage.warning(result);

function post(url, data, success, failure = defaultFailure,
    error = defaultError) {
  axios.post(url, data, {
    headers: {
      "Content-Type": "application/x-www-form-urlencoded", // 表单数据
    }, withCredentials: true, // 允许携带 cookie，没有使用 JWT
  }).then(({data}) => {
    if (data.success) {
      success(data.result, data.status);
    } else {
      failure(data.result, data.status);
    }
  }).catch(error);
}

function get(url, success, failure = defaultFailure, error = defaultError) {
  axios.get(url, {
    withCredentials: true, // 允许携带 cookie，没有使用 JWT
  }).then(({data}) => {
    if (data.success) {
      success(data.result, data.status);
    } else {
      failure(data.result, data.status);
    }
  }).catch(error);
}

export {post, get};
