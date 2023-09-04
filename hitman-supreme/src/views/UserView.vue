<template>
  <v-row>
    <v-col></v-col>
    <v-col class="d-flex justify-center align-center">
      <AccountInfo v-if="!error" class="mainComponent" :name=name :elimination-code=eliminationCode />
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
const error = ref(null)
const store = userStore()

onMounted(()=>{

})
</script>

<style scoped>

</style>
