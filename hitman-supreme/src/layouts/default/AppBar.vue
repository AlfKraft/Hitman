<template>
  <v-app-bar class="bg-red-darken-4 d-flex">
    <v-app-bar-title>
      HITMAN
    </v-app-bar-title>
    <p class="mr-6" v-if="isUserAdmin() || isUserUser()">Codename: {{"Codename"}}</p>
    <v-btn v-if="!(isUserAdmin() || isUserUser())" to="/">Home</v-btn>
    <v-btn v-if="!(isUserAdmin() || isUserUser())" to="/about">About</v-btn>
    <v-btn v-if="!(isUserAdmin() || isUserUser())" to="/register">Register</v-btn>
    <v-btn v-if="!(isUserAdmin() || isUserUser())" to="/login">Log In</v-btn>
    <v-btn v-if="isUserAdmin() || isUserUser()" @click="findRoute">MyHub</v-btn>
    <v-btn v-if="isUserUser()" to="/missions">Missions</v-btn>
    <v-btn v-if="isUserUser()" to="/target">Target</v-btn>
    <v-btn v-if="isUserAdmin()" to="/missionhub">MissionHub</v-btn>
    <v-btn v-if="isUserAdmin()" to="/userhub">UserHUB</v-btn>
    <v-btn v-if="(isUserAdmin() || isUserUser())" @click="logout">Logout</v-btn>
  </v-app-bar>
</template>

<script setup>
import {useRouter} from "vue-router";
import { useAuthStore } from '@/stores';
import {computed} from "vue";

const router = useRouter();
const auth = useAuthStore()


computed(
  isUserUser(),
  isUserAdmin()
)


function isUserUser() {
  if (auth.user){
    return auth.user.token && auth.user.role === 'USER'
  }
  return false
}
function isUserAdmin() {
  if (auth.user){
    return auth.user.token && auth.user.role === 'ADMIN'
  }
  return false
}

function findRoute(){
  if(isUserAdmin()){
    router.push('/admin')
  }
  if (isUserUser()){
    router.push('/user')
  }
}

async function logout(){
  auth.logout()
  await router.push('/')
}


//
</script>
