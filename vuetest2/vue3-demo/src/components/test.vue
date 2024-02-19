<script setup>
	const props = defineProps({
		car: String,
		test2: Object,
		money: Number
	})
	const emit = defineEmits([
		'emit1',
		'huaqian'
	])
	const sendemit = () => {
		emit('emit1', '测试子传父的参数')
	}
	const huaqian=()=>{
		let newmoney=props.money;
		 newmoney-=100
		emit('huaqian', newmoney)
	}
	
	console.log(props)
</script>
<template>
	<div class="a">
		<h1>我是子组件</h1>
		{{ car }}
		{{ test2 }}
		<h1>子的钱{{money}}</h1>
		<button @click="huaqian">花钱</button>
		<button @click="sendemit">子传父</button>
	</div>
</template>
<style scoped>
	.a {
		border: 1px red solid;
		width: 500px;
		height: 500px;
	}
</style>