<template>
  <v-container>
    <v-row>
      <mission-control class="rounded h-100"/>
    </v-row>
    <v-row>
      <MissionCreator class="bg-red-darken-4 rounded" />
    </v-row>
  </v-container>
</template>

<script setup>

import MissionCreator from '@/components/MissionCreator'
import MissionControl from '@/components/MissionControl'
</script>

<style scoped>

</style>
