<template>
  <v-container v-if="!loading">
    <v-row dense class="d-flex justify-center align-center">
      <v-card v-if="checkpoints.length === 0 && !loading">
        <v-card-title text-no-wrap>You don't have any active checkpoints</v-card-title>
      </v-card>
      <v-col class="d-flex justify-center align-center" v-for="checkpoint in checkpoints" :key="checkpoint.id">
        <Checkpoint class="mainComponent" :id="checkpoint.id" :name="checkpoint.checkpointName" :start-time="checkpoint.startTime"
                 :end-time="checkpoint.endTime" :location="checkpoint.location" :available="checkpoint.available"/>
      </v-col>
    </v-row>
  </v-container>
  <v-container v-if="loading">
    <v-card>
      <Loading/>
    </v-card>
  </v-container>
</template>

<script setup>
import Checkpoint from '@/components/Checkpoint.vue'
import {onMounted, ref} from 'vue'
import {storeToRefs} from "pinia/dist/pinia";
import {checkpointStore} from "@/stores/checkpoint.store";
import Loading from "@/components/Loading";
const store = checkpointStore()
const {checkpoints, loading} = storeToRefs(checkpointStore())

onMounted(async ()=>{
  loading.value = true
  await store.getMyCheckpoints().catch(error =>{
    checkpoints.value = []
    loading.value = false
  })
})


</script>

<style scoped>

</style>
