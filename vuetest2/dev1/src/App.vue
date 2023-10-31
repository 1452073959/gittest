<template>
	<div class="app">
		<baseCount></baseCount>
		<!-- //传参的测试 -->
		<Headtest :title.sync="proptitle" :proptitle2='proptitle2' @emitmessage="handleMessage2" ref="testref">测试插槽

		<template v-slot="testsolt">具名插槽</template> 
		<template v-slot="testsolt2">具名插槽2</template>
		</Headtest>
		<!-- <span>父组件</span> -->
		<!-- <p>{{proptitle}}</p> -->
		<!-- <HmButton></HmButton> -->
		<!-- <Directive></Directive> -->
		<!-- <Loadin></Loadin> -->
		
		<a href="#/find/测试路由">测试路由</a>
		<a href="#/my">首页</a>
				{{$route.query.test}}
		<router-link to="my?test=456456">首页有参数</router-link>
		<router-link to="friend?test=45645226">重定向到首页</router-link>
		<router-link to="dds">404</router-link>
		<!-- //区别多了个类 -->
		<router-view></router-view>
	</div>
	<!-- <div></div> -->
</template>

<script>
	import baseCount from './components/BaseCount'
	import Headtest from './components/Headtest.vue'
	import Directive from './components/Directive.vue'
	import Loadin from './components/Loadin.vue'
	import HelloWorld from './components/HelloWorld.vue'
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
			Directive,
			Loadin,
			HelloWorld
		},
		mounted() {
			console.log(this.$refs.testref);
			// var a="proptitle"
			
			// handleMessage2(message) {
			// 	this.proptitle = "子传父内容:" + message
			// }
			// $proptitle
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