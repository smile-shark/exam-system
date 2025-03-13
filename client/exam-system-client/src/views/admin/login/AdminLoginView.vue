<template>
    <div>
        <el-form :rules="rules" ref="loginForm" :model="loginForm" class="loginContainer">
            <h3 class="loginTitle">
            <!-- 管理系统登录 -->
            &nbsp;
            </h3>
            <el-form-item prop="username">
                <el-input type="text" v-model="loginForm.username" placeholder="请输入账号" >
                </el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input
                :type="showPassword?'text':'password'"
                v-model="loginForm.password"
                placeholder="请输入密码"
                >
                <template #suffix>
                    <div style="display: flex;align-items: center;justify-content: center;height: 100%;" @click="showPassword=!showPassword">
                        <el-icon class="el-icon-view" />
                    </div>
                </template>
                </el-input>
            </el-form-item>
            <el-form-item>
            </el-form-item>
            <el-button type="primary" style="width:100%" @click="submitLogin">登录</el-button>
        </el-form>
    </div>
</template>

<script>
import api from "@/axios/index"
export default {
  name: "Login",
    data(){
      return{
        showPassword:false,
          loginForm:{
              username:"",
              password:"",
          },
          checked: true,
          rules:{
              username:[{required:true,message:"请输入用户名",trigger:"blur"},{ min: 2, max: 40, message: '长度在 2 到 40 个字符', trigger: 'blur' }
              ],
              password:[{required:true,message:"请输入密码",trigger:"blur"},,{ min: 6,  message: '密码长度要大于6', trigger: 'blur' }],
              code:[{required:true,message:"请输入验证码",trigger:"blur"}],
          }

      }
  },
    methods:{
      submitLogin(){
          // 登录
          // 账号不为空
          if(this.loginForm.username.length==0){
            this.$message.error("账号不能为空")
            retrun
          }
          // 密码不为空
          if(this.loginForm.password.length==0){
            this.$message.error("密码不能为空")
            retrun
          }
          // 密码长度大于6
          if(this.loginForm.password.length<6){
            this.$message.error("密码长度要大于6")
            retrun
          }
          api.adminLogin(this.loginForm.username,this.loginForm.password).then(res=>{
            if(res.msg=="登录成功"){
                localStorage.adminInfo=JSON.stringify(res.data)
                setTimeout(()=>{
                    this.$router.push("/admin/home")
                },1000)
            }
          })
      }
    }
};
</script>

<style scoped>
    .loginContainer{
        border-radius: 15px;
        background-clip: padding-box;
        margin: 180px auto;
        width: 350px;
        padding: 15px 35px 15px 35px;
        /* background: aliceblue; */
        background: url(@/assets/logo.png) no-repeat;
        background-position-y:-130%;
        border:1px solid rgb(17, 31, 189);
        box-shadow: 0 0 25px #4a96dc;
    }
    .loginTitle{
        margin: 0px auto 48px auto;
        text-align: center;
        font-size: 40px;
    }
    .loginRemember{
        text-align: left;
        margin: 0px 0px 15px 0px;
    }
    body{
        /* background-image: url("../assets/background.jpg") ; */
        background-size:100%;
    }
</style>

