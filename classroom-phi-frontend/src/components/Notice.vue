<script setup>
</script>

<template>
  <div class="component-notice">
    <el-tabs tab-position="top">
      <el-tab-pane label="全部"></el-tab-pane>
      <el-tab-pane label="教学活动"></el-tab-pane>
      <el-tab-pane label="教务通知"></el-tab-pane>
      <el-tab-pane label="系统通知"></el-tab-pane>
    </el-tabs>
    <div class="handle flex-between">
      <div class="btn"><i class="font_family icon-pichuli_outline"></i>一键标为已读</div>
      <div class="btn"><i class="font_family icon-liulan"></i>查看全部</div>
    </div>
  </div>
</template>

<style>
.el-tabs__item {
  padding: 0 20px;
  height: 40px;
  box-sizing: border-box;
  line-height: 40px;
  display: inline-block;
  list-style: none;
  font-size: 16px;
  font-weight: 500;
  color: #303133;
  position: relative;
}
</style>
