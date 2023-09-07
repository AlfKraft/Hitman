<template>
<v-card>
  <v-card-title>MISSION CONTROL</v-card-title>
</v-card>
  <v-table v-if="!loading">
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
  <v-card v-else class="d-flex justify-center align-center">
    <Loading/>
  </v-card>

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
  console.log(store.loading)
}
)

</script>

<style scoped>

</style>
