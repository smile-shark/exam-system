<template>
    <div style="padding:20px">
        <el-row>
            <el-col :span="6">
                <h2>课程添加</h2>
                <el-form >
                    <el-form-item label="课程名称" :model="course" style="width: 100%;">
                        <el-input v-model="course.courseName" placeholder="请输入课程名称"></el-input>
                    </el-form-item>
                    <el-form-item label="课程图片（添加图片连接）" :model="course">
                        <el-input v-model="course.courseImage" placeholder="请输入课程图片连接"></el-input>
                        <el-image :src="course.courseImage">
                            <div slot="error" class="image-slot">
                                <i class="el-icon-picture-outline"></i>
                            </div>
                        </el-image>
                    </el-form-item>
                    <el-form-item label="课程描述">
                        <el-input type="textarea" v-model="course.courseDescribe" placeholder="请输入课程描述"></el-input>
                    </el-form-item>
                    <el-form-item label="添加章节">
                        <br>
                        <el-form v-for="(chapter,index) in course.chapters" :key="index" :data="chapter">
                            <el-form-item label="章节标题" label-width="100px">
                                <el-input v-model="chapter.chapterName" placeholder="请输入章节标题（第*章）"></el-input>
                            </el-form-item>
                            <el-form-item label="章节名称" label-width="100px">
                                <el-input v-model="chapter.chapterName" placeholder="请输入章节名称"></el-input>
                            </el-form-item>
                            <el-form-item label="添加小节">
                                <br>
                                <el-form v-for="(subsection,index) in chapter.subsections" :key="index" :data="subsection">
                                    <el-form-item label="小节名称" label-width="120px">
                                        <el-input v-model="subsection.subsectionName" placeholder="请输入小节名称"></el-input>
                                    </el-form-item> 
                                </el-form>
                            </el-form-item>
                            <el-form-item label-width="120px">
                                <el-button size="mini" type="primary" icon="el-icon-plus" @click="chapter.subsections.push({
                                    subsectionName:''
                                })">小节
                                </el-button>
                                <el-button size="mini" type="warning" icon="el-icon-minus" @click="chapter.subsections.pop()">
                                    小节
                                </el-button>
                            </el-form-item>
                        </el-form>
                    </el-form-item>
                    <el-form-item label-width="80px">
                        <el-button size="small" type="primary" icon="el-icon-plus" @click="course.chapters.push({
                            chapterName:'',
                            chapterTitle:'',
                            subsections:[]
                        })">章节
                        </el-button>
                        <el-button size="small" type="warning" icon="el-icon-minus" @click="course.chapters.pop()">
                            章节
                        </el-button>
                    </el-form-item>
                    <el-form-item style="display: flex;justify-content: center;">
                        <el-button size="medium" type="success" @click="addCourse">
                            确认添加
                        </el-button>
                    </el-form-item>
                </el-form>
            </el-col>
            <el-col :span="18">
                <div>
                    <el-form label-width="20px" style="display: flex;align-items: center;justify-content: center;">
                        <el-form-item>
                            <h2>课程列表</h2>
                        </el-form-item>
                        <el-form-item>
                            <el-input v-model="courseName" placeholder="请输入课程名称"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-checkbox v-model="vague">模糊搜索</el-checkbox>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="nextPage(1)">搜索</el-button>
                        </el-form-item>
                    </el-form>
                </div>
                <div style="padding:20px">
                    <el-table :data="courses" style="width: 100%" height="740">
                        <el-table-column prop="courseId" label="课程ID" width="300"></el-table-column>
                        <el-table-column prop="courseName" label="课程名称"></el-table-column>
                        <el-table-column prop="courseImage" label="课程图片">
                            <template slot-scope="scope">
                                <el-image
                                style="width: 100px;height: 100px;"
                                :src="scope.row.courseImage"
                                fit="contain"></el-image>
                            </template>
                        </el-table-column>
                        <el-table-column label="描述">
                            <template slot-scope="scope">
                                <div v-html="scope.row.courseDescribe" 
                                style="overflow-x: hidden;white-space: nowrap;text-overflow: ellipsis;width:100%;height: 100%;"></div>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" fixed="right" width="120" >
                            <template slot-scope="scope" >
                            <div style="display: flex;flex-direction: column;align-items: right;gap: 10px;">
                                <el-button type="success" @click="showChapters(scope.row)" size="mini">查看章节</el-button>
                                <el-button type="success" @click="addChapter(scope.row)" size="mini">添加章节</el-button>
                                <el-button type="primary" @click="editCourse(scope.row)" size="mini">编辑</el-button>
                                <el-button type="danger" @click="deleteCourse(scope.row)" size="mini">删除</el-button>
                            </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
                <div style="display: flex;justify-content: center;align-items: center;padding:20px">
                    <el-pagination
                    background
                    layout="prev, pager, next"
                    :total="total"
                    @current-change="nextPage">
                    </el-pagination>
                </div>
            </el-col>
        </el-row>
        <el-dialog title="查看章节" :visible.sync="chaptersDialogVisible" width="80%">
            <el-table :data="chapters" style="width: 100%">  
                <el-table-column prop="chapterId" label="章节ID" width="300"></el-table-column>
                <el-table-column prop="chapterName" label="章节名称"></el-table-column>
                <el-table-column prop="chapterTitle" label="章节标题"></el-table-column>
                <el-table-column label="操作" fixed="right">    
                    <template slot-scope="scope">
                        <el-button type="success" @click="showSubsections(scope.row)" size="mini">查看小节</el-button>
                        <el-button type="success" @click="addSubsection(scope.row)" size="mini">添加小节</el-button>
                        <el-button type="primary" @click="editChapter(scope.row)" size="mini">编辑</el-button>
                        <el-button type="danger" @click="deleteChapter(scope.row)" size="mini">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
        <el-dialog title="查看小节" :visible.sync="subsectionsDialogVisible" width="80%">
            <el-table :data="subsections" style="width: 100%">  
                <el-table-column prop="subsectionId" label="小节ID" width="300"></el-table-column>
                <el-table-column prop="subsectionName" label="小节名称"></el-table-column>
                <el-table-column label="操作" fixed="right">    
                    <template slot-scope="scope">
                        <el-button type="primary" @click="editSubsection(scope.row)" size="mini">编辑</el-button>
                    <el-button type="danger" @click="deleteSubsection(scope.row)" size="mini">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>
<script>
import api from '@/axios/index'
export default {
    data(){
        return{
            course:{
                courseName:'',
                courseImage:'',
                courseDescribe:'',
                chapters:[
                    {
                        chapterName:'',
                        chapterTitle:'',
                        subsections:[
                            {
                                subsectionName:''
                            }
                        ]
                    }
                ]
            },
            courses:[
                {
                    courseId:'',
                    courseName:'',
                    courseImage:'',
                    courseDescribe:'',
                    chapters:[
                        {
                            chapterId:'',
                            chapterName:'',
                            chapterTitle:'',
                            subsections:[
                                {
                                    subsectionId:'',
                                    subsectionName:''
                                }
                            ]
                        }
                    ]
                }
            ],
            chapters:[
                {
                    chapterId:'',
                    chapterName:'',
                    chapterTitle:'',
                    subsections:[
                        {
                            subsectionId:'',
                            subsectionName:''
                        }
                    ]
                }
            ],
            subsections:[
                {
                    subsectionId:'',
                    subsectionName:''
                }
            ],
            chaptersDialogVisible:false,
            subsectionsDialogVisible:false,
            page:1,
            size:10,
            total:0,
            courseName:'',
            vague:true
        }
    },
    methods:{
        showChapters(row){
            this.chapters=row.chapters
            this.chaptersDialogVisible=true
        },
        showSubsections(row){
            console.log(row)
            this.subsections=row.subsections
            this.subsectionsDialogVisible=true
        },
        editCourse(row){
            
        },
        deleteCourse(row){

        },
        editChapter(row){

        },
        deleteChapter(row){
            
        },
        addCourse(){
            // 课程名称不能为空
            if(!this.course.courseName){
                this.$message.error('课程名称不能为空')
                return
            }
            // 课程图片不能为空
            if(!this.course.courseImage){
                this.$message.error('课程图片不能为空')
                return
            }
            // 课程描述不能为空
            if(!this.course.courseDescribe){
                this.$message.error('课程描述不能为空')
                return
            }
            // 章节不能为空
            if(!this.course.chapters.length){
                this.$message.error('章节不能为空')
                return
            }
            // 小节不能为空
            for(let i=0;i<this.course.chapters.length;i++){
                if(!this.course.chapters[i].subsections.length){
                    this.$message.error('第'+(i+1)+'章节小节不能为空')
                    return
                }
            }
            api.insertCourse(this.course).then(res=>{
                this.nextPage(1)
            })
        },
        nextPage(page){
            if(page){
                this.page=page
            }else{
                this.page=1
            }
            api.getAllCourses(this.page,this.size,this.courseName,this.vague).then(res=>{
                this.courses=res.data.list
                this.total=res.data.total
            })
        }
    },
    mounted(){
        this.nextPage(1)
    }
}
</script>