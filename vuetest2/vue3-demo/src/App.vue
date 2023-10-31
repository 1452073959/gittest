<!-- <script>
export default{
  setup(){
    //获取不到this
      console.log("最早执行",this)

      const a="<h1>这是一个";
      const b=()=>{
        console.log('这是一个函数')    
      };
      return{
        a,b
      }
  }
  ,beforeCreate(){
    console.log(456,this)
  }
}
</script> -->

<script setup>
import {ref,computed} from 'vue'
import test from '@/components/test.vue'
let c= ref(100);
const d=(e)=>{
  list.value.push(c.value)
  console.log(e,list)
  console.log(c.value)
  console.log(list_computed.value)
        c.value+=c.value
      };
const a="打算打哒哒"
const b=()=>{
        console.log('这是一个函数')    
      };

const list=ref([1,2,3,4,5,6]);
const emit_ddd=(msg)=>{
  list.value.push(msg)
  console.log(msg)
}
const list_computed=computed(()=>{
  return list.value.filter(item=>item>2);
})

</script>
<template>
<div>
  {{ a }}
  {{ c }}
  <button @click="b">按钮</button>
  <button @click="d(10)">按钮</button>
  <test car="测试父传子" :test2=list @emit1="emit_ddd"></test>
</div>
</template>