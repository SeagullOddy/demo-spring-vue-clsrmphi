<script setup>
import Notice from "@/components/Notice.vue";
import {useAuthStore} from "@/stores";
import {nextTick, ref} from "vue";
import {get} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";

// 退出登录
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

// 展示账户头像
const avatarImgRef = ref(null)
const authStore = useAuthStore()
nextTick(() => {
  avatarImgRef.value.src = authStore.getAccount().avatar
})
</script>

<template>
  <div class="component-header">
    <div class="header-content">
      <a class="h-left">
        <!--  标题左部分  -->
        <slot name="left"></slot>
      </a>
      <!--   标题中部   -->
      <slot/>
      <div class="h-right">
        <!--    通知    -->
        <el-popover width="450" trigger="click" placement="bottom-end"
                    popper-style="top: 50px; left: 940px">
          <template #reference>
            <span>
              <span class="notice component-notice_count el-popover__reference"
                    aria-describedby="el-popover-3869" tabindex="0">
                <i class="icon-notifications_outline font_family"></i>
              </span>
            </span>
          </template>
          <Notice/>
        </el-popover>
        <!--     头像   -->
        <el-popover trigger="click">
          <template #reference>
            <span>
              <span class="userinfo el-popover__reference" aria-describedby="el-popover-2094"
                    tabindex="0">
                <img ref="avatarImgRef" class="avatar" alt=""
                     src="https://assets.ketangpai.com//Public/Common/img/40/40.png">
              </span>
            </span>
          </template>
          <ul class="common_header-userinfo_drop">
            <li><i class="font_family icon-set_outline"></i>个人设置</li>
            <li @click="logout()"><i class="font_family icon-import_outline"></i>退出登录</li>
          </ul>
        </el-popover>
      </div>
    </div>
  </div>
</template>

<style>
.el-menu, .el-menu--horizontal > .el-menu-item:not(.is-disabled):focus, .el-menu--horizontal > .el-menu-item:not(.is-disabled):hover, .el-menu--horizontal > .el-submenu .el-submenu__title:hover {
  background-color: #fff;
}

.el-menu--horizontal > .el-menu-item {
  display: list-item;
  float: left;
  height: 60px;
  line-height: 60px;
  margin: 0;
  border-bottom: 2px solid transparent;
  color: #909399;
}
</style>
