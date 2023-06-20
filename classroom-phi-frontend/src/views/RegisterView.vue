<script setup>
import {reactive, ref} from 'vue'
import {ElMessage} from "element-plus";
import router from "@/router";
import {post, postOthers} from "@/net";

// HTML 元素的引用
const registerFormRef = ref(null)
const teacherRoleRef = ref(null)
const studentRoleRef = ref(null)
const figureCodeImgRef = ref(null)

// 是否选择学生身份，用于控制学号输入框的显示
const chooseStudent = ref(false)

// 虚拟的十二位手机号，用于课堂派的接口
const virtualTelephone = ref('000000000000')

// 注册表单的数据
const registerFormData = reactive({
  emailOrTelephone: '', // 邮箱/手机号
  password: '',
  password2: '',
  accountRole: 'TEACHER', // 默认为老师，注册页面打开时默认选则的是老师身份
  name: '',
  school: '',
  studentNo: null, // 默认为 null，此时在后端不会收到该字段
  verify: '' // 验证码
})

// 注册表单验证器和验证规则
// 邮箱、电话正则表达式来源于：https://c.runoob.com/front-end/854/
const emailRegExp = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
const telephoneRegExp = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
const emailOrTelephoneValidator = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入邮箱/手机号'))
  } else if (value.length > 30) {
    callback(new Error('邮箱/手机号长度不能超过30位'))
  } else if (!emailRegExp.test(value) && !telephoneRegExp.test(value)) {
    callback(new Error('格式不正确'))
  } else {
    callback()
  }
}
// 密码必须包含数字、字母和特殊字符
const passwordRegExp = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[ -/:-@\[-`{-~]).*$/
const passwordValidator = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  } else if (value.length < 8 || value.length > 20) {
    callback(new Error('密码长度为8-20位'))
  } else if (!passwordRegExp.test(value)) {
    callback(new Error('密码必须包含数字、字母和特殊字符'))
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
    {required: true, message: '请输入姓名', trigger: 'blur'},
    {min: 2, max: 20, message: '姓名长度为2-20位', trigger: 'blur'}
  ],
  school: [
    {required: true, message: '请输入学校/机构', trigger: 'blur'},
    {min: 2, max: 50, message: '学校/机构长度为2-50位', trigger: 'blur'}
  ],
  studentNo: [
    {required: true, message: '请输入学号', trigger: 'blur'},
    {min: 5, max: 20, message: '学号长度为5-20位', trigger: 'blur'}
  ],
})

// 切换角色方法
const changeRole = (accountRole) => {
  studentRoleRef.value.classList.toggle('active')
  teacherRoleRef.value.classList.toggle('active')
  chooseStudent.value = (accountRole === 'STUDENT')
  registerFormData.accountRole = accountRole
}

// 请求图形验证码
const figureCodeData = {
  url: '',
  sessionid: ''
}
const getFigureCode = () => {
  postOthers('https://openapiv5.ketangpai.com/', '/UserApi/getFigureCode',
      {
        reqtimestamp: new Date().getTime(), // 当前时间戳
      }, {
        onJudge: (data) => {
          return data.status === 1 // status === 1 -> true -> 请求成功
        },
        onSuccess: (data) => {
          figureCodeData.url = data.data.url
          figureCodeData.sessionid = data.data.sessionid
          figureCodeImgRef.value.src = data.data.url + '&time=0'
          // 每次请求图形验证码后，虚拟手机号数 + 1
          virtualTelephone.value = (parseInt(virtualTelephone.value) + 1).toString().padStart(12,
              '0')
        },
        contentType: 'application/json;charset=UTF-8' // 课堂派的接口需要使用 json 格式
      })
}

// 注册方法，参数为注册表单元素（不是 ref）
const register = async (registerFormEl) => {
  if (!registerFormEl) {
    return
  }
  // 验证表单
  await registerFormEl.validate((valid, fields) => {
    if (!valid) {
      ElMessage.error('请检查输入项')
      return
    }
    // 检验验证码，使用了课堂派的接口
    postOthers('https://openapiv5.ketangpai.com/', '/UserApi/sendCode',
        {
          type: 'reg', // 类型为注册
          verify: registerFormData.verify, // 用户输入的验证码
          mobile: virtualTelephone.value, // 手机号，假拟一个而不要使用用户输入的手机号
          sessionid: figureCodeData.sessionid, // 前面得到的图形验证码的 sessionid
          secondDomain: '', // 二级域名，不需要
          reqtimestamp: new Date().getTime(), // 当前时间戳
        }, {
          // 判断接口返回结果
          onJudge: (data) => {
            return data.status === 1; // status === 1 -> true -> 请求成功
          },
          // 验证码正确，向自己的后端发送注册请求
          onSuccess: () => {
            post('/api/auth/register',
                {
                  emailOrTelephone: registerFormData.emailOrTelephone,
                  password: registerFormData.password,
                  accountRole: registerFormData.accountRole,
                  name: registerFormData.name,
                  school: registerFormData.school,
                  studentNo: registerFormData.studentNo // 选择老师时，学号为 null
                }, {
                  onSuccess: (data) => {
                    ElMessage.success(data.result) // 自己后端返回的信息在 result 属性中
                    router.push('/login')
                  },
                  // 注册失败，提示并重新获取验证码
                  onFailure: (data) => {
                    ElMessage.warning(data.result)
                    getFigureCode()
                  },
                  // 出现错误，也是提示并重新获取验证码
                  onError: (data) => {
                    ElMessage.error('发生了一些错误，请联系管理员：' + data.result)
                    getFigureCode()
                  }
                })
          },
          // 验证码错误，提示并重新获取验证码
          onFailure: (data) => {
            ElMessage.warning(data.message) // 课堂派接口返回的信息在 message 属性中
            getFigureCode()
          },
          // 出现错误，也是提示并重新获取验证码
          onError: (data) => {
            ElMessage.error('发生了一些错误，请联系管理员：' + data.message)
            getFigureCode()
          },
          // 请求内容类型
          contentType: 'application/json;charset=UTF-8'
        })
  })
}

// 页面加载时获取一次图形验证码
getFigureCode()
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
              <div class="accountRole-box">
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
                <img ref="figureCodeImgRef" class="verify-img" style="cursor: pointer;"
                     src="@/assets/img/verify.png"
                     @click="getFigureCode()"
                     alt=""/>
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
