import {reactive} from 'vue'
import {defineStore} from 'pinia'

/**
 * 用于认证的 pinia store，暴露了账户的获取和设置方法
 */
export const useAuthStore = defineStore('auth', () => {
  const authStore = reactive({
    account: null,
  })
  const getAccount = () => {
    return authStore.account
  }
  const setAccount = (user) => {
    authStore.account = user
  }
  return {getAccount, setAccount}
})
