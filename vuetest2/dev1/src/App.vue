<template>
	<div class="app">
		<baseCount></baseCount>
		<Headtest :title="proptitle" :proptitle2='proptitle2' @emitmessage="handleMessage2"></Headtest>
		{{proptitle}}
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
				proptitle: '测试父传子1',
				proptitle2: '测试父传子2',
				emitmessage2: ''
			}
		},
		methods: {
			handleMessage2(message) {
				console.log(message);
				this.proptitle=message
			}
		},
		components: {
			baseCount,
			Headtest,
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