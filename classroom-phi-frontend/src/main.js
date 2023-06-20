import 'element-plus/dist/index.css'

import axios from "axios";
import router from './router'
import App from './App.vue'

import {createApp} from 'vue'
import {createPinia} from 'pinia'

const app = createApp(App)

// 装载 pinia 和 vue-router
app.use(createPinia())
app.use(router)

// 设置 axios 发送请求时的默认基础路径
axios.defaults.baseURL = 'http://localhost:8080/'

app.mount('#app')
