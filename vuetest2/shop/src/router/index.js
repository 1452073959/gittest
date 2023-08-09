import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/login/index'
import Layout from '@/views/layout'
import Search from '@/views/search'
import ProDetail from '@/views/prodetail'
import SearchList from '@/views/search/list'
import Pay from '@/views/pay'
import MyOrder from '@/views/myorder'
// import HomeView from '../views/HomeView.vue'

Vue.use(VueRouter)

const routes = [
  // {
  //   path: '/',
  //   name: 'home',
  //   component: HomeView
  // },
  // {
  //   path: '/',
  //   component: Login
  // },
  // {
  //   path: '/',
  //   component: Layout
  // },
  // {
  //   path: '/about',
  //   name: 'about',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  // }

  {
    path: '/login', component: Login
  },
  {
    path: '/', component: Layout
  },
  { path: '/search', component: Search },
  { path: '/searchlist', component: SearchList },
  // 动态路由传参，确认将来是哪个商品，路由参数中携带 id
  { path: '/prodetail/:id', component: ProDetail },
  { path: '/pay', component: Pay },
  { path: '/myorder', component: MyOrder }
]

const router = new VueRouter({
  routes
})

export default router
