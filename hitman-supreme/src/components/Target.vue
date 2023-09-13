<template>
  <v-card>
    <v-img
      :src="target.image"
      height="400px"
      cover
    ></v-img>
    <v-card-title>TARGET</v-card-title>
    <v-card-title v-if="!errorMessage"> Name: {{ target.name }}</v-card-title>
    <v-card-subtitle v-if="!errorMessage"> Social media: {{target.facebook}} </v-card-subtitle>
    <v-row></v-row>
    <v-card-subtitle v-if="!errorMessage"> School/Speciality:</v-card-subtitle>
    <v-card-text v-if="!errorMessage">
      {{target.schoolAndSpeciality}}
    </v-card-text>
    <v-card-subtitle v-if="!errorMessage"> Work:</v-card-subtitle>
    <v-card-text v-if="!errorMessage">
      {{target.workPlace}}
    </v-card-text>
    <v-card-subtitle v-if="!errorMessage"> Hobbies:</v-card-subtitle>
    <v-card-text v-if="!errorMessage">
      {{target.hobbies}}
    </v-card-text>
    <v-card-subtitle v-if="!errorMessage"> Favorite places:</v-card-subtitle>
    <v-card-text v-if="!errorMessage">
      {{target.favoritePlaces}}
    </v-card-text>
    <v-alert v-if="errorMessage">{{errorMessage}}</v-alert>
    <v-card-actions v-if="!errorMessage">
      <v-row class="ma-3 d-flex justify-center align-center ">
      <v-text-field v-if="!loading" append-inner-icon="mdi-skull-scan" @click:append-inner="eliminate()" v-model="eliminatePlayerData.eliminationCode" prepend-inner-icon="mdi-barcode" label="Capture code" variant="underlined"></v-text-field>
        <v-progress-circular v-else indeterminate size="30"></v-progress-circular>
      </v-row>
    </v-card-actions>
  </v-card>
</template>

<script setup>
import {onMounted, reactive, ref} from 'vue'
import {userStore} from "@/stores";
const store = userStore();
const loading = ref(false)
const errorMessage = ref(null)

const target = ref({
  name: '',
  facebook: '',
  schoolAndSpeciality: '',
  workPlace: '',
  hobbies: '',
  favoritePlaces: '',
  image: ''
  }
)
const eliminatePlayerData = reactive({
  eliminationCode: ''
}
)


onMounted(async ()=>{

  await store.getTarget().then(response => {
    target.value.name = response.data.name;
    target.value.facebook = response.data.facebook
    target.value.schoolAndSpeciality = response.data.schoolAndSpeciality
    target.value.workPlace = response.data.workPlace
    target.value.hobbies = response.data.hobbies
    target.value.favoritePlaces = response.data.favoritePlaces
    target.value.image = response.data.image
  }).catch(error => {
    errorMessage.value = error.response.data.message
  })
})


async function eliminate() {
  loading.value = true;
  await store.eliminateTarget(eliminatePlayerData).then(response => {
    errorMessage.value = 'Target eliminated. Your next target is ' + response.data.name
    target.value.name = response.data.name;
    target.value.facebook = response.data.facebook
    target.value.schoolAndSpeciality = response.data.schoolAndSpeciality
    target.value.workPlace = response.data.workPlace
    target.value.hobbies = response.data.hobbies
    target.value.favoritePlaces = response.data.favoritePlaces
    target.value.image = response.data.image
  }).catch(error => {
    errorMessage.value = error.response.data.message
  })
  eliminatePlayerData.eliminationCode = ''
  loading.value = false;
  await new Promise(r => setTimeout(r, 3000));
  errorMessage.value = null
}
</script>

<style scoped>

</style>
