<template>
  <v-container>
    <v-row dense class="d-flex justify-center align-center">
      <v-col class="d-flex justify-center align-center" v-for="mission in missions" :key="mission.id">
        <Mission class="mainComponent" :name="mission.missionName" :details="mission.missionInfo" :start-time="mission.startTime"
        :end-time="mission.endTime" :location="mission.location" :points="mission.points"/>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import Mission from '@/components/Mission'
import {userStore} from "@/stores";
import {onMounted, ref} from 'vue'
const missions = ref([{id: 3}])
const store = userStore()

  onMounted(async ()=>{
  await store.getMissions().then(response => {
    missions.value = response.data.missions
  })
})

</script>

<style scoped>

</style>
