import { defineStore } from 'pinia';

import axios from "axios";

export const userStore = defineStore({
  id: 'user',
  state: () => ({
    // initialize state from local storage to enable user to stay logged in
  }),
  actions: {
    getTarget(){
      return axios.get('/getMyTarget')
    },
    getMissions(){
      return axios.get('/getMissions')
    },
    eliminateTarget(eliminationData){
      return axios.post('/eliminateTarget', eliminationData)
    },
    getMyStats(){
      return axios.get('/getMyStats')
    },
    getAllPlayers(){
      return axios.get('/getPlayers')
    },
    leaderboard(){
      return axios.get('/leaderBoard')
    }

  }
});
