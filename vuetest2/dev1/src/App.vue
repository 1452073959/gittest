<template>
	<div class="app">
		<baseCount></baseCount>
		<Headtest :title.sync="proptitle" :proptitle2='proptitle2' @emitmessage="handleMessage2" ref="testref"></Headtest>
		<span>父组件</span>
		<p>{{proptitle}}</p>
		<HmButton></HmButton>
	</div>
	<!-- <div></div> -->
</template>

<script>
	import baseCount from './components/BaseCount'
	import Headtest from './components/Headtest.vue'
	export default {
		data() {
			return {
				proptitle: '测试父传子66622',
				proptitle2: '测试父传子2',
				emitmessage2: ''
			}
		},
		methods: {
			handleMessage2(message) {
				console.log(message);
				this.proptitle = "子传父内容:" + message
			}
		},
		components: {
			baseCount,
			Headtest,
		},
		mounted() {
			console.log(this.$refs.testref);
			// 可以开始操作dom了
		},
		//子传父的第二种方式
		// provide() {
		// 	return {
		// 		message: 'Hello from parent'
		// 	};
		// }
	}
</script>

<style>
</style>