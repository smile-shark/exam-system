<template>
    <div>
        <el-row>
            <el-col :span="6">
                <el-form ref="form" :model="examPaper" label-width="120px" >
                <el-form-item label="课程：">
                    <el-select v-model="examPaper.courseIds" filterable multiple placeholder="请选择" @visible-change="selectHiddleMethod($event,getChapters)">
                        <el-option
                        v-for="item in courses"
                        :key="item.courseId"
                        :label="item.courseName"
                        :value="item.courseId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="章节：">
                    <el-select v-model="examPaper.chapterIds" filterable multiple placeholder="请选择" @visible-change="selectHiddleMethod($event,getSubsections)">
                        <el-option
                        v-for="item in chapters"
                        :key="item.chapterId"   
                        :label="item.chapterTitle+' '+item.chapterName"
                        :value="item.chapterId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="小节：">
                    <el-select v-model="examPaper.subsectionIds" filterable multiple placeholder="请选择">
                        <el-option
                        v-for="item in subsections"
                        :key="item.subsectionId"
                        :label="item.subsectionName"
                        :value="item.subsectionId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="试卷标题：">
                    <el-input v-model="examPaper.examPaperTitle" type="text" placeholder="请输入试卷标题"></el-input>
                </el-form-item>
                <el-form-item label="试卷总分：">
                    <el-input v-model="examPaper.examPaperScore" type="number" min="0" placeholder="请输入试卷总分"></el-input>
                </el-form-item>
                <el-form-item label="单选题数量：">
                    <el-input v-model="examPaper.questionCountType0" type="number" min="0" placeholder="请输入题目数量"></el-input>
                </el-form-item>
                <el-form-item label="多选题数量：">
                    <el-input v-model="examPaper.questionCountType1" type="number" min="0" placeholder="请输入题目数量"></el-input>
                </el-form-item>
                <el-form-item label="判断题数量：">
                    <el-input v-model="examPaper.questionCountType2" type="number" min="0" placeholder="请输入题目数量"></el-input>
                </el-form-item>
                <el-form-item label-width="40px">
                    <el-button type="primary" @click.prevent="createExamPaper">生成试卷</el-button>
                    <el-button type="primary" @click.prevent="getQuestions">拉取题目</el-button>
                    <el-button @click.prevent="reset">重置</el-button>
                </el-form-item>
                </el-form>
            </el-col>
            <el-col :span="18" style="padding:10px;">
                <h2 style="display: flex;justify-content: center;align-items: center;">{{ examPaper.examPaperTitle }}</h2>
                <div style="padding:10px;">
                    试卷总分：
                    <span style="font-weight: bold;">{{ examPaper.examPaperScore }}</span>
                    <span style="display: inline-block;width: 30px;"></span>
                    题目总数：
                    <span style="font-weight: bold;">{{ questionNum() }}</span>
                </div>
                <div style="background-color: #f0eeed;min-height: 75vh;">
                    <!-- 三个table（单选，多选，判断） -->
                    <el-row>
                        <el-col :span="24" v-if="questions.length>0" style="padding:10px">
                            <h2 v-if="examPaper.questionCountType0>0" style="padding:10px;">单选题</h2>
                            <el-table v-if="examPaper.questionCountType0>0"  :data="questions.filter(item=>item.questionType==0)" style="width: 100%;">
                                <el-table-column label="刷新"
                                fixed="right"
                                width="100">
                                    <template slot-scope="scope">
                                        <el-button type="primary" 
                                        @click.prevent="refreshQuestion(scope.row)"
                                        icon="el-icon-refresh-left"></el-button>
                                    </template>
                                </el-table-column>
                                <el-table-column label="题目ID" prop="questionId" width="300"></el-table-column>
                                <el-table-column label="题目">
                                   <template slot-scope="scope">
                                    <div style="overflow-x: hidden; white-space: nowrap; text-overflow: ellipsis;" 
                                    v-html="scope.row.question">
                                    </div>
                                   </template>
                                </el-table-column>
                            </el-table>
                            <h2 v-if="examPaper.questionCountType1>0" style="padding:10px;">多选题</h2>
                            <el-table v-if="examPaper.questionCountType1>0" :data="questions.filter(item=>item.questionType==1)" style="width: 100%;">
                                <el-table-column label="刷新"
                                fixed="right"
                                width="100">
                                    <template slot-scope="scope">
                                        <el-button type="primary" 
                                        @click.prevent="refreshQuestion(scope.row)"
                                        icon="el-icon-refresh-left"></el-button>
                                    </template>
                                </el-table-column>
                                <el-table-column label="题目ID" prop="questionId" width="300"></el-table-column>
                                <el-table-column label="题目">
                                   <template slot-scope="scope">
                                    <div style="overflow-x: hidden;white-space: nowrap;text-overflow: ellipsis;">
                                        {{ scope.row.question }}
                                    </div>
                                   </template>
                                </el-table-column>
                            </el-table>
                            <h2 v-if="examPaper.questionCountType2>0" style="padding:10px;">判断题</h2>
                            <el-table v-if="examPaper.questionCountType2>0" :data="questions.filter(item=>item.questionType==2)" style="width: 100%;">
                                <el-table-column label="刷新"
                                fixed="right"
                                width="100">
                                    <template slot-scope="scope">
                                        <el-button type="primary" 
                                        @click.prevent="refreshQuestion(scope.row)"
                                        icon="el-icon-refresh-left"></el-button>
                                    </template>
                                </el-table-column>
                                <el-table-column label="题目ID" prop="questionId" width="300"></el-table-column>
                                <el-table-column label="题目">
                                   <template slot-scope="scope">
                                    <div style="overflow-x: hidden;white-space: nowrap;text-overflow: ellipsis;">
                                        {{ scope.row.question }}
                                    </div>
                                   </template>
                                </el-table-column>
                            </el-table>
                        </el-col>
                    </el-row>
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
            examPaper:{
                courseIds:[],
                chapterIds:[],
                subsectionIds:[],
                questionCountType0:0,
                questionCountType1:0,
                questionCountType2:0,
                examPaperTitle:'试卷标题',
                examPaperScore:100,
            },
            courses:[],
            chapters:[],
            subsections:[],
            questions:[],
            isSubmit:false,
        }
    },
    methods:{
        selectHiddleMethod(isShow,fun){
            if(!isShow){
                fun()
            }
        },
        getChapters(){
            if(this.examPaper.courseIds.length==0){
                return
            }
            api.chapterListNameTitleAndIdInCourseId(this.examPaper.courseIds).then(res=>{
                this.chapters=res.data
            })
        },
        getSubsections(){
            if(this.examPaper.chapterIds.length==0){
                return
            }
            api.subsectionListNameAndIdInChapterId(this.examPaper.chapterIds).then(res=>{
                this.subsections=res.data
            })
        },
        getQuestions(){
            //试卷标题不能为空
            if(this.examPaperTitle==''){
                this.$message.error('试卷标题不能为空')
                return
            }
            //试卷标题不能为'试卷标题'
            if(this.examPaperTitle==='试卷标题'){
                this.$message.error('试卷标题不能为“试卷标题”')
                return
            }
            //试卷总分不能为空
            if(this.examPaperScore==''){
                this.$message.error('试卷总分不能为空')
                return
            }
            // 试卷总分不能为0
            if(this.examPaperScore=='0'){
                this.$message.error('试卷总分不能为0')
                return
            }
            // 题目数量不能为0
            if(this.questionNum()==0){
                this.$message.error('题目数量不能为0')
                return
            }
            api.pullQuestionListByParams(
                this.examPaper.courseIds,
                this.examPaper.chapterIds,
                this.examPaper.subsectionIds,
                this.examPaper.questionCountType0,
                this.examPaper.questionCountType1,
                this.examPaper.questionCountType2
            ).then(res=>{
                this.questions=res.data
            })
        },
        questionNum(){
            let count=
            parseInt(this.examPaper.questionCountType0)+
            parseInt(this.examPaper.questionCountType1)+
            parseInt(this.examPaper.questionCountType2)
            return count
        },
        refreshQuestion(row){
            api.questionListByParamsNotQuestionId(
                this.examPaper.courseIds,
                this.examPaper.chapterIds,
                this.examPaper.subsectionIds,
                row.questionType,
                row.questionId
            ).then(res=>{
                // 添加题目
                this.questions.push(res.data)
            })
            // 删除题目
            this.questions.splice(this.questions.findIndex(item=>item.questionId==row.questionId),1)
        },
        createExamPaper(){
            if(this.questions.length==0){
                this.$message.error('请先拉取题目')
                return
            }
            let adminId=JSON.parse(localStorage.adminInfo).administratorId
            if(adminId&&!this.isSubmit){
                // 点快了会创建很多要加个防抖
                this.isSubmit=true
                api.createExamPaper(adminId,this.examPaper.examPaperTitle,this.questions,this.examPaper.examPaperScore).then(res=>{
                    if(res.msg=='创建成功'){
                        // 全部重置
                        this.reset()
                        this.isSubmit=false
                    }
                })
            }else{
                this.$message.error('请先登录')
                return
            }
        },
        reset(){
            this.examPaper={
                questionCountType0:0,
                questionCountType1:0,
                questionCountType2:0,
                examPaperTitle:'试卷标题',
                examPaperScore:100,
            }
            this.questions=[]
        }
    },
    mounted(){
        api.getCourseListNameAndId().then(res=>{
            this.courses=res.data
        })
    }
}
</script>

<style scoped>

</style>