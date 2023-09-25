import { defineStore } from 'pinia';

import axios from "axios";

export const adminStore = defineStore({
  id: 'admin',
  state: () => ({
    // initialize state from local storage to enable user to stay logged in
    gameData : []
  }),
  actions: {
    async eliminatePlayer(playerData){
      await axios.post('/eliminatePlayer', playerData)
    },
    async revivePlayer(playerData){
      await axios.post('/revivePlayer', playerData)
    },
    shufflePlayers(){
      return axios.get('/codes').then(
        response => {
          this.gameData = response.data.gameData
        }
      )
    },
    generateNewURLsForPlayers(){
       axios.get('/images').catch(error=>{
         console.log(error)
       })
    },
    getGameData(){
      return axios.get('/gameData').then(response => {
        this.gameData = response.data.gameData
      })
    },
    timeout(){
      return axios.get('/timeout')
    },
    getTimeOut(){
      return axios.get('/getTimeOut')
    }
  }
});
