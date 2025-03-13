<template>
	<div class="center">
		<h1>考试系统</h1>
		<div class="logon">
			<div :class="overlaylong">
				<div class="overlaylong-Signin" v-if="disfiex == 0">
					<h2 class="overlaylongH2">登 录</h2>
					<input v-model="loginAcount" type="text" placeholder="账号">
					<input v-model="loginPassword" type="password" placeholder="密码">
					<h3><el-link>忘记密码？</el-link><el-link @click="routerTo('/admin/login')">管理员登录</el-link></h3>
					<button class="inupbutton" @click.prevent="studentLogin">登 录</button>
				</div>
				<div class="overlaylong-Signup" v-if="disfiex == 1">
					<h2 class="overlaylongH2">注册账户</h2>
					<input v-model="signupName" type="text" placeholder="昵称">
					<input v-model="signupAccount" type="text" placeholder="账号">
					<input v-model="signupPassword" type="password" placeholder="密码">
					<input v-model="signupRePassword" type="password" placeholder="重复密码">
					<template>
						<el-select v-model="adminId" filterable placeholder="请选择教师"
						style="width: 300px;">
							<el-option
							v-for="(item,index) in options"
							:key="index"
							:label="item.administratorName"
							:value="item.administratorId">
							</el-option>
						</el-select>
					</template>
					<button class="inupbutton" @click.prevent="studentSignUp">注 册</button>
				</div>
 
			</div>
			<div :class="overlaytitle">
				<div class="overlaytitle-Signin" v-if="disfiex == 0">
					<h2 class="overlaytitleH2">你好，朋友！</h2>
					<p class="overlaytitleP">
						输入你的名字，让我们快乐的开始考试吧
					</p>
					<div class="buttongohs" @click="Signin">注 册</div>
				</div>
				<div class="overlaytitle-Signup" v-if="disfiex == 1">
					<h2 class="overlaytitleH2">欢迎回来！</h2>
					<p class="overlaytitleP">为了与我们保持联系，请使用您的个人信息登录</p>
					<div class="buttongohs" @click="Signup">登 录</div>
				</div>
			</div>
		</div>
 
	</div>
</template>
 
<script>
import api from '@/axios/index'
	export default {
		data() {
			return {
				overlaylong: 'overlaylong',
				overlaytitle: 'overlaytitle',
				disfiex: 0,
				options:[],
				adminId: null,
				loginAcount:'',
				loginPassword:'',
				signupName:'',
				signupAccount:'',
				signupPassword:'',
				signupRePassword:''
			}
		},
		methods: {
			Signin() {
				this.overlaylong = "overlaylongleft"
				this.overlaytitle = "overlaytitleright"
				setTimeout(() => {
					this.disfiex = 1
				}, 200)
			},
			Signup() {
				this.overlaylong = "overlaylongright"
				this.overlaytitle = "overlaytitleleft"
 
				setTimeout(() => {
					this.disfiex = 0
				}, 200)
			},
			studentSignUp(){
				// 账号最少6位
				if(this.signupAccount.length<6){
					this.$message.error('账号长度最少6位')
					return
				}
				// 密码最少6位
				if(this.signupPassword.length<6){
					this.$message.error('密码长度最少6位')
					return
				}
				// 昵称最少1位
				if(this.signupName.length<1){
					this.$message.error('昵称长度最少1位')
					return
				}
				// 账号和密码长度最多40，昵称最多20
				if(this.signupAccount.length>40 || this.signupPassword.length>40 || this.signupName.length>20){
					this.$message.error('账号或密码或昵称长度超过限制')
					return	
				}
				// 密码和重复密码不一致
				if(this.signupPassword!= this.signupRePassword){
					this.$message.error('两次密码输入不一致')
					return
				}
				// 如果没有选择admin
				if(!this.adminId){
					this.$message.error('请选择教师')
					return
				}
				api.studentSignUp(
					this.signupAccount,
					this.signupPassword,
					this.signupName,
					this.adminId
				).then(res=>{
					if(res.msg=='注册成功'){
						this.Signup()
						this.signupAccount=''
						this.signupPassword=''
						this.signupRePassword=''
						this.signupName=''
						this.adminId=null
					}
				})
			},
			studentLogin(){
				// 账号密码不能为空
				if(this.loginAcount.length==0 || this.loginPassword.length==0){
					this.$message.error('账号或密码不能为空')
					return
				}
				// 账号密码最少6位
				if(this.loginAcount.length<6 || this.loginPassword.length<6){
					this.$message.error('账号或密码长度最少6位')
					return
				}
				api.studentLogin(this.loginAcount,this.loginPassword).then(res=>{
					if(res.msg=='登录成功'){
						localStorage.setItem('studentInfo',JSON.stringify(res.data))
						setTimeout(() => {
							this.$router.push('/home')
						}, 1000);
					}
				})
			},
			routerTo(path){
				this.$router.push(path)
			}
		},
		mounted(){
			api.getAdministratorNameList().then(res=>{
				if(res.success){
					this.options=res.data
				}
			})
		}
	}
</script>
<style>
	.center {
		width: 100vw;
		height: 100vh;
		/* background-image: url('https://gd-hbimg.huaban.com/9165dc3c0f8279ae5402d1f47212847bff68b49f3cb547-QEli8R'); */
		background-size: 100% 100%;
		background-repeat: no-repeat;
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}
 
	h1 {
		font-size: 30px;
		color: black;
	}
 
	.logon {
		background-color: #fff;
		border-radius: 10px;
		box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
		/* position: relative;
		overflow: hidden; */
		width: 768px;
		max-width: 100%;
		min-height: 480px;
		margin-top: 20px;
		display: flex;
		background: -webkit-linear-gradient(right, #4284db, #29eac4);
	}
 
	.overlaylong {
		border-radius: 10px 0 0 10px;
		width: 50%;
		height: 100%;
		background-color: #fff;
		display: flex;
		align-items: center;
		justify-content: center;
	}
 
	.overlaylongleft {
		border-radius: 0px 10px 10px 0px;
		width: 50%;
		height: 100%;
		background-color: #fff;
		transform: translateX(100%);
		transition: transform 0.6s ease-in-out;
		display: flex;
		align-items: center;
		justify-content: center;
	}
 
	.overlaylongright {
		border-radius: 10px 0 0 10px;
		width: 50%;
		height: 100%;
		background-color: #fff;
		transform: translateX(0%);
		transition: transform 0.6s ease-in-out;
		display: flex;
		align-items: center;
		justify-content: center;
	}
 
	.overlaytitle {
		border-radius: 0px 10px 10px 0px;
		width: 50%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0);
		display: flex;
		align-items: center;
		justify-content: center;
	}
 
 
	.overlaytitleH2 {
		font-size: 30px;
		color: #fff;
		margin-top: 20px;
	}
 
	.overlaytitleP {
		font-size: 15px;
		color: #fff;
		margin-top: 20px;
	}
 
	.overlaytitleleft {
		border-radius: 0px 10px 10px 0px;
		width: 50%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0);
		display: flex;
		align-items: center;
		justify-content: center;
		transform: translateX(0%);
		transition: transform 0.6s ease-in-out;
	}
 
	.overlaytitleright {
		border-radius: 0px 10px 10px 0px;
		width: 50%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0);
		display: flex;
		align-items: center;
		justify-content: center;
		transform: translateX(-100%);
		transition: transform 0.6s ease-in-out;
	}
 
	.overlaytitle-Signin {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}
 
	.overlaytitle-Signup {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}
 
	.buttongohs {
		width: 180px;
		height: 40px;
		border-radius: 50px;
		border: 1px solid #fff;
		color: #fff;
		font-size: 15px;
		text-align: center;
		line-height: 40px;
		margin-top: 40px;
	}
 
	.overlaylongH2 {
		font-size: 25px;
		color: black;
		/* width: 250px; */
	}
 
	.overlaylong-Signin {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}
 
	.overlaylong-Signup {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}
 
	input {
		background-color: #eee;
		border: none;
		padding: 12px 15px;
		margin: 10px 0;
		width: 270px;
	}
	h3{
		font-size: 10px;
		margin-top: 10px;
		cursor: pointer;
	}
	.inupbutton{
		background-color: #29eac4;
		border: none;
		width: 180px;
		height: 40px;
		border-radius: 50px;
		font-size: 15px;
		color: #fff;	
		text-align: center;
		line-height: 40px;
		margin-top: 30px;
	}
</style>
 