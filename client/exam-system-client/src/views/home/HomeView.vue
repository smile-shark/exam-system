<template>
 <div>
  <el-row>
    <el-col :span="2" :xs="0"><h1></h1></el-col>
    <el-col :span="5" :xs="12" style="height: 20vh;">
        <img src="@/assets/logo.png" style="height: 100%;"/>
    </el-col>
    <el-col :span="15" :xs="7"><h1></h1></el-col>
    <el-col :span="1" :xs="3">
      <div style="line-height: 20vh;height: 20vh;color:#37b5f8">{{ studentName }}</div>
    </el-col>
    <el-col :span="1" :xs="2">
      <div style="line-height: 20vh;height: 20vh;">
        <el-link @click="routerPush('/login')">
          退出
        </el-link>
      </div>
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="24">
      <el-menu
        :default-active="activeIndex2"
        class="el-menu-demo"
        mode="horizontal"
        background-color="#37b5f8"
        text-color="#fff"
        active-text-color="#004d99">
        <el-menu-item index="1" @click="routerPush('/home/childHome')">首 页</el-menu-item>
        <el-menu-item index="2" @click="routerPush('/home/mistakesCollection')">错 题 集</el-menu-item>
        <el-menu-item index="3">收 藏</el-menu-item>
        <el-menu-item index="4">设 置</el-menu-item>
      </el-menu>
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="24">
      <router-view/>
    </el-col>
  </el-row>
 </div>
</template>
<script>
import router from '@/router';

export default {
  data(){
    return{
      activeIndex2: '1',
      studentName:''
    }
  },
  methods: {
    routerPush(path){
      this.$router.push(path)
    }
  },
  mounted(){
    // 获取学生姓名
    if(localStorage.studentInfo)this.studentName=JSON.parse(localStorage.studentInfo).studentName
  }
}
</script>
<style>
.a{
  color: #004d99
}
</style>