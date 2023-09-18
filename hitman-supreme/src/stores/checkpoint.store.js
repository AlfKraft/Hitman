import { defineStore } from 'pinia';

import axios from "axios";

export const checkpointStore = defineStore({
  id: 'checkpoint',
  state: () => ({
    checkpoints: [],
    loading: false,
    errorMessage: null
  }),
  actions: {
    async createNewCheckpoint(checkpointData){
      return await axios.post('/createNewCheckpoint',checkpointData).then(async () => {
          await this.getCheckpoints()
        }
      )
    },
    async getCheckpoints(){
      return await axios.get('/getCheckpoints').then(response => {
          this.checkpoints = response.data.checkpoints
        }
      )
    },
    async completeCheckpoint(checkpointCompletionData){
      return await axios.post('/completeCheckpoint',checkpointCompletionData).then(async () => {
          await this.getMyCheckpoints()
        }
      )
    },
    async getMyCheckpoints(){
      this.loading = true
      return await axios.get('/getMyCheckpoints').then(response => {
          this.checkpoints = response.data.userCheckpoints
          this.loading = false;
        }
      )
    }
  },
});
