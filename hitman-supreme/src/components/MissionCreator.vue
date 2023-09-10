<template>
  <v-container >
    <v-row class="d-flex justify-center align-center">
      <v-alert class="ma-2 bg-red-darken-4" v-if="message">{{message}}</v-alert>
    </v-row>
    <v-row>
      <v-form class="w-100 ma-2" v-if="!loading">
        <v-text-field v-model="missionData.missionName" label="Insert mission name"></v-text-field>
        <v-textarea v-model="missionData.missionInfo" label="Insert mission info"></v-textarea>
        <v-text-field v-model="missionData.missionLocation" label="Add mission location"></v-text-field>
        <v-text-field v-model="missionData.startDateTime" type="datetime-local" label="Start time"></v-text-field>
        <v-text-field v-model="missionData.endDateTime" type="datetime-local" label="End time"></v-text-field>
        <v-text-field v-if="!missionData.forEliminated" v-model="missionData.points" label="Points worth"></v-text-field>
        <v-text-field v-model="missionData.missionCode" label="Mission completion code"></v-text-field>
        <v-switch color="white" v-model="missionData.forEliminated" label="For eliminated players"></v-switch>
        <v-text-field v-model="missionData.missionCompletionCount" label="Mission completion count"></v-text-field>
        <v-btn @click="createMission">Create Mission</v-btn>
      </v-form>
      <Loading v-else/>
    </v-row>
  </v-container>
</template>
<script setup>
import {reactive, ref} from 'vue';
import Loading from "@/components/Loading";
const loading = ref(false)
import {missionStore} from "@/stores/mission.store";

const message = ref(null)
const store = missionStore()
const missionData = reactive({
  missionName: '',
  missionInfo:'',
  missionLocation: '',
  startDateTime: '',
  endDateTime: '',
  points: '',
  missionCode: '',
  forEliminated: false,
  missionCompletionCount: ''
});


async function createMission(){
  loading.value = true
  await store.createNewMission(missionData).then(response =>{
    message.value = "Mission created successfully!"
  }
  ).catch(
    err =>
    {
      message.value = err.response.data.message
    }
  )
  loading.value = false

}



</script>

<style scoped>

</style>

