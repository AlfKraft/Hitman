<template>
  <v-card>
    <v-img
      src="@/assets/mission.jpg"
      height="200px"
      cover
    ></v-img>
    <div v-if="!props.loading">
    <v-card-title class="text-wrap">Welcome agent {{props.name}}!</v-card-title>
    <v-card-title v-if="eliminated" class="text-red">You are eliminated!</v-card-title>
    <v-card-text v-if="eliminated">Keep an eye on the missions. There might be a chance to revive you.</v-card-text>
    <v-card-title class="text-wrap" v-if="!eliminated"> Your elimination code: {{props.eliminationCode}} </v-card-title>
    <v-card-text v-if="!eliminated && eliminationCode.length === 0" class="text-red">You are alive! You will be included in the game with the next target shuffle. Everyday @ 01:00</v-card-text>
    <v-card-title> Points: {{props.score}}p </v-card-title>
    <v-card-title>Rank: {{props.rank}} </v-card-title>
    </div>
    <Loading v-if="props.loading"/>
  </v-card>
</template>

<script setup>
import Loading from "@/components/Loading";

const props = defineProps({
  name: String,
  eliminationCode: String,
  score : String,
  eliminated: Boolean,
  rank: String,
  loading: Boolean
})





</script>

<style scoped>

</style>
