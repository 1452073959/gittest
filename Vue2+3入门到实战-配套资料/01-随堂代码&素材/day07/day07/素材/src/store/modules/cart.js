import axios from 'axios'

export default {
  namespaced: true,
  state () {
    return {
      list: []
    }
  },
  mutations: {
    updateList (state, payload) {
      state.list = payload
    },
    updateCount (state, payload) {
      const goods = state.list.find((item) => item.id === payload.id)
      goods.count = payload.count
    }
  },
  actions: {
    async getList (ctx) {
      const res = await axios.get('http://localhost:3000/cart')
      ctx.commit('updateList', res.data)
    },
    async updateCount (ctx, payload) {
      await axios.patch('http://localhost:3000/cart/' + payload.id, {
        count: payload.count
      })
      ctx.commit('updateCount', payload)
    }
  },
  getters: {
    total (state) {
      return state.list.reduce((p, c) => p + c.count, 0)
    },
    totalPrice (state) {
      return state.list.reduce((p, c) => p + c.count * c.price, 0)
    }
  }
}
