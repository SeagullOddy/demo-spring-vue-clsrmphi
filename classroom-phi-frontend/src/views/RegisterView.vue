<script setup>
import {reactive, ref} from 'vue'
import {ElMessage} from "element-plus";
import router from "@/router";
import {post, postOthers} from "@/net";
import {useStore} from "@/stores";

// HTML 元素的引用
const registerFormRef = ref()
const teacherRoleRef = ref()
const studentRoleRef = ref()
const figureCodeImgRef = ref()

// 是否选择了学生身份，用于控制学号输入框的显示
const chooseStudent = ref(false)

// 注册表单的数据
const registerFormData = reactive({
  emailOrTelephone: '', // 邮箱/手机号
  password: '',
  password2: '',
  roleType: 'TEACHER', // 默认为老师，注册页面打开时默认选则的是老师身份
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

// 用户切换选择的身份
const changeRoleType = (roleType) => {
  studentRoleRef.value.classList.toggle('active')
  teacherRoleRef.value.classList.toggle('active')
  chooseStudent.value = (roleType === 'STUDENT')
  registerFormData.roleType = roleType
}

// 请求图形验证码
const figureCodeData = {
  url: '',
  sessionid: ''
}
const getFigureCode = () => {
  // 使用 postOthers 请求其他网站的接口
  postOthers('https://openapiv5.ketangpai.com/', '/UserApi/getFigureCode',
      {
        // 当前时间戳
        reqtimestamp: new Date().getTime(),
      }, {
        // 用于判断请求是否成功
        onJudge: (result) => {
          // status === 1 -> true -> 请求成功
          return result.status === 1
        },
        // 请求成功后的回调函数
        onSuccess: (result) => {
          figureCodeData.url = result.data.url
          figureCodeData.sessionid = result.data.sessionid
          figureCodeImgRef.value.src = result.data.url + '&time=0'
        },
        // 请求失败后的回调函数
        onFailure: (result) => {
          // 课堂派的接口返回的信息在 message 字段中
          ElMessage.warning(result.message)
        },
        onError: (result) => {
          ElMessage.error('发生了一些错误，请联系管理员：' + result.message)
        }
      })
}

// 注册方法
const register = async () => {
  if (!registerFormRef.value) {
    return
  }
  await registerFormRef.value.validate(valid => {
    if (!valid) {
      ElMessage.error('请检查输入项')
      return
    }
    // 还需要检验验证码，使用了课堂派的接口
    postOthers('https://openapiv5.ketangpai.com/', '/UserApi/sendCode',
        {
          // 类型为注册
          type: 'reg',
          // 用户输入的验证码
          verify: registerFormData.verify,
          // 手机号，假拟一个而不要使用用户输入的手机号
          mobile: useStore().nextVirtualTelephone(),
          // 前面得到的图形验证码的 sessionid
          sessionid: figureCodeData.sessionid,
          // 二级域名，不需要
          secondDomain: '',
          // 当前时间戳
          reqtimestamp: new Date().getTime(),
        }, {
          onJudge: (result) => {
            // status === 1 -> true -> 请求成功
            return result.status === 1;
          },
          // 验证码正确，向自己的后端发送注册请求
          onSuccess: () => {
            post('/api/auth/register',
                {
                  emailOrTelephone: registerFormData.emailOrTelephone,
                  password: registerFormData.password,
                  roleType: registerFormData.roleType,
                  name: registerFormData.name,
                  school: registerFormData.school,
                  // 选择老师时，学号为 null
                  studentNo: registerFormData.studentNo
                }, {
                  onSuccess: (result) => {
                    // 自己后端接口返回的消息存在 message 属性中
                    ElMessage.success(result.message)
                    router.push('/login')
                  },
                  // 注册失败，提示并重新获取验证码
                  onFailure: (result) => {
                    ElMessage.warning(result.message)
                    getFigureCode()
                  },
                  // 请求出现错误，也是提示并重新获取验证码
                  onError: (result) => {
                    ElMessage.error('发生了一些错误，请联系管理员：' + result.message)
                    getFigureCode()
                  },
                  contentType: "application/x-www-form-urlencoded"
                })
          },
          // 输入的验证码错误，提示并重新获取验证码
          onFailure: (result) => {
            ElMessage.warning(result.message) // 课堂派接口返回的信息在 message 属性中
            getFigureCode()
          },
          // 请求出现错误，也是提示并重新获取验证码
          onError: (result) => {
            ElMessage.error('发生了一些错误，请联系管理员：' + result.message)
            getFigureCode()
          }
        })
  })
}

// 页面加载时获取一次图形验证码
getFigureCode()
</script>

<template>
  <!-- 使用 ZoomViewStyle 适配不同分辨率 -->
  <div class="view-login view-regist" :style="useStore().getZoomViewStyle()">
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
              <el-input v-model="registerFormData.password" type="password" show-password
                        placeholder="请输入密码"/>
            </el-form-item>
            <el-form-item class="margin-bottom" prop="password2">
              <el-input v-model="registerFormData.password2" type="password" show-password
                        placeholder="请再次输入密码确认"/>
            </el-form-item>
            <el-form-item class="margin-bottom">
              <p class="font-bold font16">选择身份</p>
              <div class="role-box">
                <div ref="teacherRoleRef"
                     @click="changeRoleType('TEACHER')"
                     class="item flex-align active">
                  <img src="@/assets/img/teacher.svg" class="icon" alt="">
                  <span class="name">老师</span>
                </div>
                <div ref="studentRoleRef"
                     @click="changeRoleType('STUDENT')"
                     class="item flex-align">
                  <img src="@/assets/img/student.svg" class="icon" alt="">
                  <span class="name">学生</span>
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
              <el-button @click="register()" type="primary" size="large"
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
<style src="@/assets/css/register.css" scoped/>

