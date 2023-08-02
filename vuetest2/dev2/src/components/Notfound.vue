<template>
	<h1>
		这是404
		<button @click="goSearch()">跳转</button>
	</h1>
</template>

<script>
	export default {
	  name: 'FindMusic',
	  methods: {
	    goSearch () {
	      // 1. 通过路径的方式跳转
	      // (1) this.$router.push('路由路径') [简写]
	      this.$router.push('/Directive?a=1&b=2&c=3')
	
	      // (2) this.$router.push({     [完整写法]
	      //         path: '路由路径' 
	      //     })
	      // this.$router.push({
	      //   path: '/search'
	      // })
	
	      // 2. 通过命名路由的方式跳转 (需要给路由起名字) 适合长路径
	      //    this.$router.push({
	      //        name: '路由名'
	      //    })
	      // this.$router.push({
	      //   name: 'search'
	      // })
	    }
	  }
	}
	</script>
	
</script>

<style>
</style>