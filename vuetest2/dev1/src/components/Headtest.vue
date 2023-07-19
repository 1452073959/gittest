<template>
	<div class="head">
		<h1>这是测试使用的组件组件</h1>
		<hr>
		{{ name }} {{ title }} 
		<br>
		{{ proptitle2 }}

		<div v-html="msg"></div>
		<input type="text" value="" v-model="text2" />输入框
		<button @click="sendMessage">测试子传父按钮</button>
		<h3>测试for循环</h3>
		<ul>
			<li v-for="(item, index) in booksList">
				<span>{{ item.name }}</span>
				<span>{{ item.author }}</span>
				<button @click="del(item.id)">删除</button>
			</li>
		</ul>
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
				this.$emit('emitmessage', this.text2);
				console.log("子传父结束");
			},
			del(id) {
				this.booksList = this.booksList.filter(item => item.id !== id)
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