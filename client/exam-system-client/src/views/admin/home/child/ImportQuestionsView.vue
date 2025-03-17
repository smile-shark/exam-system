<template>
    <div style="padding:10px;">
        <el-form label-width="20px" style="display: flex;align-items: center;justify-content: left;">
            <el-form-item>
                <el-upload
                    class="upload-demo"
                    :on-change="handleFileUpload"
                    :before-remove="beforeRemove"
                    multiple
                    :limit="1"
                    :on-exceed="handleExceed"
                    :file-list="fileList"
                    accept=".xlsx, .xls"
                    action="javascript:void(0)"
                    :auto-upload="false"
                >
                    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                    <div slot="tip" class="el-upload__tip">只能上传 xlsx/xls 文件</div>
                </el-upload>
            </el-form-item>
            <el-form-item>
                <el-select v-model="courseId" @change="getChapterList" filterable clearable  placeholder="课程（可选择）">
                    <el-option
                    v-for="item in courseList"
                    :key="item.courseId"
                    :label="item.courseName"
                    :value="item.courseId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-select v-model="chapterId" @change="getSelectionList" filterable clearable  placeholder="章节（可选择）">
                    <el-option
                    v-for="item in chapterList"
                    :key="item.chapterId"
                    :label="item.chapterTitle+' '+item.chapterName"
                    :value="item.chapterId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-select v-model="subsectionId" filterable clearable  placeholder="小节（可选择）">
                    <el-option
                    v-for="item in subsectionList"
                    :key="item.subsectionId"
                    :label="item.subsectionName"
                    :value="item.subsectionId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="success" @click.prevent="submintQuestionsToServer">上传到服务器</el-button>
            </el-form-item>
            <el-form-item>
                <span style="color: gray;">execl格式参考</span>
                <div class="demo-image__preview">
                    <el-image 
                        style="width: 200px; height: 40px"
                        :src="require('@/assets/updateQueestionRefer.png')" 
                        :preview-src-list="[require('@/assets/updateQueestionRefer.png')]">
                    </el-image>
                </div>
            </el-form-item>
        </el-form>
    
      <el-table
        :data="questions"
        border
        style="width: 100%; margin-top: 20px;"
      >
        <el-table-column
          v-for="(column, index) in columns"
          :key="index"
          :prop="column.prop"
          :label="column.label"
          align="center"
        >
          <template #default="{ row }">
            <div v-if="column.prop === 'questionType'">
              <el-select v-model="row[column.prop]" placeholder="请选择问题类型">
                <el-option label="单选题" :value="0" />
                <el-option label="多选题" :value="1" />
                <el-option label="判断题" :value="2" />
              </el-select>
            </div>
            <div v-else>
              <el-input v-model="row[column.prop]" placeholder="请输入内容" />
            </div>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template #default="{ row }">
            <el-button type="primary" size="mini" @click="showAnswers(row)">编辑答案</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <el-dialog
        :visible.sync="dialogVisible"
        title="编辑答案"
        width="50%"
      >
        <el-table :data="selectedAnswers" border>
          <el-table-column prop="answer" label="答案" align="center">
            <template #default="{ row }">
              <el-input v-model="row.answer" placeholder="请输入答案" />
            </template>
          </el-table-column>
          <el-table-column label="是否正确" align="center">
            <template #default="{ row }">
              <el-switch v-model="row.isTrue" />
            </template>
          </el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveAnswers">保存</el-button>
        </span>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import XLSX from "xlsx";
  import api from "@/axios/index"
  import { MessageBox, Message } from "element-ui";
  
  export default {
    data() {
      return {
        questions: [],
        columns: [
          { label: "问题", prop: "question" },
          { label: "问题类型", prop: "questionType" }
        ],
        dialogVisible: false,
        selectedAnswers: [],
        fileList: [],
        courseId:'',
        courseList:[],
        chapterId:'',
        chapterList:[],
        subsectionId:'',
        subsectionList:[],
        subminting:false,
      };
    },
    methods: {
      handleFileUpload(file) {
        this.fileList.push(file)
        const reader = new FileReader();
        reader.onload = (e) => {
          const data = e.target.result;
          const workbook = XLSX.read(data, { type: "binary" });
          const firstSheetName = workbook.SheetNames[0];
          const worksheet = workbook.Sheets[firstSheetName];
          const jsonData = XLSX.utils.sheet_to_json(worksheet);
  
          this.parseExcelData(jsonData);
          console.log(this.questions)
        };
        reader.readAsBinaryString(file.raw);
      },
      parseExcelData(jsonData) {
        this.questions = jsonData.map((row) => {
            let questionType=0;
            switch(row.questionType){
                case "单选题":
                    questionType=0;
                    break;
                case "多选题":
                    questionType=1;
                    break;
                case "判断题":
                    questionType=2;
                    break;
                default:
                    questionType=0;
                    break;
            }
            let answers = []
            for (const key in row) {
                if (row.hasOwnProperty(key)) {
                    // 匹配 answer 和 isTrue 属性
                    const matchAnswer = key.match(/^answer(_\d+)?$/);
                    const matchIsTrue = key.match(/^isTrue(_\d+)?$/);

                    if (matchAnswer) {
                        const index = matchAnswer[1] ? parseInt(matchAnswer[1].substring(1), 10) : 0;
                        const answer = row[key];
                        const isTrueKey = `isTrue${matchAnswer[1] || ""}`;
                        const isTrue = row[isTrueKey] == "是";
                        answers[index] = { answer, isTrue };
                    }
                }
            }
            return {
                question:row.question,
                questionType:questionType,
                answers: answers
            }
        });
      },
      showAnswers(row) {
        this.selectedAnswers = row.answers;
        this.dialogVisible = true;
      },
      saveAnswers() {
        this.dialogVisible = false;
        Message.success("答案已保存");
      },
      beforeRemove(file, fileList) {
        return MessageBox.confirm(`确定移除 ${file.name}？`);
      },
      handleExceed(files, fileList) {
        Message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
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
        submintQuestionsToServer(){
            // subsectionId不能为空
            if(!this.subsectionId){
                Message.error("请选择小节")
                return
            }
            // 校验问题是否为空
            if(this.questions.length==0){
                Message.error("请上传文件")
                return
            }
            if(this.subminting){
                Message.error("正在上传，请勿重复操作")
            }else{
                subminting=true
                api.insertQuestions(this.questions,this.subsectionId).then(res=>{
                    this.subminting=false
                    // 上传成功后清空数据
                    this.questions=[]
                    this.fileList=[]
                })
            }
        },
    },
    mounted(){
        api.getCourseListNameAndId().then(res=>{
            this.courseList = res.data
        })
    }
  };
  </script>
  
  <style scoped>
  /* 添加一些样式 */
  </style>