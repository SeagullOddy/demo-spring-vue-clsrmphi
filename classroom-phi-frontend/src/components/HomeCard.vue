<script setup>

</script>

<template>
  <div class="component-home_card">
    <!--  TODO 背景图片  -->
    <div class="header-info" style="">
      <p class="time">2022-2023 第二学期</p>
      <h3 title="python程序设计与数据分析" class="name text_overflow1">python程序设计与数据分析</h3>
      <p title="121230201,02,03,04" class="classname text_overflow1">121230201,02,03,04</p>
      <div class="qrcode">
        <img src="@/assets/img/qr.0b314396.svg" class="img" alt=""><span style="font-size: 14px;">加课码:X2ZE3S</span>
      </div>
    </div>
    <div class="content-info"></div>
    <div class="user-info">
      <div class="left"><span style="cursor: pointer;"> 负责人:张金荣 </span></div>
      <div class="right">
        <div class="set-top"><img src="@/assets/img/top.6cb08607.svg" alt=""> 置顶</div>
        <el-dropdown class="handle" size="large">
          <span class="el-dropdown-link el-dropdown-selfdefine">
            <img src="@/assets/img/etc.52da4ee0.svg" alt="">
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>归档</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
    <el-dialog align-center>
      <template #footer>
        <div class="dialog-footer">
          <el-button size="large">取消</el-button>
          <el-button type="primary" size="large">确定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>

</style>
