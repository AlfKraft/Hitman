<template>
  <v-row>
    <v-col></v-col>
    <v-col class="d-flex justify-center align-center">
      <v-card>
        <v-card-title>Press this button to reshuffle targets</v-card-title>
        <v-card-actions v-if="!loading">
          <v-btn @click="reshuffle()">Reshuffle</v-btn>
        </v-card-actions>
        <Loading v-else/>
      </v-card>
    </v-col>
    <v-col></v-col>
  </v-row>
</template>

<script setup>

import {missionStore} from "@/stores/mission.store";
import {ref} from "vue";
import Loading from "@/components/Loading";
const store = missionStore()
const loading = ref(false)

async function reshuffle(){
  loading.value = true
  await store.shufflePlayers().catch(error => {
    console.log(error)
  })
  loading.value = false
}
</script>

<style scoped>

</style>
