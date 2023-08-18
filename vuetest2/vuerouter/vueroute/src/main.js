import Vue from 'vue'
import App from './App.vue'
// import Find from './views/Find'
// import My from './views/My'
// import Friend from './views/Friend'
// import VueRouter from 'vue-router'
// Vue.use(VueRouter) // VueRouter插件初始化

// const router = new VueRouter({
//   // routes 路由规则们
//   // route  一条路由规则 { path: 路径, component: 组件 }
//   routes: [
//     { path: '/find', component: Find },
//     { path: '/my', component: My },
//     { path: '/friend', component: Friend },
//   ]
// })

import router from './router';
Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router
}).$mount('#app')
