<template>
	<div class="manage_page">
		<el-row style="height: 100%;">
	  		<el-col :span="4"  
			style="min-height: 100%; background-color: #324057;">
				<el-menu :default-active="defaultActive" 
				style="min-height: 100%;" router       
				background-color="#324057"
      			text-color="#fff">
					<el-menu-item index="/admin/home">
						<i class="el-icon-menu"></i>首页
					</el-menu-item>
					<el-submenu index="2">
						<template slot="title"><i class="el-icon-s-data"></i>数据管理</template>
						<el-menu-item index="adminQuestionManage">题目管理</el-menu-item>
						<el-menu-item>题目导入</el-menu-item>
						<el-menu-item>课程管理</el-menu-item>
					</el-submenu>
					<el-submenu index="3">
						<template slot="title"><i class="el-icon-user"></i>学生管理</template>
						<el-menu-item>学生账号信息</el-menu-item>
						<el-menu-item>学生成绩</el-menu-item>
						<el-menu-item>学生成绩统计</el-menu-item>
					</el-submenu>
					<el-submenu index="4">
						<template slot="title"><i class="el-icon-tickets"></i>试卷管理</template>
						<el-menu-item index="adminShowExamPaperList">试卷列表</el-menu-item>
						<el-menu-item index="examPaperRelease">试卷发布</el-menu-item>
						<el-menu-item index="createExamPaper">创建试卷</el-menu-item>
					</el-submenu>
				</el-menu>
			</el-col>
			<el-col :span="20" style="height: 100%;overflow: auto;">
				<el-row>
					<el-col :span="24" style="height: 8vh;display: flex;align-items: center;padding:20px;background-color: #eff2f7;position: relative;">
						<el-breadcrumb separator-class="el-icon-arrow-right">
							<el-breadcrumb-item :to="{ path: '/admin/home' }">首页</el-breadcrumb-item>
							<el-breadcrumb-item v-for="(item, index) in breadcrumbList" :key="index" :to="item.to">
								{{ item.name }}
							</el-breadcrumb-item>
						</el-breadcrumb>
						<div style="display: inline-block;position: absolute;right: 20px;">{{ adminName }}</div>
					</el-col>
				</el-row>
				<keep-alive>
				    <router-view></router-view>
				</keep-alive>
			</el-col>
		</el-row>
  	</div>
</template>

<script>
import api from '@/axios/index'
    export default {
		data(){
			return{
				defaultActive: '/admin/home',
				breadcrumbList: [],
				adminName:''
			}
		},
		watch: {
			"$route"(newRoute) {
			this.updateBreadcrumb(newRoute);
			}
		},
		created() {
			this.updateBreadcrumb(this.$route);
		},
		methods: {
			updateBreadcrumb(route) {
			this.breadcrumbList = route.matched
				.filter(record => record.meta && record.meta.breadcrumb)
				.map(record => ({
				name: record.meta.breadcrumb,
				to: record.path
				}));
			}
		},
		mounted(){
			if(localStorage.adminInfo){
				this.adminName=JSON.parse(localStorage.adminInfo).administratorName
			}
		}
    }
</script>
<style scoped>
.manage_page{
	height: 100vh;
}
</style>

