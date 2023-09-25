import { defineStore } from 'pinia';

import axios from "axios";

export const missionStore = defineStore({
  id: 'mission',
  state: () => ({
    missions: [],
    loading: false,
    completedMissions: []
  }),
  actions: {
    async getMyMissions(){
      this.loading = true;
       return await axios.get('/myMissions').then(
         response => {
           this.missions = response.data.missions
           this.completedMissions = response.data.completedMissions
           this.loading = false;
         }
       ).catch(error => {
         this.loading = false;
       })

    },
    async createNewMission(missionData){
      return await axios.post('/createNewMission',missionData).then(async () => {
        await this.getMissions()
      }
      )
    },

    completeMission(missionCompletionData){
      return axios.post('/completeMission', missionCompletionData)
    },
    async getMissions(){
      this.loading = true;
      return await axios.get('/getMissions').then(
        response => {
          this.missions = response.data.missions
          this.loading = false;
        }
      ).catch(error => {
        this.loading = false;
      })
  },
  },
});
