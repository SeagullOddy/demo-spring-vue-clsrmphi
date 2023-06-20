<script setup>
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import {get, post} from "@/net";
import router from "@/router";
import {useStore} from "@/stores";

// 登陆表单的引用
const loginFormRef = ref()

// 登陆表单的数据
const loginFormData = reactive({
  username: '',
  password: '',
  remember: false
})

// 登陆表单的验证规则
const rules = reactive({
  username: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
  ]
})

// 登陆（使用异步的方式），参数为表单元素（不是 ref）
const login = async (formEL) => {
  if (!formEL) {
    return
  }
  await formEL.validate((valid) => {
    if (!valid) {
      ElMessage.error('用户名和密码不可为空。')
      return
    }
    // 验证成功，发送登陆请求
    post('/api/auth/login', {
      username: loginFormData.username,
      password: loginFormData.password,
      remember: loginFormData.remember
    }, {
      // 登陆成功
      onSuccess: (data) => {
        ElMessage.success(data.result)
        // 如果用户信息为空，则获取用户登录的账户信息存入 store
        if (useStore().getAccount() == null) {
          get('/api/account/me', {
            onJudge: (data) => {
              return data.result != null
            },
            onSuccess: (data) => {
              useStore().setAccount(data.result)
              router.push('/main')
            },
            onFailure: () => {
              ElMessage.warning('登陆成功但获取用户信息失败，请刷新页面重试')
            }
          })
        }
      }
    })
  })
}
</script>

<template>
  <!-- 使用 ZoomViewStyle 进行分辨率适配 -->
  <div class="view-login" :style="useStore().getZoomViewStyle()">
    <div class="logo-box">
      <img src="/images/common/logo_blue.png" alt="">
    </div>
    <div class="login-content">
      <div class="left">
        <img src="/images/login/newbg.png" alt="">
      </div>
      <div class="right">
        <div class="right-content">
          <h2 class="title"><span>账号登录</span></h2>
          <ul class="tabs-nav">
            <li class="item activeLogin">
              账号登录
              <i class="line" style="background-color: rgb(66, 133, 244);"></i>
            </li>
            <li class="item">
              手机短信登录
              <i class="line" style="background-color: rgb(66, 133, 244);"></i>
            </li>
            <li class="item">
              微信登录
              <i class="line" style="background-color: rgb(66, 133, 244);"></i>
            </li>
          </ul>
          <div class="login-tab">
            <el-form
                ref="loginFormRef"
                :model="loginFormData"
                :rules="rules"
                size="large"
                status-icon>
              <el-form-item class="margin-bottom" prop="username">
                <el-input v-model="loginFormData.username" autocomplete="on"
                          placeholder="请输入邮箱/手机号/账号"/>
              </el-form-item>
              <el-form-item class="margin-bottom" prop="password">
                <el-input v-model="loginFormData.password" type="password"
                          placeholder="请输入密码"/>
              </el-form-item>
              <el-form-item class="margin-bottom">
                <div class="flex-between" style="width: 100%;">
                  <el-checkbox v-model="loginFormData.remember">下次自动登录</el-checkbox>
                  <!--     TODO 忘记密码     -->
                  <el-link @click="ElMessage.warning('功能暂时未实现')">忘记密码？</el-link>
                </div>
              </el-form-item>
            </el-form>
            <div class="bottom-box">
              <div class="margin-top">
                <el-button
                    type="primary"
                    size="large"
                    @click="login(loginFormRef)"
                    style="width: 100%">登录
                </el-button>
              </div>
              <div class="go-register text-right font14 flex-align flex-between">
                <p></p>
                <p> 还没有账号？
                  <span @click="router.push('/register')" class="font-color--main"
                        style="cursor: pointer;">去注册</span>
                </p>
              </div>
            </div>
            <div class="qr-box">
              <div class="qr-code"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style src="@/assets/css/login-register.css"/>
<style src="@/assets/css/login.css" scoped/>
