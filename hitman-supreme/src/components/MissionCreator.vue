<template>
  <v-container >
    <v-form>
      <v-text-field v-model="missionData.missionName" label="Insert mission name"></v-text-field>
      <v-textarea v-model="missionData.missionInfo" label="Insert mission info"></v-textarea>
      <v-text-field v-model="missionData.missionLocation" label="Add mission location"></v-text-field>
      <v-text-field v-model="missionData.startDateTime" label="Start time"></v-text-field>
      <v-text-field v-model="missionData.startDateTime" label="End time"></v-text-field>
      <v-btn @click="createMission">Create Mission</v-btn>
    </v-form>
  </v-container>
</template>
<script setup>
import {inject,reactive} from 'vue';
const axios = inject('axios');


const missionData = reactive({
  missionName: '',
  missionInfo:'',
  missionLocation: '',
  startDateTime: '',
  endDateTime: ''
});


async function createMission(){

}



</script>

<style scoped>

</style>

