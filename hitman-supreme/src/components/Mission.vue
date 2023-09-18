<template>
  <v-card class="ma-2">
    <v-card-title>MISSION</v-card-title>
    <v-card-title>{{ props.name }}</v-card-title>
    <v-card-subtitle>Start: {{props.startTime}}</v-card-subtitle>
    <v-card-subtitle class="mb-3">End: {{props.endTime}}</v-card-subtitle>
    <v-card-title v-if="props.active">Info: </v-card-title>
    <v-card-text v-if="props.active">{{props.details}}</v-card-text>
    <v-card-text v-if="props.active">Location: {{props.location}}</v-card-text>
    <v-card-title>Points worth: {{props.points}}p</v-card-title>
    <v-alert v-if="message">{{message}}</v-alert>
    <v-card-actions v-if="props.active && !loading">
      <v-row class="ma-3 d-flex justify-center align-center ">
      <v-text-field v-model="missionCompletionData.missionCode" class="flex-fill ma-2"
                    append-inner-icon="mdi-check-outline" @click:append-inner="completeMission()"
                    prepend-inner-icon="mdi-barcode" label="Mission completion code"
                    variant="underlined"></v-text-field>
      <v-progress-circular v-if="loading" indeterminate size="30"></v-progress-circular>
      </v-row>
    </v-card-actions>
  </v-card>
</template>

<script setup>

import {reactive, ref} from "vue";
import {missionStore} from "@/stores/mission.store";
import Loading from "@/components/Loading";
const store = missionStore()
const message = ref(null)
const loading = ref(false)

const props = defineProps({
  id: Number,
  name: String,
  startTime: String,
  endTime: String,
  details: String,
  location: String,
  points: Number,
  active: Boolean
})
const missionCompletionData = reactive(
  {
    missionId: props.id,
    missionCode: ''
  }
)

async function completeMission(){
  loading.value = true
  await store.completeMission(missionCompletionData).then(async (response) =>
  {
    message.value = response.data.message
    await new Promise(r => setTimeout(r, 4000));
    message.value = null
    store.getMyMissions()
  }).catch(async (error) => {
    message.value = error.response.data.message
    await new Promise(r => setTimeout(r, 4000));
    message.value = null
  })
  loading.value=false
}

</script>

<style scoped>

</style>
