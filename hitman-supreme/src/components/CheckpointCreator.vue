<template>
  <v-container >
    <v-row class="d-flex justify-center align-center">
      <v-alert class="ma-2 bg-red-darken-4" v-if="message">{{message}}</v-alert>
    </v-row>
    <v-row>
      <v-form class="w-100 ma-2" v-if="!loading">
        <v-text-field v-model="checkpointData.checkpointName" label="Checkpoint name"></v-text-field>
        <v-text-field v-model="checkpointData.checkpointLocation" label="Checkpoint location"></v-text-field>
        <v-text-field v-model="checkpointData.startDateTime" type="datetime-local" label="Checkpoint start time"></v-text-field>
        <v-text-field v-model="checkpointData.endDateTime" type="datetime-local" label="Checkpoint end time"></v-text-field>
        <v-btn @click="createCheckpoint">Create Mission</v-btn>
      </v-form>
      <Loading v-else/>
    </v-row>
  </v-container>
</template>
<script setup>
import {reactive, ref} from 'vue';
import Loading from "@/components/Loading";
const loading = ref(false)
import {checkpointStore} from "@/stores/checkpoint.store";

const message = ref(null)
const store = checkpointStore()
const checkpointData = reactive({
  checkpointName: '',
  checkpointLocation: '',
  startDateTime: '',
  endDateTime: ''
});


async function createCheckpoint(){
  loading.value = true
  await store.createNewCheckpoint(checkpointData).then(response =>{
      message.value = "Checkpoint created successfully!"
    }
  ).catch(
    err =>
    {
      message.value = err.response.data.message
    }
  )
  loading.value = false

}



</script>

<style scoped>

</style>

