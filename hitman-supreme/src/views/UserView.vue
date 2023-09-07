<template>
  <v-row class="d-flex justify-center align-center">
    <v-col ></v-col>
    <v-col class="d-flex justify-center align-center">
      <AccountInfo v-if="!loading" class="mainComponent" :name=name :elimination-code=eliminationCode :score=score />
      <v-card v-else>
      <Loading/>
      </v-card>
    </v-col>
    <v-col ></v-col>
    <v-col ></v-col>
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
    <v-col></v-col>
  </v-row>
</template>

<script setup>
import {userStore} from "@/stores";
import {ref, onMounted} from 'vue'
import AccountInfo from '@/components/AccountInfo'
import Loading from "@/components/Loading";
const name = ref('')
const eliminationCode = ref('')
const score = ref('')
const errorMessage = ref(null)
const store = userStore()
const leaderBoardData = ref([])
const loading = ref(false)

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
  loading.value = false
})
</script>

<style scoped>

</style>
