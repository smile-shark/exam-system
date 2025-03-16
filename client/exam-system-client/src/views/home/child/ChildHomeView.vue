<template>
    <div>
        <div class="exam-status">
            <div class="status-card" @click="routerPush('pendingExamPagerList')">
              <el-badge :value="examPaper0Count" :max="10" :hidden="examPaper0Count === 0">
                <div style="width:70px">待考试</div>
              </el-badge>
            </div>
            <div class="status-card" @click="routerPush('examPage')">
              <el-badge :value="examPaper1Count" :max="10" :hidden="examPaper1Count === 0">
                <div style="width:70px">考试中</div>
              </el-badge>
            </div>
            <div class="status-card" @click="routerPush('finishExamPageList')">
                <div style="width:120px">已结束考试</div>
            </div>
        </div>
        <div class="stats-container">
        <div class="stats-item">答题数<br><span class="value">{{ answerQuesitonCount }}</span></div>
        <div class="stats-item">错题数<br><span class="value">{{ wrongQuestionCount }}</span></div>
        <div class="stats-item">最近得分<br><span class="value">{{ lastScore }}</span></div>
        <div class="stats-item">平均分<br><span class="value">{{ averageScore }}</span></div>
        <div class="stats-item">考试次数<br><span class="value">{{ examCount }}</span></div>
        </div>
    </div>
</template>

<script>
import api from "@/axios/index"
export default {
    data(){
      return{
        examPaper0Count:0,
        examPaper1Count:0,
        answerQuesitonCount:0,
        wrongQuestionCount:0,
        lastScore:0,
        averageScore:0,
        examCount:0
      }
    },
    methods:{
      routerPush(path){
        this.$router.push(path)
      }
    },
    mounted(){
      if(localStorage.studentInfo){
        let studentId = JSON.parse(localStorage.studentInfo).studentId
        api.getExamPaperCountByState(studentId,0).then(res=>{
            this.examPaper0Count = res.data
        })
        api.getExamPaperCountByState(studentId,1).then(res=>{
            this.examPaper1Count = res.data
        })
        api.getAnswerQuestionCountByStudentId(studentId).then(res=>{
            this.answerQuesitonCount = res.data
        })
        api.getWoringQuestionCountByStudentId(studentId).then(res=>{
            this.wrongQuestionCount = res.data
        })
        api.getLastExamScoreByStudentId(studentId).then(res=>{
          this.lastScore=res.data
        })
        api.getAvgScoreByStudentId(studentId).then(res=>{
          this.averageScore=res.data
        })
        api.getExamCountByStudentId(studentId).then(res=>{
          this.examCount=res.data
        })
      }else{
        this.$message.error('请先登录')
        this.$router.push('/login')
      }
    }
}
</script>

<style scoped>
/* 基本重置 */
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  font-family: Arial, sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f0f0f0;
}

/* 卡片样式 */
.status-card, .stats-card {
  border: none;
  padding: 20px;
  margin: 5px;
  text-align: center;
  color: #fff; /* 文字颜色为白色 */
  transition: transform 0.3s, box-shadow 0.3s;
  /* 使卡片占据剩余空间 */
  flex-grow: 1;
}

/* 鼠标悬停效果 */
.status-card:hover, .stats-card:hover {
  transform: scale(1.05); /* 放大 */
  box-shadow: 0 10px 20px rgba(0,0,0,0.2); /* 阴影 */
  cursor: pointer; /* 鼠标手型 */
}

/* 考试状态布局 */
.exam-status {
  display: flex;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 20px;
}

/* 为每个卡片指定不同的背景颜色 */
.status-card:nth-child(1) { background-color: #3498db; }
.status-card:nth-child(2) { background-color: #2ecc71; }
.status-card:nth-child(3) { background-color:#0099cc; }

.stats-item:nth-child(1){background-color:#ea740b ;}
.stats-item:nth-child(2){background-color:#fe4444 ;}
.stats-item:nth-child(3){background-color:#6413b8 ;}
.stats-item:nth-child(4){background-color:#12b39a ;}


/* 统计信息容器 */
.stats-container {
  display: flex;
  justify-content: space-between;
  width: 100%;
  padding: 10px;
  /* border: 2px solid #ccc; */
}

/* 统计信息项 */
.stats-item {
  flex: 1; /* 平均分配空间 */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #fff; /* 文字颜色 */
  background-color: #3498db; /* 背景颜色 */
  margin:5%;
  padding: 20px;
  border-radius: 5px; /* 圆角 */
  transition: transform 0.3s, box-shadow 0.3s;
}

/* 统计信息项鼠标悬停效果 */
.stats-item:hover {
  transform: scale(1.04); /* 放大 */
  box-shadow: 0 10px 20px rgba(0,0,0,0.2); /* 阴影 */
}

/* 统计信息项的值 */
.value {
  font-size: 1.5em;
  font-weight: bold;
}

/* 响应式设计 */
@media (max-width: 600px) {
  .stats-container {
    flex-direction: column;
    align-items: center;
  }

  .stats-item {
    width: calc(100% - 10px); /* 占满整行 */
    margin-bottom: 10px;
  }
}

</style>