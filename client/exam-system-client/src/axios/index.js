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
    if(resp.data.code==200){
        Message.success(resp.data.msg)
    }else{
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
    }
}
