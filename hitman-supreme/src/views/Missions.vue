<template>
  <v-container>
    <v-row class="d-flex justify-center align-center">
      <v-col></v-col>
      <v-col>
        <Mission class="mainComponent"/>
      </v-col>
      <v-col></v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import Mission from '@/components/Mission'
import {userStore} from "@/stores";
import {onMounted, ref} from 'vue'
const missions = ref([])
const store = userStore()

  onMounted(async ()=>{
  await store.getMissions().then(response => {
    missions.value = response.data.missions
  })
})

</script>

<style scoped>

</style>
