<script setup>
import {get} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";

const logout = () => {
  get('/api/auth/logout', (result) => {
    ElMessage.success(result)
    router.push('/')
  }, (result) => {
    ElMessage.error(result)
  })
}
</script>

<template>
  Hello!
  <el-button type="primary" @click="logout">Logout</el-button>
</template>

<style scoped>

</style>
