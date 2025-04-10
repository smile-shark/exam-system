<template>
    <div>
        <el-row>
            <el-col :span="24">
                <div class="search-container">
                    <el-select v-model="administratorIds" filterable clearable multiple placeholder="创建人（可选择）">
                        <el-option
                        v-for="item in administrators"
                        :key="item.administratorId"
                        :label="item.administratorName"
                        :value="item.administratorId">
                        </el-option>
                    </el-select>
                </div>
                <div class="search-container">
                    <el-select v-model="examPaperState" filterable clearable multiple   placeholder="试卷状态（可选择）">
                        <el-option
                        v-for="item in examPaperStates"
                        :key="item.examPaperStateType"
                        :label="item.examPaperStateName"
                        :value="item.examPaperStateType">
                        </el-option>
                    </el-select>
                </div>
                <div class="search-container">
                    <el-input v-model="examPaperTitle" placeholder="试卷标题"></el-input>
                </div>
                <div class="search-container">
                    <template>
                    <el-checkbox v-model="vague">模糊查询</el-checkbox>
                    </template>
                </div>
                <div class="search-container">
                    <el-button type="primary" @click="serachExamPaperList(1)">搜索</el-button>
                </div>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24" style="height: 75vh;">
                <template>
                <el-table
                :data="examPapers"
                style="width: 100%;"
                height="100%">
                <el-table-column
                    prop="examPaperId"
                    label="试卷ID"
                    width="300">
                </el-table-column>
                <el-table-column
                label="试卷标题"
                width="200">
                <template slot-scope="scope">
                    <div style="overflow-x: hidden; white-space: nowrap; text-overflow: ellipsis;" 
                    v-html="scope.row.examPaperTitle">
                    </div>
                </template>
                </el-table-column>
                <el-table-column label="创建时间">
                    <template slot-scope="scope">
                        <div>{{ longToDateTime(scope.row.examPaperCreateTime) }}</div>
                    </template>
                </el-table-column>
                <el-table-column prop="questionCount" label="问题数量"></el-table-column>
                <el-table-column prop="totalScore" label="试卷总分"></el-table-column>
                <el-table-column
                label="试卷状态">
                <template slot-scope="scope">
                    <span v-if="scope.row.examPaperState===0">正常</span>
                    <span v-if="scope.row.examPaperState===1">作废</span>
                </template>
                </el-table-column>
                <el-table-column label="创建人">
                    <template slot-scope="scope">
                        <div>{{ scope.row.administrator.administratorName }}</div>
                    </template>
                </el-table-column>
                <el-table-column
                label="操作"
                fixe="right">
                    <template slot-scope="scope">
                        <!-- <el-button type="text" @click="viewExamPaper(scope.row)">查看</el-button> -->
                        <el-button type="text" @click="editExamPaper(scope.row)" v-if="scope.row.examPaperState===0">作废</el-button>
                        <el-button type="text" @click="deleteExamPaper(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
                </el-table>
            </template>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24" style="display: flex;justify-content: center;align-items: center;">
                <el-pagination
                @current-change="serachExamPaperList"
                background
                :page-size="size"
                :hide-on-single-page="true"
                layout="prev, pager, next"
                :total="examPaperCount">
                </el-pagination>
            </el-col>
        </el-row>
    </div>
</template>
<script>
import api from '@/axios/index'
export default{
    data(){
        return{
            examPaperTitle:'',
            vague:true,
            administratorIds:[],
            examPaperState:[],
            examPaperStates:[
                {
                    examPaperStateType:0,
                    examPaperStateName:'正常'
                },
                {
                    examPaperStateType:1,
                    examPaperStateName:'作废'
                }
            ],
            page:1,
            size:10,
            examPaperCount:0,
            examPapers:[],
            administrators:[]
        }
    },
    methods:{
        serachExamPaperList(page){
            if(page){
                this.page = page;
            }else{
                this.page = 1;
            }
            api.getExamPaperListByParams(this.administratorIds,this.examPaperState,this.examPaperTitle,this.page,this.size,this.vague).then(res=>{
                if(res.msg=='获取成功'){    
                    this.examPapers = res.data.list;
                    this.examPaperCount = res.total;
                }
            })
        },
        longToDateTime(times){
            const date = new Date(times);
            const year = date.getFullYear();
            const month = (date.getMonth() + 1).toString().padStart(2, '0');
            const day = date.getDate().toString().padStart(2, '0');
            const hours = date.getHours().toString().padStart(2, '0');
            const minutes = date.getMinutes().toString().padStart(2, '0');
            const seconds = date.getSeconds().toString().padStart(2, '0');
            return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        },
        editExamPaper(row){
            api.updateExamPaperToCancel(row.examPaperId).then(res=>{
                if(res.msg=='作废成功'){
                    this.$message.success(res.msg)
                    this.serachExamPaperList(1);
                }else{
                    this.$message.error(res.msg);
                }
            })  
        },
        deleteExamPaper(row){
            api.deleteExamPaperById(row.examPaperId).then(res=>{
                if(res.msg=='删除成功'){
                    this.$message.success(res.msg)
                    this.serachExamPaperList(1);
                }else{
                    this.$message.error(res.msg);
                }
            })  
        }
    },
    mounted(){
        api.getAdministratorNameList().then(res=>{
            this.administrators = res.data
        })
    }
}
</script>
<style scoped>
.search-container{
    display: inline-block;
    padding:10px;
}
</style>