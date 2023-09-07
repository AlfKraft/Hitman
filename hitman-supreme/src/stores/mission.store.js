import { defineStore } from 'pinia';

import axios from "axios";

export const missionStore = defineStore({
  id: 'mission',
  state: () => ({
    missions: [],
    loading: false
  }),
  actions: {
    async getMissions(){
      this.loading = true;
       return await axios.get('/getMissions').then(
         response => {
           this.missions = response.data.missions
           this.loading = false;
         }
       ).catch(error => {
         this.missions = []
         this.loading = false;
       })

    },
    createNewMission(missionData){
      return axios.post('/createNewMission',missionData).then(async () => {
        await this.getMissions()
      }
      )
    },

    shufflePlayers(){
      return axios.get('/codes')
    }
  },
});
