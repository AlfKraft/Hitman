<template>
  <v-card :style="playerCardData.eliminated ? 'border: 5px solid #B71C1C' : 'border: 5px solid #00796B'" v-if="!loading">
    <v-img v-if="!errorMessage"
           :src="playerCardData.profileImage"
           height="400px"
           cover
    ><v-card-actions class="justify-end">
      <v-btn @click="closeProfileWindow" color="red-darken-4" icon="mdi-close-circle" size="x-large"></v-btn>
    </v-card-actions></v-img>
    <v-card-title class="text-wrap" v-if="!errorMessage">Name: {{ playerCardData.name }}</v-card-title>
    <v-card-text class="text-wrap" v-if="!errorMessage"> Email: {{playerCardData.email}}</v-card-text>
    <v-card-text class="text-wrap" v-if="!errorMessage"> Phone: {{playerCardData.phoneNumber}}</v-card-text>
    <v-card-text class="text-wrap" v-if="!errorMessage"> Birthdate: {{playerCardData.birthdate}}</v-card-text>
    <v-card-text class="text-wrap" v-if="!errorMessage"> Social media: {{playerCardData.facebook}}</v-card-text>
    <v-card-text class="text-wrap" v-if="!errorMessage"> School/Speciality: {{playerCardData.schoolAndSpeciality}}</v-card-text>
    <v-card-text class="text-wrap" v-if="!errorMessage"> Work: {{playerCardData.workPlace}}</v-card-text>
    <v-card-text class="text-wrap" v-if="!errorMessage"> Hobbies:  {{playerCardData.hobbies}}</v-card-text>
    <v-card-text class="text-wrap" v-if="!errorMessage"> Favorite places: {{playerCardData.favoritePlaces}}</v-card-text>
    <v-alert v-if="errorMessage">{{errorMessage}}</v-alert>
    <v-row class="ma-2">
      <v-btn>Approve</v-btn>
    </v-row>
  </v-card>
  <v-card class="mainComponent">
    <Loading v-if="loading"></Loading>
  </v-card>
</template>

<script setup>
import {onMounted, reactive, ref} from 'vue'
import {userStore} from "@/stores";
import Loading from "@/components/Loading";
const store = userStore();
const loading = ref(false)
const errorMessage = ref(null)

const emit = defineEmits(['close'])

const player = defineProps({
    id: Number
  }
)
const playerCardData = ref({
  profileImage: '',
  name: '',
  email: '',
  birthdate: '',
  facebook: '',
  schoolAndSpeciality: '',
  workPlace : '',
  hobbies: '',
  favoritePlaces: '',
  phoneNumber: '',
  approved: null,
  eliminated: null
  }
)


const playerData = reactive(
  {
    id: player.id
  }
)


onMounted( async ()=>{
  loading.value = true;
  await store.getPlayerData(playerData).then(response => {
      playerCardData.value.profileImage = response.data.profileImage
      playerCardData.value.name = response.data.name
      playerCardData.value.email = response.data.email
      playerCardData.value.birthdate = response.data.birthdate
      playerCardData.value.facebook = response.data.facebook
      playerCardData.value.schoolAndSpeciality = response.data.schoolAndSpeciality
      playerCardData.value.workPlace= response.data.workPlace
      playerCardData.value.hobbies = response.data.hobbies
      playerCardData.value.favoritePlaces = response.data.favoritePlaces
      playerCardData.value.phoneNumber = response.data.phoneNumber
      playerCardData.value.approved = response.data.approved
      playerCardData.value.eliminated = response.data.eliminated
    }
  ).catch(error => {
    console.log(error)
  })
  loading.value = false;
})

function closeProfileWindow(){
  emit('close')
}


</script>

<style scoped>

</style>
