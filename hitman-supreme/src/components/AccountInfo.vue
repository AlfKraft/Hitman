<template>
  <v-card>
    <v-img
      src="@/assets/mission.jpg"
      height="200px"
      cover
    ></v-img>
    <v-card-title>Welcome agent {{props.name}} !</v-card-title>
    <v-card-title> Your elimination code: {{props.eliminationCode}} </v-card-title>
    <v-card-title> Points: {{props.score}} </v-card-title>
  </v-card>
</template>

<script setup>
import {ref} from 'vue'
const rankingEnding = ref('')
const props = defineProps({
  name: String,
  eliminationCode: String,
  score : String,
})
/*
function getRankingEnding(){
  if (props.ranking === 1){
    return 'st'
  }
  return rankingEnding
}

 */




</script>

<style scoped>

</style>
