import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from '@/views/login/LoginView.vue'
import HomeView from '@/views/home/HomeView.vue'
import ChildHomeView from '@/views/home/child/ChildHomeView.vue'
import MistakesCollectionView from '@/views/home/child/MistakesCollectionView.vue'
import AdminLoginView from '@/views/admin/login/AdminLoginView.vue'
import AdminHomeView from '@/views/admin/home/AdminHomeView.vue'
import AdminChildHomeView from '@/views/admin/home/child/AdminChildHomeView.vue'
import path from '@/axios/path'


Vue.use(VueRouter)
const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView,
    meta: { breadcrumb: '学生登录' }
  },
  {
    path: '/home',
    name: 'home',
    component: HomeView,
    meta: { breadcrumb: '学生首页' },
    children: [
      {
        path: '/',
        redirect: 'childHome'
      },
      {
        path: 'childHome',
        name: 'childHome',
        component: ChildHomeView,
        meta: { breadcrumb: '学生子首页' }
      },
      {
        path: 'mistakesCollection',
        name: 'mistakesCollection',
        component: MistakesCollectionView,
        meta: { breadcrumb: '错题集' }
      }
    ]
  },
  {
    path: '/admin',
    redirect: '/admin/login',
    meta: { breadcrumb: '管理员登录' }
  },
  {
    path: '/admin/login',
    name: 'adminLogin',
    component: AdminLoginView,
    meta: { breadcrumb: '管理员登录' }
  },
  {
    path: '/admin/home',
    name: 'adminHome',
    component: AdminHomeView,
    meta: { breadcrumb: '' },
    children: [
      {
        path: '/',
        redirect: 'childHome'
      },
      {
        path: 'childHome',
        name: 'adminChildHome',
        component: AdminChildHomeView,
        meta: { breadcrumb: '' }
      }
    ]
  }
];
const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes
})
router.onError((error)=>{

  if(error.name!=='NavigationDuplicated'){
    console.error(error)
  }
})

export default router
