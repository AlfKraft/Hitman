<template>
  <v-container>
    <v-row class="d-flex justify-center">
      <v-col><mission-control class="bg-red-darken-4 rounded h-100"/></v-col>
      <v-col><MissionCreator class="bg-red-darken-4 rounded" /></v-col>
    </v-row>
  </v-container>
</template>

<script setup>

import MissionCreator from '@/components/MissionCreator'
import MissionControl from '@/components/MissionControl'
</script>

<style scoped>

</style>
