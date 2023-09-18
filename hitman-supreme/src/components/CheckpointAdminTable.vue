<template>
  <v-container class="mb-3">
    <v-row class="d-flex justify-center">
      <v-card class="w-100">
        <v-card-title>CHECKPOINT CONTROL</v-card-title>
      </v-card>
    </v-row>
    <v-row  v-if="!loading">
      <v-table class="w-100 rounded">
        <thead>
        <tr>
          <th>
            Checkpoint name
          </th>
          <th>
            Start time
          </th>
          <th>
            End time
          </th>
          <th>
            Location
          </th>
          <th>
            Checkpoint code
          </th>
        </tr>
        </thead>
        <tbody>
        <tr
          v-for="checkpoint in checkpoints"
          :key="checkpoint.id"
        >
          <td>{{ checkpoint.checkpointName }}</td>
          <td>{{ checkpoint.startTime }}</td>
          <td>{{ checkpoint.endTime }}</td>
          <td>{{ checkpoint.location }}</td>
          <td>{{ checkpoint.checkpointCode }}</td>
        </tr>
        </tbody>
      </v-table>
    </v-row>
    <v-card v-else class="d-flex justify-center align-center">
      <Loading/>
    </v-card>
  </v-container>
</template>

<script setup>

import {computed, onMounted, ref} from "vue";
import {checkpointStore} from "@/stores/checkpoint.store";
import {storeToRefs} from "pinia/dist/pinia";
import Loading from "@/components/Loading";

const store = checkpointStore()
const {checkpoints, loading} = storeToRefs(checkpointStore())
onMounted(async () => {
    await store.getCheckpoints()
  }
)

</script>

<style scoped>

</style>
