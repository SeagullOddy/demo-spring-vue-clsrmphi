<script setup>
import Header from "@/components/Header.vue";
</script>

<template>
  <Header>
    <template #left>
      <img src="/images/common/logo_blue.png" style="max-height: 28px;" alt="">
    </template>
    <el-menu class="el-menu-demo" mode="horizontal" :ellipsis="false">
      <el-menu-item index="1" class="is-active">我的课堂</el-menu-item>
    </el-menu>
  </Header>
  <router-view/>
</template>

<style src="@/assets/css/main.css" scoped/>
