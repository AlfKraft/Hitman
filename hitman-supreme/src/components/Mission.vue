<template>
  <v-card>
    <v-img
      src="@/assets/mission.jpg"
      height="400px"
      cover
    ></v-img>
    <v-card-title>MISSION</v-card-title>
    <v-card-subtitle>{{ props.name }}</v-card-subtitle>
    <v-card-subtitle>{{props.startTime}}</v-card-subtitle>
    <v-card-subtitle>{{props.endTime}}</v-card-subtitle>
    <v-card-text>{{props.details}}</v-card-text>
    <v-card-subtitle>{{props.location}}</v-card-subtitle>
    <v-card-actions>
      <v-text-field class="flex-fill" prepend-inner-icon="mdi-barcode" label="Mission completion code" variant="underlined"></v-text-field>
    </v-card-actions>
  </v-card>
</template>

<script setup>
import {ref} from 'vue'


const props = defineProps({
  name: String,
  startTime: String,
  endTime: String,
  details: String,
  location: String,
})

</script>

<style scoped>

</style>
