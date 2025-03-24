import axios from "axios";
import path from "./path";
import { Message } from "element-ui";
import router from "@/router";
import CryptoJS from "crypto-js";
import {fastJsonParse} from 'fast-json-parse'

const AESKEY='www.examSystem.com'

const myAxios=axios.create({
  timeout:6000
})
// 请求拦截器
myAxios.interceptors.request.use(config => {
  // 动态获取 localStorage 中的 token
  const adminToken = localStorage.getItem("adminToken");
  const studentToken = localStorage.getItem("studentToken");

  // 设置请求头
  config.headers["AdminToken"] = adminToken || "";
  config.headers["StudentToken"] = studentToken || "";

  return config;
}, error => {
  return Promise.reject(error);
});
//获取数据之前
myAxios.interceptors.response.use(
  resp => {
    if (resp.data.code == 200 && resp.data.msg) {
      Message.success(resp.data.msg);
    } else if (resp.data.msg) {
      Message.error(resp.data.msg);
      if (resp.data.msg == '请重新登录') {
        setTimeout(() => {
          router.push('/login');
        }, 1000);
      }
    }

    // 解析 JSONPath
    const resolveJsonPath = (data, path) => {
      try {
        // 去掉 $ 和 .，然后分割路径
        const keys = path.replace(/^\$\.?/, '').split('.');
        let result = data;
        for (const key of keys) {
          // 处理数组索引，例如 list[0]
          if (key.includes('[')) {
            const arrayKey = key.split('[')[0];
            const index = key.match(/\[(\d+)\]/)[1];
            result = result[arrayKey][index];
          } else {
            result = result[key];
          }
        }
        return result;
      } catch (error) {
        console.warn(`无法解析 $ref: ${path}`, error);
        return null;
      }
    };
    // 处理 URL 替换
    if (!JSON.stringify(resp.data).includes('ai.cqzuxia.com')) {
      resp.data = JSON.parse(
          JSON.stringify(resp.data)
              .replace(/\/oss\/api\/ImageViewer\//g, '<url id="cvggi4oh8njkpu5d9k5g" type="url" status="failed" title="" wc="0">https://ai.cqzuxia.com/oss/api/ImageViewer/</url> ')
      );
  }
    return resp.data;
  },
  error => {
    console.log(error);
    return Promise.reject(error);
  }
);

// 接口集合
export default {
    getAdministratorNameList(){
       return myAxios.post(path.getAdministratorList)
    },
    studentSignUp(account,password,name,adminId){
      return myAxios.post(path.studentSignUp,{
        studentAccount:account,
        studentPassword:CryptoJS.AES.encrypt(password,AESKEY).toString(),
        studentName:name,
        administratorId:adminId
      })
    },
    studentLogin(account,password){
      return myAxios.post(path.studentLogin,{
        studentAccount:account,
        studentPassword:CryptoJS.AES.encrypt(password,AESKEY).toString()
      })
    },
    adminLogin(account,password){
      return myAxios.post(path.adminLogin,{
        administratorAccount:account,
        administratorPassword:CryptoJS.AES.encrypt(password,AESKEY).toString()
      })
    },
    getStudentCount(){
      return myAxios.post(path.studentCount)
    },
    getCourseCount(){
      return myAxios.post(path.courseCount)
    },
    getQuestionCount(){
      return myAxios.post(path.questionCount)
    },
    getExamPaperCount(){
      return myAxios.post(path.examPaperCount)
    },
    getCourseListNameAndId(){
      return myAxios.post(path.courseListNameAndId)
    },
    getChapterListNameTitleAndId(courseId){
      return myAxios.post(path.chapterListNameTitleAndId,{
        courseId:courseId
      })
    },
    getSubsectionListNameAndId(chapterId){
      return myAxios.post(path.subsectionListNameAndId,{
        chapterId:chapterId
      })  
    },
    getQuestionList(page,size,courseId,chapterId,subsectionId,questionTypes,questionContent,vague){
      return myAxios.post(path.questionListByParams,{
        vague:vague,
        page:page,
        size:size,
        courseId:courseId,
        chapterId:chapterId,
        subsectionId:subsectionId,
        questionTypes:questionTypes,
        questionContent:questionContent
      })
    },
    getSubsectionInfo(subsectionId){
      return myAxios.post(path.subsectionInfo,{
        subsectionId:subsectionId
      })
    },
    getAnswerInfo(questionId){
      return myAxios.post(path.answerInfo,{
        questionId:questionId
      })
    },
    chapterListNameTitleAndIdInCourseId(courseIds){
      return myAxios.post(path.chapterListNameTitleAndIdInCourseId,{
        courseIds:courseIds
      })
    },
    subsectionListNameAndIdInChapterId(chapterIds){
      return myAxios.post(path.subsectionListNameAndIdInChapterId,{
        chapterIds:chapterIds
      })
    },
    pullQuestionListByParams(courseIds,chapterIds,subsectionIds,questionType0Count,questionType1Count,questionType2Count){
      return myAxios.post(path.pullQuestionListByParams,{
        courseIds:courseIds,
        chapterIds:chapterIds,
        subsectionIds:subsectionIds,
        questionType0Count:questionType0Count,
        questionType1Count:questionType1Count,
        questionType2Count:questionType2Count
      })
    },
    questionListByParamsNotQuestionId(courseIds,chapterIds,subsectionIds,questionType,questionId){
      return myAxios.post(path.questionListByParamsNotQuestionId,{
        courseIds:courseIds,
        chapterIds:chapterIds,
        subsectionIds:subsectionIds,
        questionType:questionType,
        questionId:questionId
      })
    },
    createExamPaper(administratorId,examPaperTitle,questions,totalScore){
      return myAxios.post(path.createExamPaper,{
        administratorId:administratorId,
        examPaperTitle:examPaperTitle,
        questions:questions,
        totalScore:totalScore
      })
    },
    getExamPaperIdTitle(){
      return myAxios.post(path.getExamPaperIdTitle)
    },
    getAllStudent(){
      return myAxios.post(path.getAllStudent)
    },
    releaseExamPaper(examPaperId,administratorId,examStartTime,examEndTime,notes,students,duration){
      return myAxios.post(path.releaseExamPaper,{
        examPaperId:examPaperId,
        administratorId:administratorId,
        examStartTime:examStartTime,
        examEndTime:examEndTime,
        notes:notes,
        students:students,
        duration:duration
      })
    },
    getExamPaperListByParams(administratorIds,examPaperStates,examPaperTitle,page,size,vague){
      return myAxios.post(path.getExamPaperListByParams,{
        administratorIds:administratorIds,
        examPaperStates:examPaperStates,
        examPaperTitle:examPaperTitle,
        page:page,
        size:size,
        vague:vague
      })
    },
    getExamPaperCountByState(studentId,examPaperAllocationState){
      return myAxios.post(path.getExamPaperCountByState,{
        studentId:studentId,
        examPaperAllocationState:examPaperAllocationState
      })
    },
    getAnswerQuestionCountByStudentId(studentId){
      return myAxios.post(path.getAnswerQuestionCountByStudentId,{
        studentId:studentId
      })
    },
    getWoringQuestionCountByStudentId(studentId){
      return myAxios.post(path.getWoringQuestionCountByStudentId,{
        studentId:studentId
      })
    },
    getLastExamScoreByStudentId(studentId){
      return myAxios.post(path.getLastExamScoreByStudentId,{
        studentId:studentId
      })
    },
    getAvgScoreByStudentId(studentId){
      return myAxios.post(path.getAvgScoreByStudentId,{
        studentId:studentId
      })
    },
    getExamCountByStudentId(studentId){
      return myAxios.post(path.getExamCountByStudentId,{
        studentId:studentId
      })
    },
    getExamPaperListByStudentIdAndState(studentId,examPaperAllocationState,page,size){
      return myAxios.post(path.getExamPaperListByStudentIdAndState,{
        studentId:studentId,
        examPaperAllocationState:examPaperAllocationState,
        page:page,
        size:size
      })
    },
    getExamPaperByExamPaperAllocationId(examPaperAllocationId){
      return myAxios.post(path.getExamPaperByExamPaperAllocationId,{
        examPaperAllocationId:examPaperAllocationId
      })
    },
    submitAnswers(examPaperAllocation){
      return myAxios.post(path.submitAnswers,{
        examPaperAllocation:examPaperAllocation
      })
    },
    getExamPaperListFinish(studentId,page,size){
      return myAxios.post(path.getExamPaperListFinish,{
        studentId:studentId,
        page:page,
        size:size
      })
    },
    getReviewExamPaperByExamPaperAllocationId(examPaperAllocationId){
      return myAxios.post(path.getReviewExamPaperByExamPaperAllocationId,{
        examPaperAllocationId:examPaperAllocationId
      })
    },
    getStudentScoreListByStudents(students,examStartTime,examEndTime){
      return myAxios.post(path.getStudentScoreListByStudents,{
        students:students,
        examStartTime:examStartTime,
        examEndTime:examEndTime
      })
    },
    getStudentScoreListByStudentsNew(students,examStartTime,examEndTime){
      return myAxios.post(path.getStudentScoreListByStudentsNew,{
        students:students,
        examStartTime:examStartTime,
        examEndTime:examEndTime
      })
    },
    insertQuestions(questions,subsectionId){
      return myAxios.post(path.insertQuestions,{
        questions:questions,
        subsectionId:subsectionId
      })
    },
    getAllCourses(page,size,courseName,vague){
      return myAxios.post(path.getAllCourses,{
        page:page,
        size:size,
        courseName:courseName,
        vague:vague
      })
    },
    insertCourse(course){
      return myAxios.post(path.insertCourse,{
        course:course
      })
    },
    deleteQuestion(questionId){
      return myAxios.post(path.deleteQuestion,{
        questionId:questionId
      })
    },
    updateQuestion(question){
      return myAxios.post(path.updateQuestion,{
        question:{
          questionId:question.questionId,
          questionName:question.questionName,
          questionType:question.questionType,
          answers:question.answers
        }
      })
    },
    insertChapter(chapter){
      return myAxios.post(path.insertChapter,{
        chapter:chapter
      })
    },
    updateCourse(course){
      return myAxios.post(path.updateCourse,{
        course:course
      })
    },
    deleteCourseByCourseId(courseId){
      return myAxios.post(path.deleteCourseByCourseId,{
        courseId:courseId
      })
    },
    insertSubsection(subsection){
      return myAxios.post(path.insertSubsection,{
        subsection:subsection
      })
    },
    updateChapter(chapter){
      return myAxios.post(path.updateChapter,{
        chapter:chapter
      })
    },
    deleteChapterByChapterId(chapterId){
      return myAxios.post(path.deleteChapterByChapterId,{
        chapterId:chapterId
      })
    },
    updateSubsection(subsection){
      return myAxios.post(path.updateSubsection,{
        subsection:subsection
      })
    },
    deleteSubsectionBySubsectionId(subsectionId){
      return myAxios.post(path.deleteSubsectionBySubsectionId,{
        subsectionId:subsectionId
      })
    },
    getStudentListVague(student,page,size,vague){
      return myAxios.post(path.getStudentListVague,{
        student:student,
        page:page,
        size:size,
        vague:vague
      })
    },
    deleteStudentByStudentId(studentId){
      return myAxios.post(path.deleteStudentByStudentId,{
        studentId:studentId
      })
    },
    getMistakesCollectionByStudentId(studentId,page,size){
      return myAxios.post(path.getMistakesCollectionByStudentId,{
        studentId:studentId,
        page:page,
        size:size
      })
    }
}
