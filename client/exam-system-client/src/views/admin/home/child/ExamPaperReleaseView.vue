<template>
    <div>
        <el-row>
            <el-col :span="6">
                <el-form ref="form" :model="release" label-width="120px" >
                    <el-form-item label="试卷：">
                        <el-select v-model="release.examPaperId" filterable placeholder="请选择试卷">
                            <el-option
                            v-for="item in examPapers"
                            :key="item.examPaperId"
                            :label="item.examPaperTitle"
                            :value="item.examPaperId">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="考试开始时间：">
                        <el-date-picker
                        v-model="release.examStartTime"
                        type="datetime"
                        placeholder="选择考试开始时间">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="考试结束时间：">
                        <el-date-picker
                        v-model="release.examEndTime"
                        type="datetime"
                        placeholder="选择考试结束时间">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="考试时长：">
                        <span>{{ marks[release.duration] }}</span><br>
                        <el-slider
                        :max="9"
                        v-model="release.duration"
                        :step="1"
                        :show-stop="true">
                        </el-slider>
                    </el-form-item>
                    <el-form-item label="备注：">
                        <el-input
                        type="textarea"
                        :autosize="{ minRows: 4, maxRows: 20}"
                        placeholder="请输入内容"
                        v-model="release.notes">
                        </el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="releaseExamPaper">发布试卷</el-button>
                        <el-button  @click="reset">重置</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
            <el-col :span="18">
                <el-row>
                    <el-col :span="24">
                        <!-- 可以用来搜索和筛选学生 -->
                    </el-col>
                </el-row>
                <el-row>    
                    <el-col :span="24" style="padding:20px">
                        <h2>学生列表</h2>
                        <!-- 学生列表 -->
                         <el-table :data="students" style="width: 100%" @selection-change="handleSelectionChange"
                         ref="table">
                            <el-table-column type="selection" width="55"/>
                            <el-table-column label="学生ID" prop="studentId" with="300"/>
                            <el-table-column prop="studentName" label="学生姓名" width="150"/>
                            <el-table-column label="教师">
                                <template slot-scope="scope">
                                    {{ scope.row.administrator.administratorName }}
                                </template>
                            </el-table-column>
                         </el-table>    
                         <el-button @click="toggleSelection()" style="margin: 20px;">取消选择</el-button>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
    </div>
</template>
<script>
import api from "@/axios/index"
 export default{
    data(){
        return{
            examPapers:[],
            release:{
                examPaperId:'',
                examStartTime:'',
                examEndTime:'',
                notes:'默认备注',
                duration:1
            },
            students:[],
            studentsSelected:[],
            isRelease:false,
            marks:{
                0:'15分钟',
                1:'30分钟',
                2:'45分钟',
                3:'60分钟',
                4:'1小时15分钟',
                5:'1小时30分钟',
                6:'1小时45分钟',
                7:'2小时',
                8:'2小时15分钟',
                9:'2小时30分钟'
            }
        }
    },
    methods:{
        releaseExamPaper(){
            // 未选择试卷
            if(!this.release.examPaperId){
                this.$message.error("请选择试卷！")
                return
            }
            // 未选择学生
            if(this.studentsSelected.length==0){
                this.$message.error("请选择学生！")
                return
            }
            // 未选择开始时间
            if(!this.release.examStartTime){
                this.$message.error("请选择考试开始时间！")
                return
            }
            // 未选择结束时间
            if(!this.release.examEndTime){
                this.$message.error("请选择考试结束时间！")
                return
            }
            // 结束时间不能小于当前时间
            if(this.release.examEndTime<new Date()){
                this.$message.error("考试结束时间不能小于当前时间！")
                return
            }
            // 开始时间大于结束时间
            if(this.release.examStartTime>this.release.examEndTime){
                this.$message.error("考试开始时间大于结束时间！")
                return
            }
            // 备注不能为空
            if(!this.release.notes){
                this.$message.error("备注不能为空！")
                return
            }
            // 时长不能为空
            if(!this.release.duration){
                this.$message.error("时长不能为空！")
                return
            }
            if(localStorage.adminInfo){
                if(!this.isRelease){
                    let administratorId=JSON.parse(localStorage.adminInfo).administratorId
                    this.isRelease=true
                    api.releaseExamPaper(
                        this.release.examPaperId,
                        administratorId,
                        this.release.examStartTime,
                        this.release.examEndTime,
                        this.release.notes,
                        this.studentsSelected,
                        this.release.duration).then(res=>{
                            this.reset()
                            this.isRelease=false
                    }).catch(err=>{
                        this.isRelease=false
                        console.log(err)
                    })
                }else{
                    this.$message.error("试卷正在发布中！")
                }
            }else{
                this.$message.error("请先登录！")
            }
        },
        handleSelectionChange(val){
            this.studentsSelected=val
        },
        toggleSelection(rows) {
            if (rows) {
            rows.forEach(row => {
                this.$refs.table.toggleRowSelection(row);
            });
            } else {
                this.$refs.table.clearSelection();
            }
            this.studentsSelected = []
        },
        reset(){
            this.release={
                examPaperId:'',
                examStartTime:'',
                examEndTime:'',
                notes:'',
                duration:1
            },
            this.toggleSelection()
            this.studentsSelected=[]
        },
    },
    mounted(){
        api.getExamPaperIdTitle().then(res=>{
            this.examPapers = res.data
        })
        api.getAllStudent().then(res=>{
            this.students = res.data
        })
    }
 }
</script>