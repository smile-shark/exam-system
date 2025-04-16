<template>
    <div>
        <el-row>
            <el-col :span="24" style="min-height: 60vh;padding:2% 20%;">
                <div class="box-border">
                    <h2 v-if="answerRecords.filter(item=>item.question.questionType==0).length>0">
                        单选题
                    </h2>
                    <div v-for="(item,index) in answerRecords.filter(item=>item.question.questionType==0)" 
                        :key="item.question.questionId"
                        :id="item.question.questionId"
                        style="padding:10px;border-bottom: 1px solid #e6e6e6;margin-top: 10px;">
                        <div>
                            <span style="color:#409eff">{{ index+1 }} 【单选】</span>
                            <span v-html="item.question.question"></span>
                        </div>
                        <div 
                        v-for="(answer,index) in item.question.answers" 
                        :key="index" style="margin:10px">
                            <el-radio v-model="item.question.selectedId" :label="answer.answerId" disabled>
                                <span :style="item.question.selectedId==answer.answerId?{color:'#F56C6C',fontWeight:'bold'}:{color:'auto'}"
                                    v-html="answer.answer">
                                </span>
                            </el-radio>
                        </div>
                        <div style="background-color: #e6e6e6;border-radius: 5px;padding:20px;;">
                            <i class="el-icon-document-copy"></i>
                            <span style="color:#999999;font-weight: bold;margin-left: 10px;">
                                正确答案：
                            </span>
                            <div v-for="(answer,index) in item.question.answers" 
                            style="margin-top: 10px;;"
                            :key="index">
                                <span v-if="answer.isTrue" style="font-weight: bold;color:#0a95d3;" v-html="answer.answer"></span>
                            </div>
                        </div>
                    </div>
                    <h2 v-if="answerRecords.filter(item=>item.question.questionType==1).length>0">
                        多选题
                    </h2>
                    <div v-for="(item,index) in answerRecords.filter(item=>item.question.questionType==1)" 
                        :key="item.question.questionId"
                        :id="item.question.questionId"
                        style="padding:10px;border-bottom: 1px solid #e6e6e6;margin-top: 10px;">
                        <div>
                            <span style="color:#409eff">{{ index+1 }} 【多选】</span>
                            <span v-html="item.question.question"></span>
                        </div>
                        <el-checkbox-group  v-model="item.question.selectedIds">
                            <div  v-for="(answer) in item.question.answers" :key="answer.answerId" style="margin:10px">
                                <el-checkbox :label="answer.answerId" disabled>
                                    <span 
                                    :style="item.question.selectedIds.includes(answer.answerId)?{color:'#F56C6C',fontWeight:'bold'}:{color:'auto'}"
                                    v-html="answer.answer"></span>
                                </el-checkbox>
                            </div>
                        </el-checkbox-group>
                        <div style="background-color: #e6e6e6;border-radius: 5px;padding:20px;;">
                            <i class="el-icon-document-copy"></i>
                            <span style="color:#999999;font-weight: bold;margin-left: 10px;">
                                正确答案：
                            </span>
                            <div v-for="(answer,index) in item.question.answers" 
                            style="margin-top: 10px;"
                            :key="index">
                                <div v-if="answer.isTrue">
                                    <span  style="font-weight: bold;color:#0a95d3;" v-html="answer.answer">
                                    </span>
                                    <hr/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <h2 v-if="answerRecords.filter(item=>item.question.questionType==2).length>0">
                        判断题
                    </h2>
                    <div v-for="(item,index) in answerRecords.filter(item=>item.question.questionType==2)" 
                        :key="item.question.questionId"
                        :id="item.question.questionId"
                        style="padding:10px;border-bottom: 1px solid #e6e6e6;margin-top: 10px;">
                        <div>
                            <span style="color:#409eff">{{ index+1 }} 【判断】</span>
                            <span v-html="item.question.question"></span>
                        </div>
                        <div 
                        v-for="(answer,index) in item.question.answers" 
                        :key="index" style="margin:10px">
                            <el-radio v-model="item.question.selectedId" :label="answer.answerId" disabled>
                                <span :style="item.question.selectedId==answer.answerId?{color:'#F56C6C',fontWeight:'bold'}:{color:'auto'}"
                                    v-html="answer.answer">
                                </span>
                            </el-radio>
                        </div>
                        <div style="background-color: #e6e6e6;border-radius: 5px;padding:20px;;">
                            <i class="el-icon-document-copy"></i>
                            <span style="color:#999999;font-weight: bold;margin-left: 10px;">
                                正确答案：
                            </span>
                            <div v-for="(answer,index) in item.question.answers" 
                            style="margin-top: 10px;;"
                            :key="index">
                                <span v-if="answer.isTrue" style="font-weight: bold;color:#0a95d3;" v-html="answer.answer"></span>
                            </div>
                        </div>
                    </div>
                </div>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24" style="display: flex; justify-content: center; align-items: center;padding:20px">
                <el-pagination
                @current-change="answerRecordsPage"
                background
                :page-size="size"
                layout="prev, pager, next"
                :total="total">
                </el-pagination>
            </el-col>
        </el-row>
    </div>
</template>
<script>
import api from "@/axios/index"
export default {
    data(){
        return{
            total:0,
            answerRecords:[],
            page:1,
            size:10
        }
    },
    methods:{
        answerRecordsPage(page=1){
            this.page=page
            if(localStorage.studentInfo){
                let studentId=JSON.parse(localStorage.studentInfo).studentId
                api.getMistakesCollectionByStudentId(studentId,this.page,this.size).then(res=>{
                    for (let answerRecord of res.data.list){
                        if(answerRecord.question.questionType==1){
                            // 多选题
                            answerRecord.question.selectedIds=answerRecord.studentSelectedAnswers.map(item=>item.answerId)
                        }else{
                            answerRecord.question.selectedId=answerRecord.studentSelectedAnswers[0].answerId
                        }
                    }
                    console.log(res.data.list)
                    this.total=res.data.total
                    this.answerRecords=res.data.list
                })
            }
        }
    },
    mounted(){
        this.answerRecordsPage()
    }
}
</script>