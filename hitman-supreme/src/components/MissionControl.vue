<template>
  <v-container class="mb-3">
  <v-row class="d-flex justify-center">
    <v-card class="w-100">
      <v-card-title>MISSION CONTROL</v-card-title>
    </v-card>
  </v-row>
  <v-row  v-if="!loading">
    <v-table class="w-100 rounded">
      <thead>
      <tr>
        <th>
          Mission name
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
          Mission code
        </th>
      </tr>
      </thead>
      <tbody>
      <tr
        v-for="mission in missions"
        :key="mission.id"
      >
        <td>{{ mission.missionName }}</td>
        <td>{{ mission.startTime }}</td>
        <td>{{ mission.endTime }}</td>
        <td>{{ mission.location }}</td>
        <td>{{ mission.missionCode }}</td>
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
import {missionStore} from "@/stores/mission.store";
import {storeToRefs} from "pinia/dist/pinia";
import Loading from "@/components/Loading";

const store = missionStore()
const {missions, loading} = storeToRefs(missionStore())
onMounted(async () => {
  await store.getMissions()
}
)

</script>

<style scoped>

</style>
