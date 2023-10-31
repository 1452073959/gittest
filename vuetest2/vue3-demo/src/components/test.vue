<script setup>
    const props=defineProps({
            car:String,
            test2:Object
    })
    const emit=defineEmits([
        'emit1'
        ])
        const sendemit=()=>{
            emit('emit1','测试子传父')
        }
    console.log(props)
</script>
<template>
<div class="a">
    <h1>我是子组件</h1>
    {{ car }}
    {{ test2 }}
    <button @click="sendemit">子传父</button>
</div>
</template>
<style scoped>
.a{
    border: 1px red solid;
    width: 500px;
    height: 500px;
}
</style>