<template>
  <v-container>
    <v-row dense class="d-flex justify-center align-center">
      <v-card v-if="missions.length === 0 && !loading">
        <v-card-title>You don't have any active missions</v-card-title>
      </v-card>
      <v-col class="d-flex justify-center align-center" v-for="mission in missions" :key="mission.id">
        <Mission class="mainComponent" :id="mission.id" :name="mission.missionName" :details="mission.missionInfo" :start-time="mission.startTime"
        :end-time="mission.endTime" :location="mission.location" :points="mission.points" :active="mission.active"/>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import Mission from '@/components/Mission'
import {onMounted, ref} from 'vue'
import {storeToRefs} from "pinia/dist/pinia";
import {missionStore} from "@/stores/mission.store";
import Loading from "@/components/Loading";
const store = missionStore()
const {missions, loading} = storeToRefs(missionStore())

onMounted(async ()=>{
  loading.value = true
  await store.getMyMissions()
})

</script>

<style scoped>

</style>
