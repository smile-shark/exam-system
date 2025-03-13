<template>
    <div>
        <el-row>
            <el-col :span="24">
                <div class="stats-container">
                <div class="stats-item">学生数量<br><span class="value">{{ studentCount }}</span></div>
                <div class="stats-item">课程数量<br><span class="value">{{ courseCount }}</span></div>
                <div class="stats-item">题目数量<br><span class="value">{{ questionCount }}</span></div>
                <div class="stats-item">试卷数量<br><span class="value">{{ paperCount }}</span></div>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import api from '@/axios/index'
export default {
    data(){
        return{
            studentCount:0,
            courseCount:0,
            questionCount:0,
            paperCount:0
        }
    },
    mounted(){
      api.getStudentCount().then(res => {
        this.studentCount = res.data
      })
      api.getCourseCount().then(res => {
        this.courseCount = res.data
      })
        api.getQuestionCount().then(res => {
        this.questionCount = res.data
      })
      api.getExamPaperCount().then(res => {
        this.paperCount = res.data
      })
    }
}
</script>

<style scoped>

.stats-item:nth-child(1){background-color:#ea740b ;}
.stats-item:nth-child(2){background-color:#fe4444 ;}
.stats-item:nth-child(3){background-color:#6413b8 ;}
.stats-item:nth-child(4){background-color:#12b39a ;}


/* 统计信息容器 */
.stats-container {
  display: flex;
  justify-content: space-between;
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
  margin:1%;
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