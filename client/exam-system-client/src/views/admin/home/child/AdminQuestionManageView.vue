<template>
    <div>
        <el-row>
            <el-col :span="24">
                <div class="search-container">
                    <el-select v-model="courseId" @change="getChapterList" filterable clearable  placeholder="课程（可选择）">
                        <el-option
                        v-for="item in courseList"
                        :key="item.courseId"
                        :label="item.courseName"
                        :value="item.courseId">
                        </el-option>
                    </el-select>
                </div>
                <div class="search-container">
                    <el-select v-model="chapterId" @change="getSelectionList" filterable clearable  placeholder="章节（可选择）">
                        <el-option
                        v-for="item in chapterList"
                        :key="item.chapterId"
                        :label="item.chapterTitle+' '+item.chapterName"
                        :value="item.chapterId">
                        </el-option>
                    </el-select>
                </div>
                <div class="search-container">
                    <el-select v-model="subsectionId" filterable clearable  placeholder="小节（可选择）">
                        <el-option
                        v-for="item in subsectionList"
                        :key="item.subsectionId"
                        :label="item.subsectionName"
                        :value="item.subsectionId">
                        </el-option>
                    </el-select>
                </div>
                <div class="search-container">
                    <el-input v-model="questionContent" placeholder="题目"></el-input>
                </div>
                <div class="search-container">
                    <template>
                    <el-checkbox v-model="vague">模糊查询</el-checkbox>
                    </template>
                </div>
                
                <div class="search-container">
                    <el-select v-model="questionType" filterable placeholder="题目类型（可选择）">
                        <el-option
                        v-for="item in questionTypeList"
                        :key="item.questionType"
                        :label="item.questionTypeName"
                        :value="item.questionType">
                        </el-option>
                    </el-select>
                </div>
                <div class="search-container">
                    <el-button type="primary" @click="serachQuestionList(1)">搜索</el-button>
                </div>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24" style="height: 75vh;">
                <template>
                <el-table
                :data="questions"
                style="width: 100%;"
                height="100%">
                <el-table-column
                    prop="questionId"
                    label="问题ID"
                    width="300">
                </el-table-column>
                <el-table-column
                label="问题"
                width="800">
                <template slot-scope="scope">
                    <div style="overflow-x: hidden; white-space: nowrap; text-overflow: ellipsis;">
                        {{ scope.row.question }}
                    </div>
                </template>
                </el-table-column>
                <el-table-column
                label="问题类型">
                <template slot-scope="scope">
                    <span v-if="scope.row.questionType===0">单选题</span>
                    <span v-if="scope.row.questionType===1">多选题</span>
                    <span v-if="scope.row.questionType===2">判断题</span>
                </template>
                </el-table-column>
                <el-table-column
                label="操作"
                fixe="right">
                    <template slot-scope="scope">
                        <el-button type="text" @click="viewQuestion(scope.row)">查看</el-button>
                        <el-button type="text" @click="editQuestion(scope.row)">编辑</el-button>
                        <el-button type="text" @click="deleteQuestion(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
                </el-table>
            </template>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24" style="display: flex;justify-content: center;align-items: center;">
                <el-pagination
                @current-change="serachQuestionList"
                background
                :page-size="size"
                :hide-on-single-page="true"
                layout="prev, pager, next"
                :total="questionCount">
                </el-pagination>
            </el-col>
        </el-row>

        <!-- 弹窗 -->
        <el-dialog
        title="详细问题"
        :visible.sync="dialogShowQuestion.show"
        width="50%">
        <el-form>
            <el-form-item label="问题ID"> 
                {{ dialogShowQuestion.questionId }}
            </el-form-item>
            <el-form-item label="问题"> 
                {{ dialogShowQuestion.question }}
            </el-form-item>
            <el-form-item label="问题类型"> 
                <span v-if="dialogShowQuestion.questionType===0">单选题</span>
                <span v-if="dialogShowQuestion.questionType===1">多选题</span>
                <span v-if="dialogShowQuestion.questionType===2">判断题</span>
            </el-form-item>
            <el-form-item label="课程"> 
                {{ dialogShowQuestion.courseName }}
            </el-form-item>
            <el-form-item label="章节"> 
                {{ dialogShowQuestion.chapterTitle }} {{ dialogShowQuestion.chapterName }}
            </el-form-item>
            <el-form-item label="小节"> 
                {{ dialogShowQuestion.subsectionName }}
            </el-form-item>
            <el-form-item label="答案">
                <el-table
                :data="dialogShowQuestion.answers"
                style="width: 100%;">
                    <el-table-column 
                    prop="answerId"
                    label="答案ID"
                    width="300"></el-table-column>
                    <el-table-column>
                        <template slot-scope="scope">
                            <div style="overflow-x: hidden; white-space: nowrap; text-overflow: ellipsis;">
                                {{ scope.row.answer }}
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column>
                        <template slot-scope="scope">
                            <span v-if="scope.row.isTrue">正确</span>
                            <span v-else>错误</span>
                        </template>
                    </el-table-column>
                </el-table>
            </el-form-item>
        </el-form>


        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="dialogShowQuestion.show = false">确 定</el-button>
        </span>
        </el-dialog>


    </div>
</template>
<script>
import api from '@/axios/index'
export default{
    data(){
        return{
            dialogShowQuestion:{
                show:false,
                questionId:'',
                question:'',
                questionType:0,
                courseName:'',
                chapterName:'',
                chapterTitle:'',
                subsectionName:'',
                answers:[]
            },
            questionContent: '',
            questionType:3,
            questionTypeList:[
                {
                    questionType:3,
                    questionTypeName:'全部'
                },
                {
                    questionType:0,
                    questionTypeName:'单选题'
                },
                {
                    questionType:1,
                    questionTypeName:'多选题'
                },
                {
                    questionType:2,
                    questionTypeName:'判断题'
                }
            ],
            vague:true,
            courseId:'',
            chapterId:'',
            subsectionId:'',
            courseList:[],
            chapterList:[],
            subsectionList:[],
            page:1,
            size:10,
            questionCount:0,
            questions:[],
        }
    },
    methods:{
        viewQuestion(row){
            // 通过subsectionId获取小节信息及章节、课程信息
            api.getSubsectionInfo(row.subsectionId).then(res=>{
                this.dialogShowQuestion.subsectionName=res.data.subsectionName
                this.dialogShowQuestion.chapterName=res.data.chapter.chapterName
                this.dialogShowQuestion.chapterTitle=res.data.chapter.chapterTitle
                this.dialogShowQuestion.courseName=res.data.chapter.course.courseName
            })

            // 通过questionId获取答案信息
            api.getAnswerInfo(row.questionId).then(res=>{
                this.dialogShowQuestion.answers=res.data
            })

            this.dialogShowQuestion.questionId=row.questionId
            this.dialogShowQuestion.question=row.question
            this.dialogShowQuestion.questionType=row.questionType

            this.dialogShowQuestion.show=true
        },
        getChapterList(){
            this.subsectionList=[]
            this.chapterId=''
            this.subsectionId=''
            api.getChapterListNameTitleAndId(this.courseId).then(res=>{
                this.chapterList=res.data
            })
        },
        getSelectionList(){
            this.subsectionId=''
            api.getSubsectionListNameAndId(this.chapterId).then(res=>{
                this.subsectionList=res.data
            })
        },
        serachQuestionList(page){
            if(page){
                this.page=page
            }else{
                page=1
            }
            let types=[]
            switch(this.questionType){
                case 0:
                    types=[0]
                    break
                case 1:
                    types=[1]
                    break
                    case 2:
                        types=[2]    
                        break
                        case 3:
                            types=[0,1,2]    
                            break
            }
            api.getQuestionList(this.page,this.size,this.courseId,this.chapterId,this.subsectionId,types,this.questionContent,this.vague).then(res=>{
                this.questions=res.data.list
                this.questionCount=res.data.total
            })
        }
    },
    mounted(){
        api.getCourseListNameAndId().then(res=>{
            this.courseList = res.data
        })
        this.serachQuestionList()
    }
}
</script>
<style scoped>
.search-container{
    display: inline-block;
    padding:10px;
}
</style>