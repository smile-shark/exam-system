import axios from "axios";
import path from "./path";
import { Message } from "element-ui";

const myAxios=axios.create({
  timeout:6000,
  headers:{
    token:localStorage.token 
  },
})

//获取数据之前
myAxios.interceptors.response.use(
  resp=>{
    if(resp.data.code==200&&resp.data.msg){
        Message.success(resp.data.msg)
    }else if(resp.data.msg){
        Message.error(resp.data.msg)
    }
    return resp.data
  },
  error=>{
    console.log(error)
    return Promise.reject(error)
  }
)

// 接口集合
export default {
    getAdministratorNameList(){
       return myAxios.post(path.getAdministratorList)
    },
    studentSignUp(account,password,name,adminId){
      return myAxios.post(path.studentSignUp,{
        studentAccount:account,
        studentPassword:password,
        studentName:name,
        administratorId:adminId
      })
    },
    studentLogin(account,password){
      return myAxios.post(path.studentLogin,{
        studentAccount:account,
        studentPassword:password
      })
    },
    adminLogin(account,password){
      return myAxios.post(path.adminLogin,{
        administratorAccount:account,
        administratorPassword:password
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
    }
}
