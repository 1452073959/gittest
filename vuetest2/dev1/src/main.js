import Vue from 'vue'
import App from './App.vue'
import HmButton from './components/HmButton'
import VueRouter from 'vue-router'
import HelloWorld from './components/HelloWorld'
import router from './router/index'
// Vue.use(VueRouter) // VueRouter插件初始化
// const router = new VueRouter({
// 	routes: [
// 	  { path: '/find', component: HelloWorld },
// 	  { path: '/my', component: App },
// 	  // { path: '/friend', component: BaseCount },
// 	]
// 	})
  // routes 路由规则们
  // route  一条路由规则 { path: 路径, component: 组件 }
Vue.config.productionTip = false

// 进行全局注册 → 在所有的组件范围内都能直接使用
// Vue.component(组件名，组件对象)
Vue.component('HmButton', HmButton)

//全局注册自定义指令
// Vue.directive('focus',{
// 	inserted(el)
// 	{
// 		el.focus()
// 		console.log('全局自定义指令测试');
// 		console.log(el);
// 	}
	
// })


new Vue({
  render: h => h(App),
  router:router
}).$mount('#app')
