<template>
  <v-card>
    <v-card-title>Welcome agent {{props.name}} !</v-card-title>
    <v-card-text> Your elimination code: {{props.eliminationCode}} </v-card-text>
    <v-card-text> Score: {{props.score}} </v-card-text>
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
