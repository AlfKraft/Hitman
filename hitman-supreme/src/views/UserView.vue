<template>
  <v-row>
    <v-col></v-col>
    <v-col class="d-flex justify-center align-center">
      <AccountInfo v-if="!error" class="mainComponent" :name=name :elimination-code=eliminationCode :score=score />
    </v-col>
    <v-col></v-col>
  </v-row>
</template>

<script setup>
import {userStore} from "@/stores";
import {ref, onMounted} from 'vue'
import AccountInfo from '@/components/AccountInfo'
const name = ref('')
const eliminationCode = ref('')
const score = ref('')
const errorMessage = ref(null)
const store = userStore()

onMounted( ()=>{
  store.getMyStats().then(response => {
    name.value = response.data.name
    eliminationCode.value = response.data.eliminationCode
    score.value = response.data.score
  }).catch(error => {
    errorMessage.value = error.response.data.message
  })
})
</script>

<style scoped>

</style>
