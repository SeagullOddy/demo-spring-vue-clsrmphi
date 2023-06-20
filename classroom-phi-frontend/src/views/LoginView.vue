<script setup>
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import {get, post} from "@/net";
import router from "@/router";
import {useAuthStore} from "@/stores";

// 适配 html 缩放
const viewLoginStyle = ref({
  minHeight: "calc(100vh / " + zoomRate + ")"
})

// 登陆表单的引用
const loginFormRef = ref(null)
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

// 登陆验证
const login = async (formEL) => {
  if (!formEL) {
    return
  }
  await formEL.validate((valid, fields) => {
    if (!valid) {
      ElMessage.error('用户名和密码不可为空。')
      return
    }
    post('/api/auth/login', {
      username: loginFormData.username,
      password: loginFormData.password,
      remember: loginFormData.remember
    }, {
      onSuccess: (data) => {
        ElMessage.success(data.result)
        // 登陆成功之后，获取用户信息存入 store
        get('/api/account/me', {
          onSuccess: (data) => {
            useAuthStore().setAccount(data.result)
          }
        })
        router.push('/main')
      }
    })
  })
}
</script>

<template>
  <div class="view-login" :style="viewLoginStyle">
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
                  <el-link>忘记密码？</el-link>
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
<style scoped>
[class*=driver-close-btn] {
  background: transparent;
  color: #969696;
  border: none
}

div#driver-popover-item .driver-popover-footer button {
  background-color: #1890ff;
  color: #fff;
  text-shadow: none;
  border: none;
  line-height: 1.8;
  border-radius: 4px
}

div#driver-popover-item .driver-popover-footer button:hover {
  background-color: #50abff
}

div#driver-popover-item .driver-popover-footer button:active {
  background-color: #1269ba
}

div#driver-popover-item .driver-popover-footer .driver-close-btn {
  background: transparent;
  color: #969696;
  border: none
}

div#driver-popover-item .driver-popover-footer .driver-close-btn:hover {
  background-color: transparent
}

.view-login {
  background-size: 100%;
  background-repeat: no-repeat
}

.view-login .go-register {
  margin-top: 24px
}

.view-login .go-register a img {
  display: block
}

.weichat-tip {
  text-align: center;
  padding-top: 100px;
  width: 100%;
  height: 300px;
  color: #969696
}

.tabs-nav li {
  position: relative
}

.tabs-nav li .line {
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  display: none;
  width: 60px;
  height: 2px
}

.tabs-nav .activeLogin .line {
  display: block
}

.bottom-box .otherSchool {
  justify-content: center;
  margin-top: 14px;
  padding: 12px 20px;
  width: 100%;
  height: 48px;
  font-size: 14px;
  font-weight: 500;
  background: #fff;
  border-width: 1px;
  border-style: solid;
  border-radius: 4px;
  transition: .1s;
  cursor: pointer
}
</style>
