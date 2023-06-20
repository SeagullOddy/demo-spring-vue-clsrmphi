import {createRouter, createWebHistory} from 'vue-router'
import {useAuthStore} from "@/stores";

/**
 * vue 路由
 */
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL), routes: [{
    path: '/login',
    name: 'authorize-login',
    component: () => import('../views/LoginView.vue')
  }, {
    path: '/register',
    name: 'authorize-register',
    component: () => import('../views/RegisterView.vue')
  }, {
    path: '/main',
    name: 'main',
    component: () => import('../views/MainView.vue')
  }, {
    path: '/404',
    name: 'error-404',
    component: () => import('../views/404View.vue')
  }]
})

/**
 * vue 路由守卫
 */
router.beforeEach((to, from, next) => {
  const account = useAuthStore().getAccount()
  // 如果 store 中账户为空，表示用户没有登录账户，此时访问的不是不存在的或认证相关的页面，那么跳转到登录页面
  if (account == null && (to.matched.length === 0 || !to.name.startsWith(
      'authorize'))) {
    next('/login')
  }
  // 如果用户已经登陆账户，访问的是不存在的或认证相关的页面，那么跳转到主页
  else if (account != null && (to.matched.length === 0 || to.name.startsWith(
      'authorize'))) {
    next('/main')
  }
  // 其他情况，直接放行跳转
  else {
    next()
  }
})

export default router
