<template>
  <div class="box">
    <h2>Son1 子组件{{ count }}</h2>
    从vuex中获取的值: <label>{{ $store.state.count }}</label>
    <br>
    <button @click="handleAdd(1)">值 + 1</button>
    <button @click="handleAdd(5)">值 + 5</button>
    <button @click="handleAdd(10)">值 + 10</button>
    <button @click="handleChange">一秒后修改成666</button>
    <button @click="changeFn">改标题</button>

    <hr>
    <!-- 计算属性getters -->
    <div>{{ $store.state.list }}</div>
    <div>{{ $store.getters.filterList }}</div>

    <hr>
    <!-- 测试访问模块中的state - 原生 -->
    <div>{{ $store.state.user.userInfo.name }}</div>
    <button @click="updateUser">更新个人信息</button>
    <button @click="updateUser2">一秒后更新信息</button>

    <div>{{ $store.state.setting.theme }}</div>
    <button @click="updateTheme">更新主题色</button>
    <hr>
    <!-- 测试访问模块中的getters - 原生 -->
    <div>{{ $store.getters['user/UpperCaseName'] }}</div>


    {{ for2 }}
    <ul>
			<li v-for="item in booksList" :key="item.id">
				<span>{{ item.name }}</span>
				<span>{{ item.author }}</span>
				<button @click="del(item.id)">删除</button>
			</li>
    </ul>

  </div>
</template>

<script>
export default {
  name: 'Son1Com',
  data(){
    return{
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
    }
  },
  created () {
    console.log(this.$store.getters)
    console.log(this.$store.state.count)
    // console.log(store.state.count)
  },
  computed: {
    count () {
      return this.$store.state.count
    }
  },
  methods: {
    updateUser () {
      // $store.commit('模块名/mutation名', 额外传参)
      this.$store.commit('user/setUser', {
        name: 'xiaowang',
        age: 25
      })
    },
    updateUser2 () {
      // 调用action dispatch
      this.$store.dispatch('user/setUserSecond', {
        name: 'xiaohong',
        age: 28
      })
    },
    updateTheme () {
      this.$store.commit('setting/setTheme', 'pink')
    },
    handleAdd (n) {
      // 错误代码(vue默认不会监测，监测需要成本)
      // this.$store.state.count++
      // console.log(this.$store.state.count)

      // 应该通过 mutation 核心概念，进行修改数据
      // 需要提交调用mutation
      // this.$store.commit('addCount')

      // console.log(n)
      // 调用带参数的mutation函数
      this.$store.commit('addCount', {
        count: n,
        msg: '哈哈'
      })
    },
    changeFn () {
      this.$store.commit('changeTitle', '传智教育')
    },
    handleChange () {
      // 调用action
      // this.$store.dispatch('action名字', 额外参数)
      this.$store.dispatch('changeCountAction', 666)
    }
  }
}
</script>

<style lang="css" scoped>
.box{
  border: 3px solid #ccc;
  width: 400px;
  padding: 10px;
  margin: 20px;
}
h2 {
  margin-top: 10px;
}
</style>
