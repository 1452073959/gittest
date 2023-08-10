import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    search: ['手机', '高高', '晨晨']
  },
  getters: {
    token (state) {
      return state.user.userinfo.token
    }

  },
  mutations: {
    searchedit (state, obj) {
      console.log(obj)
      state.search = obj
    }
  },
  actions: {
  },
  modules: {
    user
  }
})
