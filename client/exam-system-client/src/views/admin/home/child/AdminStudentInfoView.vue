<template>
    <div>
        <el-row>
            <el-col :span="24">
                <el-form label-width="20px" :data="student" style="display: flex;justify-content: center;align-items: center;">
                    <el-form-item>
                        <h2>学生信息列表</h2>
                    </el-form-item>
                    <el-form-item label="">
                        <el-input v-model="student.studentName" placeholder="请输入名称"></el-input>    
                    </el-form-item>
                    <el-form-item label="">
                        <el-input v-model="student.studentAccount" placeholder="请输入账号"></el-input>    
                    </el-form-item>
                    <el-form-item>
                        <el-checkbox v-model="vague">模糊查询</el-checkbox>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="search(1)">查询</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="createStudentAccount">创建账号</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24" style="padding:20px">
                <el-table :data="students" width="100%" height="550">
                    <el-table-column label="学生ID" prop="studentId" width="300"></el-table-column>
                    <el-table-column label="学生名称" prop="studentName"></el-table-column>
                    <el-table-column label="学生账号" prop="studentAccount"></el-table-column>
                    <el-table-column label="学生密码">
                        <template slot-scope="scope">
                            <el-input :type="notShowPassword ? 'password':'text'" v-model="scope.row.studentPassword" disabled></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column>
                        <template slot-scope="scope">
                            <el-button type="warning" @click="notShowPassword=!notShowPassword" size="small">显示密码</el-button>
                            <el-button type="danger" @click="deleteStudent(scope.row.studentId)" size="small">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24" style="display: flex;justify-content: center;align-items: center;">
                <el-pagination
                @current-change="search"
                :page-size="size"
                background
                layout="prev, pager, next"
                :total="total">
                </el-pagination>
            </el-col>
        </el-row>
    </div>
</template>
<script>
import api from '@/axios/index'
export default {
    data(){
        return{
            student:{
                studentName:'',
                studentAccount:'',
            },
            students:[],
            vague:true,
            page:1,
            size:10,
            total:0,
            notShowPassword:true
        }
    },
    methods:{
        search(page){
            if(page){
                this.page = page;
            }else{
                page = 1;
            }
            api.getStudentListVague(this.student,this.page,this.size,this.vague).then(res=>{
                this.students = res.data.list;
                this.total = res.data.total;
            })
        },
        deleteStudent(row){
            // 删除之前提示一下，不然点错了
            this.$confirm('确认删除该学生的账户吗？').then(() => {
                api.deleteStudentByStudentId(row).then(_=>{
                    this.search(this.page);
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
        this.search(1);      
    }
}
</script>