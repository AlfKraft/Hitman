<template>
  <v-card>
    <v-img
      src="@/assets/8436886_hitman.jpg"
      height="400px"
      cover
    ></v-img>
    <v-card-title>TARGET</v-card-title>
    <v-card-subtitle> Name: {{ target.name }}</v-card-subtitle>
    <v-card-subtitle> Info: </v-card-subtitle>
    <v-card-text>
      {{target.info}}
    </v-card-text>
    <v-alert v-if="errorMesssage">{{errorMesssage}}</v-alert>
    <v-card-actions>
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
const errorMesssage = ref(null)

const target = ref({
  name: '',
  info: '',
  }
)
const eliminatePlayerData = reactive({
  eliminationCode: ''
}
)


onMounted(async ()=>{

  await store.getTarget().then(response => {
    target.value.name = response.data.name;
    target.value.info = response.data.targetInfo;
  }).catch(error => {
    target.value.info = error.response.data.message
  })
})

async function eliminate() {
  loading.value = true;
  await store.eliminateTarget(eliminatePlayerData).then(response => {
    errorMesssage.value = 'Target eliminated. Your next target is ' + response.data.name
    target.value.name = response.data.name;
    target.value.info = response.data.targetInfo;
  }).catch(error => {
    console.log(error)
    errorMesssage.value = error.response.data.message
  })
  eliminatePlayerData.eliminationCode = ''
  loading.value = false;
  await new Promise(r => setTimeout(r, 3000));
  errorMesssage.value = null
}
</script>

<style scoped>

</style>
