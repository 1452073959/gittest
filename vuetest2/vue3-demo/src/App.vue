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
let c= ref(100);//简单类型
let fuza= ref([
	1,
	2
]);
const d=(e)=>{
	money.value+=100
  list.value.push(c.value)
  // console.log(e,list)
  // console.log(c.value)
  // console.log(list_computed.value)
        c.value+=c.value
      };
const a="父组件的参数"
const b=()=>{
        console.log('这是一个函数')    
      };

const list=ref([1,2,3,4,5,6]);
const emit_ddd=(msg)=>{
  list.value.push(msg)
  console.log(msg)
}
const emit_ddd2=(msg)=>{
  // list.value.push(msg)
  // console.log(msg)
  money.value=msg
}

const list_computed=computed(()=>{
  return list.value.filter(item=>item>2);
})

const money= ref(0)

</script>
<template>
<div>
  {{ a }}
  {{ c }}
  {{ fuza }}
  {{ list }}
<p>
	父的钱: {{money}}
</p> 
  <button @click="b">按钮</button>
  <button @click="d(10)">父的钱+100</button>
  <test :car=a :test2=list @emit1="emit_ddd" :money=money  @huaqian="emit_ddd2"></test>
</div>
</template>