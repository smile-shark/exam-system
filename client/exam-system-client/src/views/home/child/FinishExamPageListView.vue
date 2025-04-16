<template>
    <div>
        <el-row>
            <el-col :span="24" class="title">
                <i class="el-icon-back" @click="routerPush('childHome')"></i>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24" style="padding-left:1%;padding-right: 1%;">
                <h1 style="text-align: center;">已结束卷列表</h1>
                <el-empty description="没有考试" v-if="examPaperList.length==0"></el-empty>
                <el-card shadow="hover" v-for="(item,index) in examPaperList" :key="index" class="my-card-style">
                    <el-row>
                        <el-col :span="12">
                            <div style="font-size: 30px;font-weight: bold;">{{  item.examPaperRelease.examPaper.examPaperTitle }}
                                <span v-if="item.examPaperAllocationState==2"><span style="color:green;font-size: 1.2rem;">（已完成）</span></span>
                                <span v-if="item.examPaperAllocationState==3"><span style="color:red;font-size: 1.2rem;">（缺考）</span></span>
                            </div>
                            <div>开始时间：{{ timestampToString(item.examPaperRelease.examStartTime) }}</div>
                            <div>结束时间：{{ timestampToString(item.examPaperRelease.examEndTime) }}</div>
                            <div v-if="item.score">完成时间：{{ timestampToString(item.score.finishTime) }}</div>
                            <div>考试时长：{{ formatTimestampUTC(item.examPaperRelease.examEndTime-item.examPaperRelease.examStartTime) }}</div>
                            <div>
                                满分：{{ item.examPaperRelease.examPaper.totalScore }} 分
                            </div>
                            <div v-if="item.score">得分：{{ item.score.score }} 分</div>
                            <div v-else>得分：0 分</div>
                            <div>题目数量：{{ item.examPaperRelease.examPaper.questionCount }} 道</div>
                            <div v-if="item.examPaperAllocationState==0">考试状态：未开始</div>
                            <div v-if="item.examPaperAllocationState==1">考试状态：考试中</div>
                            <div v-if="item.examPaperAllocationState==2">考试状态：<span style="color:green">已完成</span></div>
                            <div v-if="item.examPaperAllocationState==3">考试状态：<span style="color:red">缺考</span></div>
                            <div>备注：{{ item.examPaperRelease.notes }}</div>
                        </el-col>
                        <el-col :span="12" style="display: flex;justify-content: flex-end;align-items: center;
                        padding:7%">
                            <el-button type="primary" @click="routerToExam(item.examPaperAllocationId)" 
                            :disabled="item.examPaperAllocationState!=2">
                                试卷回顾
                            </el-button>
                        </el-col>
                    </el-row>
                </el-card>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24" style="display: flex;justify-content: center;align-items: center;padding-top: 20px;">
                <el-pagination
                background
                layout="prev, pager, next"
                :total="total"
                :page-size="size"
                :hide-on-single-page="true"
                @current-change="handleCurrentChange">
                </el-pagination>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24" style="text-align: center;padding-top: 20px;"></el-col>
        </el-row>
    </div>
</template>
<script>
import api from "@/axios/index"
export default {
    data(){
        return{
            total:0,      
            examPaperList:[],
            page:1,
            size:2
        }
    },
    methods: {
        routerPush(path){
            this.$router.push(path)
        },
        timestampToString(timestamp) {
            const date = new Date(timestamp);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份从0开始计数
            const day = String(date.getDate()).padStart(2, '0');
            const hours = String(date.getHours()).padStart(2, '0');
            const minutes = String(date.getMinutes()).padStart(2, '0');
            const seconds = String(date.getSeconds()).padStart(2, '0');
            return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        },
        formatTimestampUTC(timestamp) {
            const totalSeconds = Math.floor(timestamp / 1000); // 将毫秒转换为秒
            const hours = Math.floor(totalSeconds / 3600);    // 计算小时
            const minutes = Math.floor((totalSeconds % 3600) / 60); // 计算分钟
            const seconds = totalSeconds % 60;                // 计算秒

            return `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
        },
        handleCurrentChange(page){
            if(page){
                this.page=page
            }else{
                this.page=1
            }
            if(localStorage.studentInfo){
                let studentId=JSON.parse(localStorage.studentInfo).studentId
                api.getExamPaperListFinish(studentId,this.page,this.size).then(res=>{
                    this.total=res.data.total
                    this.examPaperList=res.data.list
                })
            }else{
                this.$message.error('请先登录')
                this.$router.push('/login')
            }
        },
        routerToExam(examPaperAllocationId){
            const path=this.$router.resolve({
                name:'reviewPage',
                params:{
                    examPaperAllocationId:examPaperAllocationId
                }
            });
            window.open(path.href,'_blank')
        }
    },
    mounted(){
        this.handleCurrentChange(1)
    }
}
</script>
<style scoped>
.title{
    padding:10px;
    font-size: 30px;
    background-color: #f4f4f5;
}
.title>i{
    cursor:pointer;
}
.my-card-style{
    margin-top:20px;
}
.my-card-style div{
    padding:5px;
}
</style>
