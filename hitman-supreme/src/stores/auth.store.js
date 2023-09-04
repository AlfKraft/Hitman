import { defineStore } from 'pinia';

import axios from "axios";

export const useAuthStore = defineStore({
  id: 'auth',
  state: () => ({
    // initialize state from local storage to enable user to stay logged in
    user: JSON.parse(localStorage.getItem('user')),
  }),
  actions: {
    async login(loginData) {
      return await axios.post(`/login`, loginData).then(response => {
        this.user = {
          username: response.data.username,
          token: response.data.token,
          role: response.data.role
        }
        localStorage.setItem('user', JSON.stringify(this.user))
        axios.defaults.headers.common['Authorization'] = 'Bearer ' + response.data.token;
        if(this.user.role === 'ADMIN'){
          this.router.push('/admin');
        }
        else {
          this.router.push('/user')
        }
      });
    },
    logout() {
      this.user = null;
      localStorage.clear();
      axios.defaults.headers.common['Authorization'] = ''
      this.router.push('/');
    },
    async verifyAccount(){
      return await axios.get('/verify').then(response => {
        this.user = {
          username: response.data.username,
          token: response.data.token,
          role: response.data.role
        }
        localStorage.setItem('user', JSON.stringify(this.user))
        axios.defaults.headers.common['Authorization'] = 'Bearer ' + response.data.token;
      }).catch(error => {
        localStorage.clear()
        this.error = error.message
        this.user = null
      })
    }
  }
});
