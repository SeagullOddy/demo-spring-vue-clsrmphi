// element-plus
import 'element-plus/dist/index.css'

// axios
import axios from "axios";
// 设置 axios 发送请求时的默认基础路径
axios.defaults.baseURL = 'http://localhost:8080/'

// vue-router
import router from './router'

// pinia
import {createPinia} from 'pinia'

// vue
import App from './App.vue'
import {createApp} from 'vue'

const app = createApp(App)
// 装载
app.use(createPinia())
app.use(router)
app.mount('#app')
