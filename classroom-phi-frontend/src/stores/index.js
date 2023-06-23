import {reactive} from 'vue'
import {defineStore} from 'pinia'

/**
 * pinia store，用于存储全局数据
 */
export const useStore = defineStore('store', () => {
  // 数据
  const store = reactive({
    initApp: true, // 是否是新加载网页
    account: null, // 当前登录的账户信息
    virtualTelephone: '000000000000', // 虚拟电话号码，用于向课堂派验证码接口发送请求
    zoomViewStyle: {minHeight: 'calc(100vh / ' + zoomRate + ')'} // 在对应缩放比例下的 View 样式，此时 zoomRate 已经存在
  })

  // 方法
  const isInitApp = () => {
    return store.initApp
  }
  const setInitApp = (initApp) => {
    store.initApp = initApp
  }

  const getAccount = () => {
    return store.account
  }
  const setAccount = (user) => {
    store.account = user
  }

  // 为了在同时注册多个账号的场景下，避免因为使用同一个虚拟手机号导致请求验证码被拒绝，因此只提供获取下一个虚拟电话号码的方法
  const nextVirtualTelephone = () => {
    let nextNum = parseInt(store.virtualTelephone) + 1
    let nextTelephone = nextNum.toString().padStart(12, '0')
    store.virtualTelephone = nextTelephone
    return nextTelephone
  }

  const getZoomViewStyle = () => {
    return store.zoomViewStyle
  }

  return {
    isInitApp,
    setInitApp,
    getAccount,
    setAccount,
    nextVirtualTelephone,
    getZoomViewStyle
  }
})
