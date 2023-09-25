<template>
  <v-card class="ma-2">
    <v-card-title class="text-red">CHECKPOINT</v-card-title>
    <v-card-title class="text-wrap">{{ props.name }}</v-card-title>
    <v-card-text class="text-red">If not completed, then you will be eliminated!</v-card-text>
    <v-card-subtitle class="mb-3">Start: {{props.startTime}}</v-card-subtitle>
    <v-card-subtitle class="mb-3">End: {{props.endTime}}</v-card-subtitle>
    <v-card-text class="text-wrap">Location: {{props.location}}</v-card-text>
    <v-alert v-if="message">{{message}}</v-alert>
    <v-card-actions v-if="props.available && !loading">
      <v-text-field v-model="checkpointCompletionData.checkpointCode" class="flex-fill ma-2"
                    append-inner-icon="mdi-check-outline" @click:append-inner="completeCheckpoint()"
                    prepend-inner-icon="mdi-barcode" label="Checkpoint completion code"
                    variant="underlined"></v-text-field>
    </v-card-actions>
    <Loading v-if="loading"/>
  </v-card>
</template>

<script setup>

import {reactive, ref} from "vue";
import {checkpointStore} from "@/stores/checkpoint.store";
import Loading from "@/components/Loading";
const store = checkpointStore()
const message = ref(null)
const loading = ref(false)

const props = defineProps({
  id: Number,
  name: String,
  startTime: String,
  endTime: String,
  location: String,
  available: Boolean
})
const checkpointCompletionData = reactive(
  {
    checkpointId: props.id,
    checkpointCode: ''
  }
)

async function completeCheckpoint(){
  loading.value = true;
  await store.completeCheckpoint(checkpointCompletionData).then(async (response) =>
  {
    message.value = response.data.message
    await new Promise(r => setTimeout(r, 4000));
    message.value = null
    store.loading = true
    await store.getMyCheckpoints()
    store.loading = false
  }).catch(async (error) => {
    message.value = error.response.data.message
    await new Promise(r => setTimeout(r, 4000));
    message.value = null
  })
  loading.value = false;
}

</script>

<style scoped>

</style>
