<template>
  <v-container>
    <v-card>
      <v-tabs
        v-model="tab"
        align-tabs="center"
      >
        <v-tab :value="1">Missions</v-tab>
        <v-tab :value="2">Checkpoints</v-tab>
        <v-tab :value="3">Mission creator</v-tab>
        <v-tab :value="4">Checkpoint creator</v-tab>
      </v-tabs>
      <v-window v-model="tab">
        <v-window-item
          :key="1"
          :value="1"
        >
          <mission-control class="rounded h-100"/>
        </v-window-item>
        <v-window-item
          :key="2"
          :value="2"
        >
          <CheckpointAdminTable class="rounded h-100" />
        </v-window-item>
        <v-window-item
          :key="3"
          :value="3"
        >
          <MissionCreator class="bg-red-darken-4 rounded" />
        </v-window-item>
        <v-window-item
          :key="4"
          :value="4"
        >
          <CheckpointCreator class="bg-red-darken-4 rounded" />
        </v-window-item>
      </v-window>
    </v-card>
  </v-container>
</template>

<script setup>
import {ref} from 'vue'
import MissionCreator from '@/components/MissionCreator.vue'
import MissionControl from '@/components/MissionControl.vue'
import CheckpointCreator from '@/components/CheckpointCreator.vue'
import CheckpointAdminTable from "@/components/CheckpointAdminTable.vue";

const tab = ref(null)
</script>

<style scoped>

</style>
