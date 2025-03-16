import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from '@/views/login/LoginView.vue'
import HomeView from '@/views/home/HomeView.vue'
import ChildHomeView from '@/views/home/child/ChildHomeView.vue'
import MistakesCollectionView from '@/views/home/child/MistakesCollectionView.vue'
import AdminLoginView from '@/views/admin/login/AdminLoginView.vue'
import AdminHomeView from '@/views/admin/home/AdminHomeView.vue'
import AdminChildHomeView from '@/views/admin/home/child/AdminChildHomeView.vue'
import AdminQuestionManageView from '@/views/admin/home/child/AdminQuestionManageView.vue'
import CreateExamPaperView from '@/views/admin/home/child/CreateExamPaperView.vue'
import ExamPaperReleaseView from '@/views/admin/home/child/ExamPaperReleaseView.vue'
import AdminShowExamPaperListView from '@/views/admin/home/child/AdminShowExamPaperListView.vue'
import PendingExamPagerListView from '@/views/home/child/PendingExamPagerListView.vue'
import ExamPageView from '@/views/home/child/ExamPageView.vue'
import ExamPaperView from '@/views/home/child/ExamPaperView.vue'
import ResultPageView from '@/views/home/child/ResultPageView.vue'
import FinishExamPageListView from '@/views/home/child/FinishExamPageListView.vue'
import ExamPaperReviewPageView from '@/views/home/child/ExamPaperReviewPageView.vue'
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
      },
      {
        path: 'pendingExamPagerList',
        name: 'pendingExamPagerList',
        component: PendingExamPagerListView,
        meta: { breadcrumb: '待考试试卷列表' }
      },
      {
        path: 'examPage',
        name: 'examPage',
        component: ExamPageView,
        meta: { breadcrumb: '考试中试卷列表' }
      },
      {
        path: 'finishExamPageList',
        name: 'finishExamPageList',
        component: FinishExamPageListView,
        meta: { breadcrumb: '已结束试卷列表' }
      }
    ]
  },
  {
    path:'/examPaperPage/:examPaperAllocationId',
    name:'examPaperPage',
    component: ExamPaperView,
    meta: { breadcrumb: '考试试卷' }
  },
  {
    path: '/resultPage',
    name:'resultPage',
    component: ResultPageView,
    meta: { breadcrumb: '考试结果' }
  },
  {
    path: '/reviewPage/:examPaperAllocationId',
    name:'reviewPage',
    component: ExamPaperReviewPageView,
    meta: { breadcrumb: '试卷回顾' }
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
      },{
        path: 'adminQuestionManage',
        name: 'adminQuestionManage',
        component: AdminQuestionManageView,
        meta: { breadcrumb: '题目管理' }
      },{
        path: 'createExamPaper',
        name: 'createExamPaper',
        component: CreateExamPaperView,
        meta: { breadcrumb: '创建试卷' }
      },{
        path: 'examPaperRelease',
        name: 'examPaperRelease',
        component: ExamPaperReleaseView,
        meta: { breadcrumb: '发布试卷' }
      },{
        path: 'adminShowExamPaperList',
        name: 'adminShowExamPaperList',
        component: AdminShowExamPaperListView,
        meta: { breadcrumb: '试卷列表' }
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
