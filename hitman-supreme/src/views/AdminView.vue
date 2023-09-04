<template>
  <v-row>
    <v-col></v-col>
    <v-col class="d-flex justify-center align-center">
      <AccountInfo class="mainComponent" :username=username :eliminationCode=token :role=role />
    </v-col>
    <v-col></v-col>
  </v-row>
</template>

<script setup>

import {userStore} from "@/stores";
import {ref, onMounted} from 'vue'
import AccountInfo from '@/components/AccountInfo'
const username = ref('')
const eliminationCode = ref('')
const store = userStore()

onMounted(()=>{


})
</script>

<style scoped>

</style>
