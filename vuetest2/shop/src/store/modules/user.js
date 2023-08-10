import { getInfo, setInfo } from '@/utils/storage'
export default {
  namespaced: true,
  state () {
    return {
    //   userinfo: {
    //     token: '',
    //     userid: ''
    //   }
      userinfo: getInfo()
    }
  },
  mutations: {
    // 所有mutations的第一个参数，都是state
    setUserInfo (state, obj) {
      state.userinfo = obj
      setInfo(obj)
    }
  },
  actions: {
    logout (context) {

    }
  },
  getters: {}
}
