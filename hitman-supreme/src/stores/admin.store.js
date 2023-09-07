import { defineStore } from 'pinia';

import axios from "axios";

export const adminStore = defineStore({
  id: 'admin',
  state: () => ({
    // initialize state from local storage to enable user to stay logged in
  }),
  actions: {
    async eliminatePlayer(playerData){
      await axios.post('/eliminatePlayer', playerData)
    },
    async revivePlayer(playerData){
      await axios.post('/revivePlayer', playerData)
    }

  }
});
