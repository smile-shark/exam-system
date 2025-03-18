<template>
    <div>
        <el-row>
            <el-col :span="24" class="title">
                <i class="el-icon-back" @click="routerPush('childHome')"></i>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24" style="padding-left:1%;padding-right: 1%;">
                <h1 style="text-align: center;">考试中试卷列表</h1>
                <el-empty description="没有考试" v-if="examPaperList.length==0"></el-empty>
                <el-card shadow="hover" v-for="(item,index) in examPaperList" :key="index" class="my-card-style">
                    <el-row>
                        <el-col :span="12">
                            <div style="font-size: 30px;font-weight: bold;">{{  item.examPaperRelease.examPaper.examPaperTitle }}</div>
                            <div>开始时间：{{ timestampToString(item.examPaperRelease.examStartTime) }}</div>
                            <div>结束时间：{{ timestampToString(item.examPaperRelease.examEndTime) }}</div>
                            <div>考试时长：{{ formatTimestampUTC(item.examPaperRelease.duration) }}</div>
                            <div>满分：{{ item.examPaperRelease.examPaper.totalScore }} 分</div>
                            <div>题目数量：{{ item.examPaperRelease.examPaper.questionCount }} 道</div>
                            <div v-if="item.examPaperAllocationState==0">考试状态：未开始</div>
                            <div v-if="item.examPaperAllocationState==1">考试状态：考试中</div>
                            <div v-if="item.examPaperAllocationState==2">考试状态：已完成</div>
                            <div v-if="item.examPaperAllocationState==3">考试状态：缺考</div>
                            <div>备注：{{ item.examPaperRelease.notes }}</div>
                        </el-col>
                        <el-col :span="12" style="display: flex;justify-content: flex-end;align-items: center;
                        padding:7%">
                            <el-badge is-dot style="padding:0">
                                <el-button type="primary" @click="routerToExam(item.examPaperAllocationId)">
                                    进入考试
                                </el-button>
                            </el-badge>
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
            size:5
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
            const date = new Date(timestamp);
            const hours = String(date.getUTCHours()).padStart(2, '0');
            const minutes = String(date.getUTCMinutes()).padStart(2, '0');
            const seconds = String(date.getUTCSeconds()).padStart(2, '0');
            return `${hours}:${minutes}:${seconds}`;
        },
        handleCurrentChange(page){
            if(page){
                this.page=page
            }else{
                this.page=1
            }
            if(localStorage.studentInfo){
                let studentId=JSON.parse(localStorage.studentInfo).studentId
                api.getExamPaperListByStudentIdAndState(studentId,1,this.page,this.size).then(res=>{
                    console.log(res)
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
                name:'examPaperPage',
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
