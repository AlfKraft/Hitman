<template>
  <v-card class="bg-black">
    <v-row class="justify-center mt-4">
      <h1 class="text-white">HITMA<span class="text-red">N</span></h1>
    </v-row>
    <v-row>
      <v-img src="@/assets/hitman_logo.png"></v-img>
    </v-row>
    <v-row class="justify-center">
      <v-btn class="homeViewButton" @click="register">REGISTER</v-btn>
    </v-row>
    <v-row class="justify-center mb-2">
      <v-btn class="homeViewButton" @click="login">LOG IN</v-btn>
    </v-row>
  </v-card>
</template>

<script setup>

import {useRouter} from 'vue-router'
const router = useRouter()
function register(){
  router.push('/register')
}
function login(){
  router.push('/login')
}


</script>

<style scoped>
.homeViewButton {
  margin-bottom: 1rem;
  color: darkred;
  width: 200px;
  border: thin solid darkred;

}

</style>
