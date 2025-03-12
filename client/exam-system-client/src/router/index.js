import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from '@/views/login/LoginView.vue'
import HomeView from '@/views/home/HomeView.vue'
import ChildHomeView from '@/views/home/child/ChildHomeView.vue'
import MistakesCollectionView from '@/views/home/child/MistakesCollectionView.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path:'/home',
    name: 'home',
    component: HomeView,
    // 子路由
    children: [
      {
        path: '/',
        redirect: '/home/childHome'
      },
      {
        path: '/home/childHome',
        name: 'childHome',
        component: ChildHomeView
      },
      {
        path: '/home/mistakesCollection',
        name:'mistakesCollection',
        component: MistakesCollectionView
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes
})

export default router
