<script setup>
import {reactive, ref} from 'vue'
import {ElMessage} from "element-plus";
import router from "@/router";
import {post} from "@/net";

const registerFormRef = ref()
const teacherRoleRef = ref()
const studentRoleRef = ref()
const chooseStudent = ref(false)

const registerFormData = reactive({
  emailOrTelephone: '',
  password: '',
  password2: '',
  role: 'TEACHER',
  name: '',
  school: '',
  studentNo: '',
  verify: ''
})

const telephoneRegExp = /^1[3456789]\d{9}$/
const emailRegExp = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/
const emailOrTelephoneValidator = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入邮箱/手机号'))
  } else if (!emailRegExp.test(value) && !telephoneRegExp.test(value)) {
    callback(new Error('格式不正确'))
  } else {
    callback()
  }
}

const passwordRegExp = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()_+`\-={}:";'<>?,.\/]).*$/
const passwordValidator = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  } else if (value.length < 8 || value.length > 20) {
    callback(new Error('密码长度为8-20位'))
  } else if (!passwordRegExp.test(value)) {
    callback(new Error('密码必须包含字母、数字和特殊字符'))
  } else {
    callback()
  }
}

const password2Validator = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== registerFormData.password) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}

const rules = reactive({
  emailOrTelephone: [
    {validator: emailOrTelephoneValidator, trigger: 'blur'}
  ],
  password: [
    {validator: passwordValidator, trigger: 'blur'},
  ],
  password2: [
    {validator: password2Validator, trigger: 'blur'}
  ],
  name: [
    {required: true, message: '请输入姓名', trigger: 'blur'}
  ],
  school: [
    {required: true, message: '请输入学校/机构', trigger: 'blur'}
  ],
  studentNo: [
    {required: true, message: '请输入学号', trigger: 'blur'}
  ],
})

const register = async (formEl) => {
  if (!formEl) {
    return
  }
  await formEl.validate((valid, fields) => {
    if (!valid) {
      ElMessage.error('请检查输入项')
      return
    }
    post('/api/auth/register', {
      emailOrTelephone: formEl.emailOrTelephone,
      password: formEl.password,
      role: formEl.role,
      name: formEl.name,
      school: formEl.school,
      verify: formEl.verify
    }, (result) => {
      ElMessage.success(result)
      router.push('/login')
    }, (result) => {
      ElMessage.error(result)
    })
  })
}

const changeRole = (role) => {
  if (role === 'TEACHER') {
    studentRoleRef.value.classList.remove('active')
    teacherRoleRef.value.classList.add('active')
    chooseStudent.value = false
  } else if (role === 'STUDENT') {
    teacherRoleRef.value.classList.remove('active')
    studentRoleRef.value.classList.add('active')
    chooseStudent.value = true
  }
  registerFormData.role = role
}
</script>

<template>
  <div class="view-login view-regist" style="min-height: calc(76.9231vh);">
    <div class="logo-box">
      <img src="/images/common/logo_blue.png" alt="">
    </div>
    <div class="login-content">
      <div class="left">
        <img src="/images/login/bg.png" alt="">
      </div>
      <div class="right">
        <div class="right-content"><h2 class="title"><span>注册账号</span></h2>
          <el-form
              ref="registerFormRef"
              :model="registerFormData"
              :rules="rules"
              size="large"
              status-icon>
            <el-form-item class="margin-bottom" prop="emailOrTelephone">
              <el-input v-model="registerFormData.emailOrTelephone" autocomplete="on"
                        placeholder="请输入邮箱/手机号"/>
            </el-form-item>
            <el-form-item class="margin-bottom" prop="password">
              <el-input v-model="registerFormData.password" type="password"
                        placeholder="请输入密码"/>
            </el-form-item>
            <el-form-item class="margin-bottom" prop="password2">
              <el-input v-model="registerFormData.password2" type="password"
                        placeholder="请再次输入密码确认"/>
            </el-form-item>
            <el-form-item class="margin-bottom">
              <p class="font-bold font16">选择身份</p>
              <div class="role-box">
                <div ref="teacherRoleRef"
                     @click="changeRole('TEACHER')"
                     class="item flex-align active">
                  <img src="@/assets/img/teacher.svg" class="icon" alt=""><span
                    class="name">老师</span>
                </div>
                <div ref="studentRoleRef"
                     @click="changeRole('STUDENT')"
                     class="item flex-align">
                  <img src="@/assets/img/student.svg" class="icon" alt=""><span
                    class="name">学生</span>
                </div>
              </div>
            </el-form-item>
            <el-form-item class="margin-bottom" prop="name">
              <el-input v-model="registerFormData.name" placeholder="请输入姓名"/>
            </el-form-item>
            <el-form-item class="margin-bottom" prop="school">
              <el-input v-model="registerFormData.school" placeholder="请输入学校/机构"/>
            </el-form-item>
            <el-form-item v-if="chooseStudent" class="margin-bottom" prop="studentNo">
              <el-input v-model="registerFormData.studentNo" placeholder="请输入学号"/>
            </el-form-item>
            <el-form-item class="margin-bottom">
              <div class="flex-between">
                <el-input v-model="registerFormData.verify" type="number"
                          placeholder="请输入计算结果"/>
                <img
                    src="https://openapiv5.ketangpai.com/UserApi/verify?sessionid=b9nfbqtdt8mhpnjta2kvt369s3&amp;time=0"
                    class="verify-img" style="cursor: pointer;" alt=""/>
              </div>
            </el-form-item>
          </el-form>
          <div class="bottom-box">
            <div class="margin-top">
              <el-button @click="register(registerFormRef)" type="primary" size="large"
                         style="width: 100%;">注册
              </el-button>
            </div>
            <div class="margin-top text-right font14">
              已有账号？
              <span @click="router.push('/login')" class="font-color--main"
                    style="cursor: pointer;">去登录</span>
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

.view-regist {
  display: flex;
  flex-direction: column;
  background-size: 100%;
  background-repeat: no-repeat;
  padding-top: 20px
}

.view-regist .login-content {
  flex: 1;
  display: flex;
  align-items: center
}

.view-regist .login-content .right {
  margin: 12px 0
}

.view-regist .login-content .right .verify-img {
  cursor: pointer;
  display: block;
  border-radius: 8px;
  margin-left: 16px;
  width: 154px;
  height: 48px
}

.view-regist .login-content .right .bottom-box {
  padding-bottom: 12px;
  margin-top: 12px
}

.view-regist .email-success {
  padding: 12px 0
}

.view-regist .email-success .el-button {
  height: 34px;
  padding-top: 0;
  padding-bottom: 0;
  line-height: 32px
}

.view-regist .email-success h1 {
  color: #00ca90;
  font-size: 22px;
  margin-bottom: 10px
}

.view-regist .email-success h1 i {
  font-size: 22px;
  margin-right: 10px
}

.view-regist .email-success p {
  font-size: 14px;
  color: #3c4043;
  line-height: 1.5
}

.view-regist .email-success .email-tip {
  font-size: 12px;
  color: #4285f4
}
</style>
