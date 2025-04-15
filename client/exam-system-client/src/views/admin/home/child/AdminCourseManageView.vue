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
                                <el-input v-model="chapter.chapterTitle" placeholder="请输入章节标题（第*章）"></el-input>
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

        <!-- 添加章节 -->
        <el-dialog title="添加章节" :visible.sync="addChapterGroup.show" width="40%">
            <el-form :model="addChapterGroup.chapter">
                <el-form-item label="章节标题" label-width="100px">
                    <el-input v-model="addChapterGroup.chapter.chapterTitle" placeholder="请输入章节标题（第*章）"></el-input>
                </el-form-item>
                <el-form-item label="章节名称" label-width="100px">
                    <el-input v-model="addChapterGroup.chapter.chapterName" placeholder="请输入章节名称"></el-input>
                </el-form-item>
                <el-form-item label="添加小节">
                    <br>
                    <el-form-item v-for="(subsection, index) in addChapterGroup.chapter.subsections" :key="index" label="小节名称" label-width="120px">
                    <el-input v-model="subsection.subsectionName" placeholder="请输入小节名称"></el-input>
                    </el-form-item>
                </el-form-item>
                <el-form-item label-width="120px">
                    <el-button size="mini" type="primary" icon="el-icon-plus" @click="addChapterGroup.chapter.subsections.push(
                        { subsectionName: '' })">
                    添加小节
                    </el-button>
                    <el-button size="mini" type="warning" icon="el-icon-minus" @click="addChapterGroup.chapter.subsections.pop()">
                    删除小节
                    </el-button>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="addChapterSubmit">确认添加</el-button>
            </div>
        </el-dialog>

        <!-- 编辑课程 -->
        <el-dialog title="编辑课程" :visible.sync="editCourseGroup.show" width="40%">
        <el-form :model="editCourseGroup.course">
            <el-form-item label="课程名称" style="width: 100%;">
                <el-input v-model="editCourseGroup.course.courseName" placeholder="请输入课程名称"></el-input>
            </el-form-item>
            <el-form-item label="课程图片（添加图片连接）">
                <el-input v-model="editCourseGroup.course.courseImage" placeholder="请输入课程图片连接"></el-input>
                <el-image :src="editCourseGroup.course.courseImage">
                    <div slot="error" class="image-slot"></div>
                </el-image>
            </el-form-item>
            <el-form-item label="课程描述">
                <el-input type="textarea" v-model="editCourseGroup.course.courseDescribe" autosize placeholder="请输入课程描述"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="editCourseSubmit">确认修改</el-button>
        </div>
        </el-dialog>

        <!-- 添加小节 -->
        <el-dialog title="添加小节" :visible.sync="addSubsectionGroup.show" width="40%">
            <el-form :model="addSubsectionGroup.subsection">
                <el-form-item label="小节名称" label-width="120px">
                    <el-input v-model="addSubsectionGroup.subsection.subsectionName" placeholder="请输入小节名称"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="addSubsectionSubmit">确认添加</el-button>
            </div>
        </el-dialog>

        <!-- 编辑章节 -->
        <el-dialog title="编辑章节" :visible.sync="editChapterGroup.show" width="40%">
            <el-form :model="editChapterGroup.chapter">
                <el-form-item label="章节标题" label-width="100px">
                    <el-input v-model="editChapterGroup.chapter.chapterTitle" placeholder="请输入章节标题（第*章）"></el-input>
                </el-form-item>
                <el-form-item label="章节名称" label-width="100px">
                    <el-input v-model="editChapterGroup.chapter.chapterName" placeholder="请输入章节名称"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="editChapterSubmit">确认修改</el-button>
            </div>
        </el-dialog>

        <!-- 编辑小节 -->
         <el-dialog title="编辑小节" :visible.sync="editSubsectionGroup.show" width="40%">
            <el-form :model="editSubsectionGroup.subsection">
                <el-form-item label="小节名称" label-width="120px">
                    <el-input v-model="editSubsectionGroup.subsection.subsectionName" placeholder="请输入小节名称"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="editSubsectionSubmit">确认修改</el-button>
            </div>
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
            vague:true,
            addChapterGroup:{
                show:false,
                chapter:{
                    chapterTitle:'',
                    chapterName:'',
                    subsections:[],
                    courseId:''
                }
            },
            editCourseGroup:{
                show:false,
                course:{
                    courseName:'',
                    courseImage:'',
                    courseDescribe:'',
                    courseId:''
                }
            },
            addSubsectionGroup:{
                show:false,
                subsection:{
                    subsectionName:'',
                    chapterId:''
                }
            },
            editChapterGroup:{
                show:false,
                chapter:{
                    chapterTitle:'',
                    chapterName:'',
                    chapterId:''
                }
            },
            editSubsectionGroup:{
                show:false,
                subsection:{
                    subsectionName:'',
                    subsectionId:''
                }
            }
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
        addChapter(row){
            this.addChapterGroup.chapter.courseId=row.courseId
            this.addChapterGroup.show=true
        },
        addChapterSubmit(){
            // 章节名称不能为空
            if(!this.addChapterGroup.chapter.chapterName){
                this.$message.error('章节名称不能为空')
                return
            }
            // 章节标题不能为空
            if(!this.addChapterGroup.chapter.chapterTitle){
                this.$message.error('章节标题不能为空')
                return
            }
            // 小节不能为空
            if(!this.addChapterGroup.chapter.subsections.length){
                this.$message.error('小节不能为空')
                return
            }else{
                for(let i=0;i<this.addChapterGroup.chapter.subsections.length;i++){
                    // 小节名称不能为空
                    if(!this.addChapterGroup.chapter.subsections[i].subsectionName){
                        this.$message.error('第'+(i+1)+'小节名称不能为空')
                        return
                    }   
                }
            }
            api.insertChapter(this.addChapterGroup.chapter).then(res=>{
                this.addChapterGroup.show=false
                this.nextPage(this.page)
                // 清空表单
                this.addChapterGroup.chapter.chapterTitle=''
                this.addChapterGroup.chapter.chapterName=''
                this.addChapterGroup.chapter.subsections=[]
            })
        },
        editCourse(row){
            this.editCourseGroup.course.courseId=row.courseId
            this.editCourseGroup.course.courseName=row.courseName
            this.editCourseGroup.course.courseImage=row.courseImage
            this.editCourseGroup.course.courseDescribe=row.courseDescribe
            this.editCourseGroup.show=true
        },
        editCourseSubmit(){
            // 课程名称不能为空
            if(!this.editCourseGroup.course.courseName){
                this.$message.error('课程名称不能为空')
                return
            }
            // 课程图片不能为空
            if(!this.editCourseGroup.course.courseImage){
                this.$message.error('课程图片不能为空')
                return
            }
            // 课程描述不能为空
            if(!this.editCourseGroup.course.courseDescribe){
                this.$message.error('课程描述不能为空')
                return
            }
            api.updateCourse(this.editCourseGroup.course).then(res=>{
                this.editCourseGroup.show=false
                this.nextPage(this.page)
            })
        },
        deleteCourse(row){
            // 删除之前提示一下，不然点错了
            this.$confirm('确认删除该课程吗？').then(() => {
                api.deleteCourseByCourseId(row.courseId).then(res=>{
                    this.nextPage(this.page)
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                })
            })
        },
        editChapter(row){
            this.editChapterGroup.chapter.chapterId=row.chapterId
            this.editChapterGroup.chapter.chapterTitle=row.chapterTitle
            this.editChapterGroup.chapter.chapterName=row.chapterName
            this.editChapterGroup.show=true
        },
        editChapterSubmit(){
            // 章节名称不能为空
            if(!this.editChapterGroup.chapter.chapterName){
                this.$message.error('章节名称不能为空')
                return
            }
            // 章节标题不能为空
            if(!this.editChapterGroup.chapter.chapterTitle){
                this.$message.error('章节标题不能为空')
                return
            }
            api.updateChapter(this.editChapterGroup.chapter).then(res=>{
                this.editChapterGroup.show=false
                this.nextPage(this.page)
            })
        },
        addSubsection(row){
            this.addSubsectionGroup.subsection.chapterId=row.chapterId
            this.addSubsectionGroup.show=true
        },
        addSubsectionSubmit(){
            // 小节名称不能为空
            if(!this.addSubsectionGroup.subsection.subsectionName){
                this.$message.error('小节名称不能为空')
                return
            }
            api.insertSubsection(this.addSubsectionGroup.subsection).then(res=>{
                this.addSubsectionGroup.show=false
                this.nextPage(this.page)
                // 清空表单
                this.addSubsectionGroup.subsection.subsectionName=''
            })
        },
        deleteChapter(row){
            // 删除之前提示一下，不然点错了
            this.$confirm('确认删除该章节吗？').then(() => {
                api.deleteChapterByChapterId(row.chapterId).then(res=>{
                    this.nextPage(this.page)
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                })
            })
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
            for(let i=0;i<this.course.chapters.length;i++){
                // 章节中的标题格式需要正确
                if(!/^第\d章$/.test(this.course.chapters[i].chapterTitle)){
                    this.$message.error('第'+(i+1)+'章节标题格式错误，请按照“第*章”格式填写')
                    return
                }
                // 章节名称不能为空
                if(!this.course.chapters[i].chapterName){
                    this.$message.error('第'+(i+1)+'章节名称不能为空')
                    return
                }
                // 小节不能为空
                if(!this.course.chapters[i].subsections.length){
                    this.$message.error('第'+(i+1)+'章节小节不能为空')
                    return
                }else{
                    for(let j=0;j<this.course.chapters[i].subsections.length;j++){
                        // 小节名称不能为空
                        if(!this.course.chapters[i].subsections[j].subsectionName){
                            this.$message.error('第'+(i+1)+'章节第'+(j+1)+'小节名称不能为空')
                            return
                        }   
                    }
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
        },
        editSubsection(row){
            this.editSubsectionGroup.subsection.subsectionId=row.subsectionId
            this.editSubsectionGroup.subsection.subsectionName=row.subsectionName
            this.editSubsectionGroup.show=true
        },
        editSubsectionSubmit(){
            // 小节名称不能为空
            if(!this.editSubsectionGroup.subsection.subsectionName){
                this.$message.error('小节名称不能为空')
                return
            }
            api.updateSubsection(this.editSubsectionGroup.subsection).then(res=>{
                this.editSubsectionGroup.show=false
                this.nextPage(this.page)
            })
        },
        deleteSubsection(row){
            // 删除之前提示一下，不然点错了
            this.$confirm('确认删除该小节吗？').then(() => {
                api.deleteSubsectionBySubsectionId(row.subsectionId).then(res=>{
                    this.nextPage(this.page)
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                })
            })            
        }
    },
    mounted(){
        this.nextPage(1)
    }
}
</script>