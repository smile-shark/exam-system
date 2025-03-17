<template>
    <div>
        <el-row>
            <el-col :span="13" style="padding:10px;">
                <el-row>
                    <el-col :span="24">
                        <el-form style="display: flex;align-items: center;justify-content: center;" label-width="20px">
                            <el-form-item>
                                <el-date-picker
                                v-model="fromDateToDate"
                                type="datetimerange"
                                :picker-options="pickerOptions"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期">
                                </el-date-picker>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="searchStudentScores">查询</el-button>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="exportToExcel" :disabled="studentScores.length < 1">导出</el-button>
                            </el-form-item>
                        </el-form>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-table :data="studentScores" style="width: 100%" v-if="studentScores[0]" id="table-out">
                            <el-table-column label="学生名称" fixed="left">
                                <template slot-scope="scope">
                                    {{ scope.row.studentName }}
                                </template>
                            </el-table-column>
                            <el-table-column  
                            :label="studentScores[0].examPaperAllocations[index].examPaperRelease.examPaper.examPaperTitle" 
                            v-for="(examPaperAllocation,index) in studentScores[0].examPaperAllocations" :key="index">
                                <template slot-scope="scope">
                                    {{ scope.row.examPaperAllocations[index].score?scope.row.examPaperAllocations[index].score.score:'缺考' }}
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-col>
                </el-row>
            </el-col>
            <el-col :span="11" style="padding:10px;">
                <h2>学生列表</h2>
                <!-- 学生列表 -->
                <el-table :data="students" style="width: 100%" @selection-change="handleSelectionChange"
                ref="table">
                <el-table-column type="selection" width="55"/>
                <el-table-column label="学生ID" prop="studentId" with="300"/>
                <el-table-column prop="studentName" label="学生姓名" width="120"/>
                <el-table-column label="教师" width="120">
                    <template slot-scope="scope">
                        {{ scope.row.administrator.administratorName }}
                    </template>
                </el-table-column>
                </el-table>    
                <el-button @click="toggleSelection()" style="margin: 20px;">取消选择</el-button>
            </el-col>
        </el-row>
    </div>
</template>
<script>
import api from '@/axios/index'
import FileSaver from 'file-saver'
import * as XLSX from 'xlsx'
export default{
    data(){
        return{
            students:[],
            studentsSelected:[],
            fromDateToDate:[],
            pickerOptions: {
            shortcuts: [
            {
                text: '最近一天',
                onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24);
                    picker.$emit('pick', [start, end]);
                }
            },
            {
            text: '最近一周',
                onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                    picker.$emit('pick', [start, end]);
                }
            }, {
                text: '最近一个月',
                onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                picker.$emit('pick', [start, end]);
                }
            }, {
                text: '最近三个月',
                onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                picker.$emit('pick', [start, end]);
                }
            }]},
            studentScores:[
            ]
        }
    },
    methods:{
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
            this.studentsSelected = [ ];
        },
        searchStudentScores(){
            // 开始时间和结束时间不能为空
            if(!this.fromDateToDate[0] || !this.fromDateToDate[1]){
                this.$message.error("开始时间和结束时间不能为空")
                return
            }
            // 开始时间不能大于结束时间
            if(this.fromDateToDate[0] > this.fromDateToDate[1]){
                this.$message.error("开始时间不能大于结束时间")
                return
            }
            // 至少需要选择一个学生
            if(this.studentsSelected.length < 1){
                this.$message.error("至少需要选择一个学生")
                return
            }
            api.getStudentScoreListByStudentsNew(this.studentsSelected,this.fromDateToDate[0],this.fromDateToDate[1]).then(res=>{
                this.studentScores = res.data
                console.log(this.studentScores[0].examPaperAllocations[0].examPaperRelease.examPaper.examPaperTitle)
            })
        },
        exportToExcel() {
            // 检查数据是否存在
            if (!this.studentScores || this.studentScores.length === 0) {
                this.$message.warning("没有数据可以导出");
                return;
            }

            // 1. 生成表头
            const header = ["学生名称"];
            if (this.studentScores[0].examPaperAllocations) {
                this.studentScores[0].examPaperAllocations.forEach((allocation) => {
                header.push(allocation.examPaperRelease.examPaper.examPaperTitle);
                });
            }

            // 2. 生成表格数据
            const data = [header]; // 第一行为表头
            this.studentScores.forEach((student) => {
                const row = [student.studentName];
                if (student.examPaperAllocations) {
                student.examPaperAllocations.forEach((allocation) => {
                    row.push(allocation.score ? allocation.score.score : "缺考");
                });
                }
                data.push(row);
            });

            // 3. 创建工作表
            const worksheet = XLSX.utils.aoa_to_sheet(data);

            // 4. 创建工作簿
            const workbook = XLSX.utils.book_new();
            XLSX.utils.book_append_sheet(workbook, worksheet, "学生成绩");

            // 5. 生成 Excel 文件并下载
            const excelBuffer = XLSX.write(workbook, {
                bookType: "xlsx",
                type: "array",
            });
            const blob = new Blob([excelBuffer], {
                type: "application/octet-stream",
            });
            FileSaver.saveAs(blob, "学生成绩.xlsx");
        }
    },
    mounted(){
        api.getAllStudent().then(res=>{
            this.students = res.data
        })
    }
}
</script>