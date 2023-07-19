const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave:false,
// publicPath: 'http://192.168.50.153:8687/dist/' // 或者设置为你的子目录路径
})

