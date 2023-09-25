<template>
  <v-row class="justify-center">
    <h1 class="text-white">Welcome B.O.S.S.</h1>
  </v-row>
  <v-row>
    <v-col class="d-flex justify-center align-center">
      <v-card max-width="420px" class="pa-2">
        <v-alert v-if="errorMessage">{{errorMessage}}</v-alert>
        <v-card-title class="text-wrap">Press this button to reshuffle targets</v-card-title>
        <v-card-actions v-if="!loading">
          <v-btn class="bg-red-darken-4"  @click="reshuffle()">Reshuffle</v-btn>
        </v-card-actions>
        <Loading v-else/>
      </v-card>
    </v-col>
    <v-col class="d-flex justify-center align-center">
      <v-card class=" mainComponent pa-2">
        <v-alert v-if="errorMessage">{{errorMessage}}</v-alert>
        <v-card-title v-if="timeoutEnabled" class="text-wrap">Disable time out</v-card-title>
        <v-card-title v-if="!timeoutEnabled" class="text-wrap">Enable time out</v-card-title>
        <v-card-actions v-if="!loading">
          <v-btn class="bg-red-darken-4"  @click="timeout()">Time out</v-btn>
        </v-card-actions>
        <Loading v-else/>
      </v-card>
    </v-col>
    <v-col class="d-flex justify-center align-center">
      <v-card  max-width="420px" class="pa-2">
        <v-alert v-if="errorMessage">{{errorMessage}}</v-alert>
        <v-card-title class="text-wrap">Press this to generate new URLs for player images</v-card-title>
        <v-card-actions v-if="!loading">
          <v-btn class="bg-red-darken-4" @click="genImages()">Generate new urls</v-btn>
        </v-card-actions>
        <Loading v-else/>
      </v-card>
    </v-col>
  </v-row>
  <v-row class="justify-center ma-2">
    <v-card>
      <v-card-title>CURRENT GAME</v-card-title>
      <v-data-table
        :loading="loading"
        :headers="headers"
        :items="gameData"
        :items-per-page="itemsPerPage"
        :page="pageNum"
        @update:page="pageChange"
        @update:items-per-page="itemsPerPageChange"
        class="elevation-1 rounded"
      >
      </v-data-table>
    </v-card>

  </v-row>
</template>

<script setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import {adminStore} from "@/stores/admin.store";
import {onMounted, ref} from "vue";
import Loading from "@/components/Loading";
import {storeToRefs} from "pinia/dist/pinia";
const store = adminStore()
const loading = ref(false)
const errorMessage = ref(null)
const {gameData} = storeToRefs(adminStore())
const itemsPerPage = ref(10)
const pageNum = ref(1)
const timeoutEnabled = ref(false)
const headers = ref([
  { title: 'Player name', key: 'playerName', align: 'center' },
  { title: 'Code', key: 'eliminationCode', align: 'center' },
  { title: 'Target name', key: 'targetName', align: 'center'},
])


onMounted(async ()=>{
  loading.value = true
  await store.getGameData().catch(error => {
    errorMessage.value = error.response.data.message
  })
  await store.getTimeOut().then(response => {
    timeoutEnabled.value = response.data.enabled
    }
  ).catch(error => {
    console.log(error)
  })
  loading.value = false
})

async function reshuffle(){
  loading.value = true
  await store.shufflePlayers().catch(error => {
    errorMessage.value = error.response.data.message
  })
  loading.value = false
}

function pageChange(newPage){
  pageNum.value = newPage
}

function itemsPerPageChange(newValue){
  itemsPerPage.value = newValue
}

async function genImages(){
  loading.value = true
  await store.generateNewURLsForPlayers().catch(error => {
    console.log(error)
  })
  loading.value = false
}

async function timeout(){
  loading.value = true
  await store.timeout().then(response =>{
    timeoutEnabled.value = !timeoutEnabled.value
  }
  ).catch(error => {
    errorMessage.value = "Couldn't toggle time out"
  })
  loading.value = false
}

</script>

<style scoped>

</style>
