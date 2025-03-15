<template>
    <div class="containter">
        <el-row>
            <el-col :span="24" >
                <div class="box-border title-style">
                    <div style="font-weight: bold;font-size: 1.5rem;">
                        {{ examPaperAllocation.examPaperRelease.examPaper.examPaperTitle }}
                    </div>
                    <div style="color: #999999;">
                        <div>
                            <span> 答题时间：{{ formatTimesToMinutes(
                                examPaperAllocation.examPaperRelease.examEndTime-examPaperAllocation.examPaperRelease.examStartTime
                            ) }} 分钟</span>
                            <span> 总题数： {{ examPaperAllocation.examPaperRelease.examPaper.questionCount }} 题</span>
                            <span> 总分： {{ examPaperAllocation.examPaperRelease.examPaper.totalScore }} 分</span>
                            <span v-if="examPaperAllocation.examPaperAllocationState==0"> 考试状态：未开始</span>
                            <span v-if="examPaperAllocation.examPaperAllocationState==1"> 考试状态：考试中</span>
                            <span v-if="examPaperAllocation.examPaperAllocationState==2"> 考试状态：已完成</span>
                            <span v-if="examPaperAllocation.examPaperAllocationState==3"> 考试状态：缺考</span>
                        </div>
                    </div>
                </div>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="18" >
                <div class="box-border">
                    <h2 v-if="examPaperAllocation.examPaperRelease.examPaper.questionOnTestPapers.filter(item=>item.question.questionType==0).length>0">
                        单选题
                    </h2>
                    <div v-for="(item,index) in examPaperAllocation.examPaperRelease.examPaper.questionOnTestPapers.filter(item=>item.question.questionType==0)" 
                        :key="item.question.questionId"
                        :id="item.question.questionId"
                        style="padding:10px;border-bottom: 1px solid #e6e6e6;margin-top: 10px;">
                        <div>
                            <span style="color:#0195d3">{{ index+1 }} 【单选】</span>
                            <span v-html="item.question.question"></span>
                        </div>
                        <div 
                        v-for="(answer,index) in item.question.answers" 
                        :key="index" style="margin:10px">
                            <el-radio v-model="item.question.selectedId" :label="answer.answerId">
                                <span 
                                    v-html="answer.answer">
                                </span>
                            </el-radio>
                        </div>
                    </div>
                    <h2 v-if="examPaperAllocation.examPaperRelease.examPaper.questionOnTestPapers.filter(item=>item.question.questionType==1).length>0">
                        多选题
                    </h2>
                    <div v-for="(item,index) in examPaperAllocation.examPaperRelease.examPaper.questionOnTestPapers.filter(item=>item.question.questionType==1)" 
                        :key="item.question.questionId"
                        :id="item.question.questionId"
                        style="padding:10px;border-bottom: 1px solid #e6e6e6;margin-top: 10px;">
                        <div>
                            <span style="color:#0195d3">{{ index+1 }} 【多选】</span>
                            <span v-html="item.question.question"></span>
                        </div>
                        <el-checkbox-group  v-model="item.question.selectedIds">
                            <div  v-for="(answer) in item.question.answers" :key="answer.answerId" style="margin:10px">
                                <el-checkbox :label="answer.answerId">
                                    <span v-html="answer.answer"></span>
                                </el-checkbox>
                            </div>
                        </el-checkbox-group>
                    </div>
                    <h2 v-if="examPaperAllocation.examPaperRelease.examPaper.questionOnTestPapers.filter(item=>item.question.questionType==2).length>0">
                        判断题
                    </h2>
                    <div v-for="(item,index) in examPaperAllocation.examPaperRelease.examPaper.questionOnTestPapers.filter(item=>item.question.questionType==2)" 
                        :key="item.question.questionId"
                        :id="item.question.questionId"
                        style="padding:10px;border-bottom: 1px solid #e6e6e6;margin-top: 10px;">
                        <div>
                            <span style="color:#0195d3">{{ index+1 }} 【判断】</span>
                            <span v-html="item.question.question"></span>
                        </div>
                        <div 
                        v-for="(answer,index) in item.question.answers" 
                        :key="index" style="margin:10px">
                            <el-radio v-model="item.question.selectedId" :label="answer.answerId">
                                <span 
                                    v-html="answer.answer">
                                </span>
                            </el-radio>
                        </div>
                    </div>
                </div>
            </el-col>
            <el-col :span="6" >
                <div style="position: fixed;">
                    <div class="box-border" >
                        <i class="el-icon-time"></i> 考试剩余时间
                        <span style="color:#5eb95e;font-weight: bold;"> 
                        {{ formattedTime }} 
                        </span>
                    </div>
                    <div class="box-border exam-card-box">
                        <div style="font-weight: bold;font-size: 1.1rem;">
                            答题卡
                        </div>
                        <div style="color:#999999;">
                            点击图标转跳到对应的题目
                        </div>
                        <div style="font-size: 14px;">
                            <div v-if="examPaperAllocation.examPaperRelease.examPaper.questionOnTestPapers.filter(item=>item.question.questionType==0).length>0">
                                <div>
                                    单选题
                                </div>
                                <div class="calendar ">
                                    <div v-for="(item,index) in examPaperAllocation.examPaperRelease.examPaper.questionOnTestPapers.filter(item=>item.question.questionType==0)" 
                                        :key="index"
                                        :style="item.question.selectedId?
                                        {backgroundColor:'#409eff',color:'white'}:
                                        {backgroundColor:'#f1f1f1',color:'#afafaf'}"
                                        @click="toQuestionPosition(item.question.questionId)">
                                        {{ index+1 }}
                                    </div>       
                                </div>
                            </div>
                            <div v-if="examPaperAllocation.examPaperRelease.examPaper.questionOnTestPapers.filter(item=>item.question.questionType==1).length>0">
                                <div>
                                    多选题
                                </div>
                                <div class="calendar ">
                                    <div v-for="(item,index) in examPaperAllocation.examPaperRelease.examPaper.questionOnTestPapers.filter(item=>item.question.questionType==1)" 
                                        :key="index"
                                        :style="item.question.selectedIds.length>0?
                                        {backgroundColor:'#409eff',color:'white'}:
                                        {backgroundColor:'#f1f1f1',color:'#afafaf'}"
                                        @click="toQuestionPosition(item.question.questionId)">
                                        {{ index+1 }}
                                    </div>       
                                </div>
                            </div>
                            <div v-if="examPaperAllocation.examPaperRelease.examPaper.questionOnTestPapers.filter(item=>item.question.questionType==2).length>0">
                                <div>
                                    判断题
                                </div>
                                <div class="calendar ">
                                    <div v-for="(item,index) in examPaperAllocation.examPaperRelease.examPaper.questionOnTestPapers.filter(item=>item.question.questionType==2)" 
                                        :key="index"
                                        :style="item.question.selectedId?
                                        {backgroundColor:'#409eff',color:'white'}:
                                        {backgroundColor:'#f1f1f1',color:'#afafaf'}"
                                        @click="toQuestionPosition(item.question.questionId)">
                                        {{ index+1 }}
                                    </div>       
                                </div>
                            </div>
                        </div>
                        <div style="display: flex;justify-content: center;align-items: center;">
                            <el-button type="primary" icon="el-icon-s-order" size="mini" >
                                提交答卷
                            </el-button>
                        </div>
                        <div style="font-size: 14px;">
                            <span style="display: inline-block;width:10px;height: 10px;background-color: #d2d2d2;"></span>
                            未做&nbsp;&nbsp;
                            <span style="display: inline-block;width:10px;height: 10px;background-color:#0195d3;"></span>
                            已做
                        </div>
                    </div>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import api from "@/axios/index"
export default {
    data(){
        return{
            examPaperAllocation:{
                examPaperRelease:{
                    examPaper:{
                        questionOnTestPapers:[
                            {
                                question:{
                                    answers:[
                                        {
                                            answer:'',
                                            answerId:''
                                        }
                                    ],
                                    question:'',
                                    questionId:'',
                                    questionType:0,
                                    selectedId:null,
                                    selectedIds:[]
                                }
                            }
                        ],
                        questionCount:0,
                        totalScore:0,
                        examPaperTitle:'',
                        examEndTime:0,
                        examStartTime:0
                    }
                },
                examPaperAllocationState:0,
                examPaperAllocationId:'',
                studentId:'',
            },
            formattedTime: "",
        }
    },
    methods:{
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
        formatTimestampNow(timestamp) {
            const now = new Date(); // 当前时间
            const date = new Date(timestamp); // 传入的时间戳
            const need = date - now; // 时间差（毫秒）

            // 将时间差转换为小时、分钟和秒
            const totalSeconds = Math.floor(need / 1000); // 总秒数
            const hours = String(Math.floor(totalSeconds / 3600)).padStart(2, '0'); // 小时
            const minutes = String(Math.floor((totalSeconds % 3600) / 60)).padStart(2, '0'); // 分钟
            const seconds = String(totalSeconds % 60).padStart(2, '0'); // 秒

            return `${hours}:${minutes}:${seconds}`;
        },
        formatTimesToMinutes(times){
            return times/(1000*60)
        },
        updateTime() {
        // 每秒更新 formattedTime
            this.formattedTime = this.formatTimestampNow(this.examPaperAllocation.examPaperRelease.examEndTime);
        },
        toQuestionPosition(questionId){
            window.location.hash=`#${questionId}`
        }
    },
    mounted(){
        console.log(this.$router.params)
        api.getExamPaperByExamPaperAllocationId(this.$route.params.examPaperAllocationId).then(res=>{
            //  初始化selectedIds为空数组
            res.data.examPaperRelease.examPaper.questionOnTestPapers.forEach(item=>{
                item.question.selectedIds = []
            })
            this.examPaperAllocation = res.data
        })
        setInterval(this.updateTime, 1000); // 每秒更新一次
    },
    beforeDestroy() {
        // 在组件销毁时清理定时器
        clearInterval(this.intervalId);
    }
}
</script>

<style scoped>
.containter{
    background-color: #f8f8f8;
    padding:20px 15%;
}
.box-border{
    border:2px solid #e6e6e6;
    background-color: white;
    padding:20px;
    margin:10px;
} 
.title-style{

}
.exam-card-box>div{
    margin-bottom:10px;
}
.calendar {
    display: grid;
    grid-template-columns: repeat(6, 1fr);
    gap: 10px;
    padding:5px;
}

.calendar div {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 30px;
    height: 30px;
    line-height: 10px;
    background-color: #f1f1f1;
    color:#afafaf;
    border-radius: 5px;
    text-align: center;
    cursor: pointer;
}
</style>