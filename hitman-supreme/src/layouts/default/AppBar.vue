<template>
  <v-app-bar class="bg-red-darken-4 d-flex">
    <v-app-bar-title>
      HITMAN
    </v-app-bar-title>
    <p class="mr-6" v-if="isUserUser()">Codename: {{username}}</p>
    <div v-if="!mobile">
    <v-btn class="mr-2" v-if="!(isUserAdmin() || isUserUser())" to="/">Home</v-btn>
    <v-btn class="mr-2" v-if="!(isUserAdmin() || isUserUser())" to="/about">About</v-btn>
    <v-btn class="mr-2" v-if="!(isUserAdmin() || isUserUser())" to="/register">Register</v-btn>
    <v-btn class="mr-2" v-if="!(isUserAdmin() || isUserUser())" to="/login">Log In</v-btn>
    <v-btn class="mr-2" v-if="isUserAdmin() || isUserUser()" @click="findRoute">MyHub</v-btn>
    <v-btn class="mr-2" v-if="isUserUser()" to="/missions">Missions</v-btn>
    <v-btn class="mr-2" v-if="isUserUser()" to="/target">Target</v-btn>
    <v-btn class="mr-2" v-if="isUserUser()" to="/checkpoints">Checkpoints</v-btn>
    <v-btn class="mr-2" v-if="isUserAdmin()" to="/missionhub">MissionHub</v-btn>
    <v-btn class="mr-2" v-if="isUserAdmin()" to="/userhub">UserHUB</v-btn>
    <v-btn class="mr-2" v-if="(isUserAdmin() || isUserUser())" @click="logout">Logout</v-btn>
    </div>
    <v-btn v-if="mobile"
    >
      <v-icon>mdi-menu</v-icon>

      <v-menu activator="parent">
        <v-list>
          <v-list-item class="justify-center" v-if="!(isUserAdmin() || isUserUser())"
          >
            <v-btn class="bg-red-darken-4" to="/">Home</v-btn>
          </v-list-item>
          <v-list-item class="justify-center" v-if="!(isUserAdmin() || isUserUser())">
            <v-btn class="bg-red-darken-4" to="/about">About</v-btn>
          </v-list-item>
          <v-list-item class="justify-center" v-if="!(isUserAdmin() || isUserUser())">
            <v-btn class="bg-red-darken-4" to="/register">Register</v-btn>
          </v-list-item>
          <v-list-item class="justify-center" v-if="!(isUserAdmin() || isUserUser())">
            <v-btn class="bg-red-darken-4" to="/login">Log In</v-btn>
          </v-list-item>
          <v-list-item class="justify-center" v-if="isUserAdmin() || isUserUser()">
            <v-btn class="bg-red-darken-4" @click="findRoute">MyHub</v-btn>
          </v-list-item>
          <v-list-item class="justify-center" v-if="isUserUser()">
            <v-btn class="bg-red-darken-4" to="/missions">Missions</v-btn>
          </v-list-item>
          <v-list-item class="justify-center" v-if="isUserUser()">
            <v-btn class="bg-red-darken-4" to="/target">Target</v-btn>
          </v-list-item>
          <v-list-item class="justify-center" v-if="isUserUser()">
            <v-btn class="bg-red-darken-4" to="/checkpoints">Checkpoints</v-btn>
          </v-list-item>
          <v-list-item class="justify-center" v-if="isUserAdmin()">
            <v-btn class="bg-red-darken-4"  to="/missionhub">MissionHub</v-btn>
          </v-list-item>
          <v-list-item class="justify-center" v-if="isUserAdmin()">
            <v-btn class="bg-red-darken-4" to="/userhub">UserHUB</v-btn>
          </v-list-item>
          <v-list-item class="justify-center" v-if="(isUserAdmin() || isUserUser())">
            <v-btn class="bg-red-darken-4" @click="logout">Logout</v-btn>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-btn>
  </v-app-bar>
</template>

<script setup>
import {useRouter} from "vue-router";
import { useAuthStore } from '@/stores';
import {computed, ref} from "vue";
import { useDisplay } from 'vuetify'

const { mobile } = useDisplay()
const router = useRouter();
const auth = useAuthStore()
const username = ref('')

computed(
  isUserUser(),
  isUserAdmin()
)


function isUserUser() {
  if (auth.user){
    username.value = auth.user.username
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
