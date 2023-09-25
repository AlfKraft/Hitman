<template>
  <v-container v-if="!profileView">
    <v-alert v-if="error">{{error}}</v-alert>
    <v-card>
      <v-card-title>ALL PLAYERS</v-card-title>
    </v-card>
      <v-data-table
        :loading="loading"
        :headers="headers"
        :items="players"
        :items-per-page="itemsPerPage"
        :page="pageNum"
        @update:page="pageChange"
        @update:items-per-page="itemsPerPageChange"
        class="elevation-1 rounded"
      >
        <template v-slot:item.eliminated="{ item }">
          <v-btn class="ma-2"  color="#00796B" :disabled="!item.raw.eliminated" v-on:click="openReviveDialog(item)">
            Revive
          </v-btn>
          <v-btn  color="#B71C1C" :disabled="item.raw.eliminated" v-on:click="openEliminateDialog(item)">
            Eliminate
          </v-btn>
        </template>
        <template v-slot:item.profile="{ item }">
          <v-btn class="ma-2"  color="blue-grey-lighten-2" v-on:click="getProfile(item)">
            Profile
          </v-btn>
        </template>
      </v-data-table>
    <div class="text-center">
      <v-dialog
        v-model="dialog"
        width="auto"
      >
        <v-card>
          <v-card-text>
            {{dialogMessage}} {{handlePlayerName}}?
          </v-card-text>
          <v-card-actions v-if="!loading">
            <v-btn v-if="handlePlayerId && eliminateDialog"  class="bg-red-darken-4" @click="eliminatePlayer(handlePlayerId)">Yes</v-btn>
            <v-btn v-if="handlePlayerId && reviveDialog"  class="bg-red-darken-4" @click="revivePlayer(handlePlayerId)">Yes</v-btn>
            <v-btn  class="bg-red-darken-4" @click="closeDialog">No</v-btn>
          </v-card-actions>
          <Loading v-if="loading"/>
        </v-card>
      </v-dialog>
    </div>
  </v-container>
  <v-container v-if="profileView">
    <v-row class="justify-center">
    <v-card class="bg-transparent mainComponent">
    <PlayerCardForAdmin :id="profileId" @close="closeProfile"/>
    </v-card>
    </v-row>
  </v-container>

</template>

<script setup>
import {onMounted, ref} from "vue";
import {userStore} from "@/stores";
import { VDataTable } from 'vuetify/labs/VDataTable'
import {adminStore} from "@/stores/admin.store";
import PlayerCardForAdmin from "@/components/PlayerCardForAdmin.vue"
import Loading from "@/components/Loading";
const dialog = ref(false)
const eliminateDialog = ref(false)
const reviveDialog = ref(false)
const error = ref(null)
const players = ref([{id:1, name:"This Name"}, {id:2, name:"The other player", eliminated: true}])
const loading = ref(false)
const storeU = userStore()
const storeA = adminStore()
const profileId = ref(null);
const profileView = ref(false)
const itemsPerPage = ref(10)
const dialogMessage = ref('')
const handlePlayerId = ref(null)
const handlePlayerName = ref(null)
const pageNum = ref(1)
const headers = ref([
    {
      title: 'Id',
      align: 'start',
      sortable: true,
      key: 'id',
    },
    { title: 'Name', key: 'name', align: 'center' },
    { title: 'Points', key: 'points', align: 'center' },
    { title: 'Profile', key: 'profile', align: 'center', value: 'profile'},
    { title: 'Game status', key: 'eliminated', align: 'center', value: 'eliminated'},
  ])

onMounted(()=>{
  loadPlayers()
})
async function revivePlayer(playerId){
  loading.value = true
  await storeA.revivePlayer({id: playerId}).then(() => {
    error.value = null
    loadPlayers()
  }).catch(err => {
      error.value = err.response.data.message
    }
  )
  loading.value = false
  closeDialog()
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

async function eliminatePlayer(playerId){
  loading.value = true

  await storeA.eliminatePlayer({id: playerId}).then(() => {
    error.value = null
    loadPlayers()
  }).catch(err => {
      error.value = err.response.data.message
    }
  )
  loading.value = false

  closeDialog()
}

function getProfile(item){
  profileId.value = item.key
  profileView.value = !profileView.value
}

function closeProfile(){
  profileView.value = !profileView.value
}

function pageChange(newPage){
  pageNum.value = newPage
}

function itemsPerPageChange(newValue){
  itemsPerPage.value = newValue
}

function openReviveDialog(item){
  reviveDialog.value = true
  handlePlayerName.value = item.raw.name
  handlePlayerId.value = item.raw.id
  dialogMessage.value = "Are you sure you want to revive"

  dialog.value = true

}
function openEliminateDialog(item){
  eliminateDialog.value = true
  handlePlayerName.value = item.raw.name
  handlePlayerId.value = item.raw.id
  dialogMessage.value = "Are you sure you want to eliminate"
  dialog.value = true
}
function closeDialog(){
  handlePlayerName.value = null
  handlePlayerId.value = null
  dialogMessage.value = ''
  dialog.value = false
  reviveDialog.value = false
  eliminateDialog.value = false
}

</script>

<style scoped>

</style>
