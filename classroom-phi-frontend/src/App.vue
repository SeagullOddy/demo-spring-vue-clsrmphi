<script setup>
import {get} from "@/net";
import {useStore} from "@/stores";
import router from "@/router";
import {ElMessage} from "element-plus";

// 首次挂载 App 时，向后端获取一次当前用户的登录信息，存入 store 中，然后跳转对应页面
const store = useStore()
if (store.getAccount() == null) {
  get('/api/account/me', {
    // 已登陆，跳转到主页
    onSuccess: (result) => {
      ElMessage.success('欢迎回来，' + result.data.name)
      store.setAccount(result.data)
      router.push('/main')
    },
    // 未登录，跳转到登录页面
    onFailure: () => {
      ElMessage.warning('您还未登录，请先登录')
      router.push('/login')
    },
    // 请求出错，跳转到登录页面
    onError: (result) => {
      ElMessage.error('获取用户信息失败，请尝试重新登录：' + result.message)
      router.push('/login')
    }
  })
}
</script>

<template>
  <router-view/>
</template>

<!-- 使用外部 css 文件使得代码更简洁，也更好维护 -->
<style src="@/assets/css/app.css"/>
