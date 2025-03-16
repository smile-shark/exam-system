<template>
    <div style="height: 100vh;display: flex;align-items: center;justify-content: center;">
        <el-result icon="success"  title="提交成功" :subTitle="'得分：'+ score +'分'">
        <template slot="extra">
            <el-button type="primary" size="medium" @click="$router.push('/home')">返回</el-button>
        </template>
        </el-result>    
    </div>
</template>
<script>
export default{
    data(){
        return{
            score: 0
        }
    },
    methods:{

    },
    mounted(){
        // 要将数据格式化为两位数的字符串
        if(localStorage.score){
            this.score = (Number(localStorage.score)).toFixed(2);
        }
    }
}
</script>