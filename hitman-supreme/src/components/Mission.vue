<template>
  <v-card>
    <v-img
      src="@/assets/mission.jpg"
      height="400px"
      cover
    ></v-img>
    <v-card-title>MISSION</v-card-title>
    <v-card-title>{{ props.name }}</v-card-title>
    <v-card-subtitle>Start: {{props.startTime}}</v-card-subtitle>
    <v-card-subtitle>End: {{props.endTime}}</v-card-subtitle>
    <v-card-title>Info: </v-card-title>
    <v-card-text>{{props.details}}</v-card-text>
    <v-card-title>Location: {{props.location}}</v-card-title>
    <v-card-title>Points worth: {{props.points}}p</v-card-title>
    <v-card-actions>
      <v-text-field v-model="missionCompletionCode" class="flex-fill ma-2" append-inner-icon="mdi-check-outline"  prepend-inner-icon="mdi-barcode" label="Mission completion code" variant="underlined"></v-text-field>
    </v-card-actions>
  </v-card>
</template>

<script setup>


const props = defineProps({
  name: String,
  startTime: String,
  endTime: String,
  details: String,
  location: String,
  points: Number
})

</script>

<style scoped>

</style>
