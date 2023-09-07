<template>
  <v-container>
    <v-alert v-if="error">{{error}}</v-alert>
    <v-card>
      <v-card-title>ALL PLAYERS</v-card-title>
    </v-card>
      <v-data-table
        :loading="loading"
        :headers="headers"
        :items="players"
        :items-per-page="10"
        :item-class="row_class"
        class="elevation-1 rounded"
      >
        <template v-slot:item.eliminated="{ item }">
          <v-btn class="ma-2"  color="#00796B" :disabled="!item.raw.eliminated" v-on:click="revivePlayer(item)">
            Revive
          </v-btn>
          <v-btn  color="#B71C1C" :disabled="item.raw.eliminated" v-on:click="eliminatePlayer(item)">
            Eliminate
          </v-btn>
        </template>
      </v-data-table>
  </v-container>

</template>

<script setup>
import {onMounted, ref} from "vue";
import {userStore} from "@/stores";
import { VDataTable } from 'vuetify/labs/VDataTable'
import {adminStore} from "@/stores/admin.store";

const error = ref(null)
const players = ref([])
const loading = ref(true)
const storeU = userStore()
const storeA = adminStore()
const headers = ref([
    {
      title: 'Id',
      align: 'start',
      sortable: true,
      key: 'id',
    },
    { title: 'Username', key: 'username', align: 'center' },
    { title: 'Name', key: 'name', align: 'center' },
    { title: 'Points', key: 'points', align: 'center' },
    { title: '', key: 'eliminated', align: 'end', value: 'eliminated'}
  ])

onMounted(()=>{
  loadPlayers()
})
async function revivePlayer(item){
  await storeA.revivePlayer({id: item.key}).then(()=>{
    error.value = null
    loadPlayers()
  }).catch(err=>{
    error.value = err.response.data.message
  }
  )
}

async function loadPlayers(){
  loading.value=true
  await storeU.getAllPlayers().then(
    response=>{
      players.value = response.data
    }
  ).catch(
    err => {
      error.value = err.response.data.message
    }
  )
 loading.value=false
}

async function eliminatePlayer(item){
  await storeA.eliminatePlayer({id: item.key}).then(()=>{
    error.value = null
    loadPlayers()
  }).catch(err => {
    error.value = err.response.data.message
    }
  )
}

</script>

<style scoped>

</style>
