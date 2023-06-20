import {createRouter, createWebHistory} from 'vue-router'
import {useStore} from "@/stores";

// vue 路由配置
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL), routes: [
    // 认证相关
    {
      path: '/login',
      name: 'authorize-login',
      component: () => import('../views/LoginView.vue')
    }, {
      path: '/register',
      name: 'authorize-register',
      component: () => import('../views/RegisterView.vue')
    },
    // 主页相关
    {
      path: '/main',
      name: 'main',
      component: () => import('../views/MainView.vue'),
      children: [{
        path: '/main',
        name: 'main-home',
        component: () => import('../views/HomeBoxView.vue')
      }]
    },
    // 404
    {
      path: '/404',
      name: 'error-404',
      component: () => import('../views/404View.vue')
    }]
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const store = useStore()
  // 如果不是第一次加载网页，那么需要先获取当前登录的账户信息
  if (!store.isInitApp()) {
    console.log('路由守卫：\n', from.path, '\n', to.path, '\n',
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
  /* 如果是第一次加载网页（包括刷新页面、更改地址栏网址访问页面），那么直接放行。
  由于第一次加载网页时，store 中的账户信息为空，而 App.vue 中添加了第一次加载页面时
  获取账户信息并跳转的方法，不这么做就会导致重复且无效的跳转 */
  else {
    console.log('第一次路由：\n', from.path, '\n', to.path, '\n',
        store.getAccount())
    useStore().setInitApp(false)
    next()
  }
})

export default router
