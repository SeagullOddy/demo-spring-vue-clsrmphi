import {createRouter, createWebHistory} from 'vue-router'
import {useStore} from "@/stores";
import {get} from "@/net";
import {ElMessage} from "element-plus";

// vue 路由配置
const router = createRouter({
  history: createWebHistory(), routes: [{
    // 认证相关
    path: '/login',
    name: 'authorize-login',
    component: () => import('../views/LoginView.vue')
  }, {
    path: '/register',
    name: 'authorize-register',
    component: () => import('../views/RegisterView.vue')
  }, {
    // 主页相关
    path: '/main',
    name: 'main',
    component: () => import('../views/MainView.vue'),
    children: [{
      path: '/main',
      name: 'main-home',
      component: () => import('../views/HomeBoxView.vue')
    }]
  }, {
    // 404
    path: '/404',
    name: 'error-404',
    component: () => import('../views/404View.vue')
  }]
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const store = useStore()
  // 1.如果是第一次加载网页（包括刷新页面、更改地址栏网址访问页面），那么需要先获取当前登录的账户信息
  if (store.isInitApp()) {
    store.setInitApp(false)
    // 向后端获取一次当前用户的登录信息，根据结果进行跳转
    get('/api/account/me', {
      // 获取成功，将信息存入 store 中，设置 initApp 为 false，最后判断放行
      onSuccess: (result) => {
        ElMessage.success('欢迎回来，' + result.data.name)
        store.setAccount(result.data)
        console.log('第一次路由：\nfrom：', from.path, '\nto：', to.path,
            '\naccount：', store.getAccount())
        if (to.matched.length === 0) {
          next('/main')
        } else {
          next()
        }
      },
      // 未登录，跳转到登录页面
      onFailure: () => {
        ElMessage.warning('您还未登录，请先登录')
        console.log('第一次路由：\nfrom：', from.path, '\nto：', to.path,
            '\naccount：', store.getAccount())
        next('/login')
      },
      // 请求出错，跳转到登录页面
      onError: (result) => {
        ElMessage.error('获取用户信息失败，请尝试重新登录：' + result.message)
        console.log('第一次路由：\nfrom：', from.path, '\nto：', to.path,
            '\naccount：', store.getAccount())
        next('/login')
      }
    })
  }
  // 2. 如果不是第一次加载网页，那么根据 store 中的账户信息进行跳转
  else {
    console.log('路由守卫：\nfrom：', from.path, '\nto：', to.path, '\naccount：',
        store.getAccount())
    // 如果 store 中账户为空，表示用户没有登录账户，此时访问的是不存在的或不是认证相关的页面，那么跳转到登录页面
    if (store.getAccount() == null && (to.matched.length === 0
        || !to.name.startsWith('authorize'))) {
      next('/login')
    }
    // 如果用户已经登陆账户，访问的是不存在的或认证相关的页面，那么跳转到主页
    else if (store.getAccount() != null && (to.matched.length === 0
        || to.name.startsWith('authorize'))) {
      next('/main')
    }
    // 其他情况，直接放行
    else {
      next()
    }
  }
})

export default router
