<template>
  <div>
    <!-- <van-nav-bar  left-text="返回" left-arrow @click-left="$router.back()" /> -->

    <div class="login">
      <van-nav-bar title="会员登录" left-arrow @click-left="$router.go(-1)" />
      <div class="container">
        <div class="title">
          <h3>手机号登录</h3>
          <p>未注册的手机号登录后将自动注册</p>
        </div>

        <div class="form">
          <div class="form-item">
            <input class="inp" maxlength="11" placeholder="请输入手机号码" type="text" v-model="mobile">
          </div>
          <div class="form-item">
            <input class="inp" maxlength="5" placeholder="请输入图形验证码" type="text" v-model="picCode">
            <img v-if="picUrl" :src="picUrl" @click="getPicCode" alt="">
          </div>
          <div class="form-item">
            <input class="inp" placeholder="请输入短信验证码" type="text"  v-model="msgCode">
            <button @click="getCode">
              {{ second === totalSecond ? '获取验证码' : second + '秒后重新发送'}}
            </button>
          </div>
        </div>

        <div class="login-btn" @click="login">登录</div>
      </div>
    </div>
  </div>
</template>

<script>
import { getPicCode, getMsgCode, codeLogin } from '@/api/login'
// import { Toast } from 'vant'
// import request from '@/utils/request'
export default {
  name: 'LoginPage',
  data () {
    return {
      picKey: '', // 将来请求传递的图形验证码唯一标识
      picUrl: '', // 存储请求渲染的图片地址
      totalSecond: 60, // 总秒数
      second: 60, // 当前秒数，开定时器对 second--
      timer: null, // 定时器 id
      mobile: '', // 手机号
      picCode: '', // 用户输入的图形验证码
      msgCode: '' // 短信验证码
    }
  },
  async created () {
    this.getPicCode()
  },
  // 离开页面清除定时器
  destroyed () {
    clearInterval(this.timer)
  },
  methods: {
    async getPicCode () {
      const res = await getPicCode()
      this.picUrl = res.data.base64
      this.picKey = res.data.key
      console.log(res)
      this.$toast.success('获取验证码成功')
    },
    // 校验 手机号 和 图形验证码 是否合法
    // 通过校验，返回true
    // 不通过校验，返回false
    validFn () {
      // if (!/^1[3-9]\d{9}$/.test(this.mobile)) {
      //   this.$toast('请输入正确的手机号')
      //   return false
      // }
      // if (!/^\w{4}$/.test(this.picCode)) {
      //   this.$toast('请输入正确的图形验证码')
      //   return false
      // }
      return true
    },

    // 获取短信验证码
    async getCode () {
      if (!this.validFn()) {
        // 如果没通过校验，没必要往下走了
        return
      }

      // 当前目前没有定时器开着，且 totalSecond 和 second 一致 (秒数归位) 才可以倒计时
      if (!this.timer && this.second === this.totalSecond) {
        // 发送请求
        // 预期：希望如果响应的status非200，最好抛出一个promise错误，await只会等待成功的promise
        await getMsgCode(this.picCode, this.picKey, this.mobile)

        this.$toast('短信发送成功，注意查收')

        // 开启倒计时
        this.timer = setInterval(() => {
          this.second--

          if (this.second <= 0) {
            clearInterval(this.timer)
            this.timer = null // 重置定时器 id
            this.second = this.totalSecond // 归位
          }
        }, 1000)
      }
    },
    // 登录
    async login () {
      // if (!this.validFn()) {
      //   return
      // }

      // if (!/^\d{6}$/.test(this.msgCode)) {
      //   this.$toast('请输入正确的手机验证码')
      //   return
      // }

      console.log('发送登录请求')

      const res = await codeLogin(this.mobile, this.msgCode)
      this.$store.commit('user/setUserInfo', res.data)
      this.$toast('登录成功')

      // 进行判断，看地址栏有无回跳地址
      // 1. 如果有   => 说明是其他页面，拦截到登录来的，需要回跳
      // 2. 如果没有 => 正常去首页
      const url = this.$route.query.backUrl || '/'
      this.$router.replace(url)
    }
  }

}
</script>

<style lang="less" scoped>
.container {
  padding: 49px 29px;

  .title {
    margin-bottom: 20px;

    h3 {
      font-size: 26px;
      font-weight: normal;
    }

    p {
      line-height: 40px;
      font-size: 14px;
      color: #b8b8b8;
    }
  }

  .form-item {
    border-bottom: 1px solid #f3f1f2;
    padding: 8px;
    margin-bottom: 14px;
    display: flex;
    align-items: center;

    .inp {
      display: block;
      border: none;
      outline: none;
      height: 32px;
      font-size: 14px;
      flex: 1;
    }

    img {
      width: 94px;
      height: 31px;
    }

    button {
      height: 31px;
      border: none;
      font-size: 13px;
      color: #cea26a;
      background-color: transparent;
      padding-right: 9px;
    }
  }

  .login-btn {
    width: 100%;
    height: 42px;
    margin-top: 39px;
    background: linear-gradient(90deg, #ecb53c, #ff9211);
    color: #fff;
    border-radius: 39px;
    box-shadow: 0 10px 20px 0 rgba(0, 0, 0, .1);
    letter-spacing: 2px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
}</style>
