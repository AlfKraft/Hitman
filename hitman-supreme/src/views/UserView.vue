<template>
  <v-row class="d-flex justify-center align-center ma-3">
    <v-col class="d-flex justify-center align-center">
      <AccountInfo v-if="!loading" class="mainComponent" :name=name :elimination-code=eliminationCode :score=score />
      <v-card v-else>
      <Loading/>
      </v-card>
    </v-col>
    <v-col class="d-flex justify-center align-center">
      <v-card>
      <v-card class="mainComponent">
        <v-card-title>TOP PLAYERS</v-card-title>
      </v-card>
        <v-card v-if="loading">
          <Loading/>
        </v-card>
      <v-table v-else class="mainComponent rounded">
        <thead>
        <tr>
          <th>
            Ranking
          </th>
          <th>
            Name
          </th>
          <th>
            Points
          </th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="leader in leaderBoardData" :key="leader.rank">
          <td>
            {{leader.rank}}.
          </td>
          <td>
            {{leader.name}}
          </td>
          <td>
            {{leader.points}}p
          </td>
        </tr>
        </tbody>
      </v-table>
      </v-card>
    </v-col>
    <v-col class="d-flex justify-center align-center">
      <v-card>
        <v-card class="mainComponent">
          <v-card-title>MY COMPLETED MISSIONS</v-card-title>
        </v-card>
        <v-card v-if="loading">
          <Loading/>
        </v-card>
        <v-table v-else class="mainComponent rounded">
          <thead>
          <tr>
            <th>
              Name
            </th>
            <th>
              Points
            </th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="mission in completedMissions" :key="mission.id">
            <td>
              {{mission.missionName}}
            </td>
            <td>
              {{mission.points}}p
            </td>
          </tr>
          </tbody>
        </v-table>
        <v-alert v-if="completedMissions.length === 0 && !loading">You haven't completed any missions yet</v-alert>
      </v-card>
    </v-col>
  </v-row>
</template>

<script setup>
import {userStore} from "@/stores";
import {ref, onMounted} from 'vue'
import AccountInfo from '@/components/AccountInfo'
import Loading from "@/components/Loading";
import {missionStore} from "@/stores/mission.store";
import {storeToRefs} from "pinia/dist/pinia";
const name = ref('')
const eliminationCode = ref('')
const score = ref('')
const errorMessage = ref(null)
const store = userStore()
const missionsStore = missionStore()
const leaderBoardData = ref([])
const loading = ref(false)
const {completedMissions} = storeToRefs(missionStore())

onMounted( async ()=>{
  loading.value = true
  await store.getMyStats().then(response => {
    name.value = response.data.name
    eliminationCode.value = response.data.eliminationCode
    score.value = response.data.score
  }).catch(error => {
    errorMessage.value = error.response.data.message
  })
  await store.leaderboard().then(response=>{
    leaderBoardData.value = response.data
  }).catch(err=>{
    errorMessage.value = err.response.data.message
  })
  await missionsStore.getMyMissions()
  loading.value = false
})
</script>

<style scoped>

</style>
