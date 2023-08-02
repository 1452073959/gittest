<template>
	<div class="head">
		<h1>这是测试使用的组件组件</h1>
		<hr>
		{{ name }} {{ title }}
		<br>
		{{ proptitle2 }}
		<div v-html="msg"  ref="testref"></div>
		<input v-focus type="text" value="" v-model="text2"X/>输入框
		<button @click="sendMessage">测试子传父按钮</button>
		<button @click="sendsync">sync测试</button>

		<h3>测试for循环</h3>
		<ul>
			<li v-for="(item, index) in booksList">
				<span>{{ item.name }}</span>
				<span>{{ item.author }}</span>
				<button @click="del(item.id)">删除</button>
			</li>
		</ul>
		<!-- //测试插槽,标签内部后备内容 -->
		<slot>插槽默认内容</slot>
		<slot name="testsolt"></slot>
		<slot name="testsolt2"></slot>
		<!-- <slot>插槽默认内容</slot> -->

	</div>


</template>

<script>
	export default {
		data() {
			return {
				name: 'HelloWorld',
				text2: '',
				msg: ' <h3>学前端~来黑马！</h3>',
				booksList: [{
						id: 1,
						name: '《红楼梦》',
						author: '曹雪芹'
					},
					{
						id: 2,
						name: '《西游记》',
						author: '吴承恩'
					},
					{
						id: 3,
						name: '《水浒传》',
						author: '施耐庵'
					},
					{
						id: 4,
						name: '《三国演义》',
						author: '罗贯中'
					}
				]


			};
		},
		//方法
		methods: {
			sendMessage() {
				console.log("开始发送");
				this.$emit('emitmessage', this.text2 + this.title);
				console.log("子传父结束");
			},
			del(id) {
				this.booksList = this.booksList.filter(item => item.id !== id)
			},
			//sync更新父组件传过来的值
			sendsync() {
				// console.log("sync");
				
				this.$emit('update:title', "新的值");
				this.$nextTick(()=>{
					console.log("异步更新$nextTickDOM,更新完成之后才会做");
				})
				
			}
		},
		//计算属性
		watch: {
			text2: {
				handler(newValue, oldValue) {
					// 在obj属性或obj内部属性发生变化时调用
					console.log('Object changed:', newValue, oldValue);

					// 执行其他操作
					// ...
				},
				deep: true
			}
		},
		//父传子接受
		props: {
			title: String,
			proptitle2: String
		},
		// 1. 创建阶段（准备数据）
		beforeCreate() {
			console.log('beforeCreate 响应式数据准备好之前')
		},
		created() {
			console.log('created 响应式数据准备好之后')
			// this.数据名 = 请求回来的数据
			// 可以开始发送初始化渲染的请求了
		},

		// 2. 挂载阶段（渲染模板）
		beforeMount() {
			
		},
		mounted() {
// console.log(this.$refs.testref);
		// this.sendsync()
			// 可以开始操作dom了
		},

		// 3. 更新阶段(修改数据 → 更新视图)
		beforeUpdate() {

		},
		updated() {

		},

		// 4. 卸载阶段
		beforeDestroy() {
			console.log('beforeDestroy, 卸载前')
			console.log('清除掉一些Vue以外的资源占用，定时器，延时器...')
		},
		destroyed() {
			console.log('destroyed，卸载后')
		}
	};
</script>





<style scoped>
	.head {
		width: 500px;
		height: 100%;
		border: 1px solid red;
	}
</style>