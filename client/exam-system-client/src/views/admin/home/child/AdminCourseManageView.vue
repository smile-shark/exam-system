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
                        <el-from v-for="(chapter,index) in course.chapters" :key="index" :data="chapter">
                            <el-form-item label="章节标题" label-width="100px">
                                <el-input v-model="chapter.chapterName" placeholder="请输入章节标题（第*章）"></el-input>
                            </el-form-item>
                            <el-form-item label="章节名称" label-width="100px">
                                <el-input v-model="chapter.chapterName" placeholder="请输入章节名称"></el-input>
                            </el-form-item>
                            <el-form-item label="添加小节">
                                <br>
                                <el-from v-for="(subsection,index) in chapter.subsections" :key="index" :data="subsection">
                                    <el-form-item label="小节名称" label-width="120px">
                                        <el-input v-model="subsection.subsectionName" placeholder="请输入小节名称"></el-input>
                                    </el-form-item> 
                                </el-from>
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
                        </el-from>
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
                        <el-button size="medium" type="success">
                            确认添加
                        </el-button>
                    </el-form-item>
                </el-form>
            </el-col>
            <el-col :span="18">

            </el-col>
        </el-row>
    </div>
</template>
<script>
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
            }
        }
    }
}
</script>