<script setup>
import {get} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";
import {useAuthStore} from "@/stores";

const logout = () => {
  get('/api/auth/logout', {
    onSuccess: (data) => {
      ElMessage.success(data.result)
      // 登出成功之后，清空 store 中的账户信息
      useAuthStore().setAccount(null)
      router.push('/login')
    }
  })
}
</script>

<template>
  Hello!
  <el-button type="primary" @click="logout">Logout</el-button>
</template>

<style scoped>

</style>
