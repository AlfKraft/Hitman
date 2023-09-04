<template>
  <v-card>
    <v-img
      src="@/assets/8436886_hitman.jpg"
      height="400px"
      cover
    ></v-img>
    <v-card-title>TARGET</v-card-title>
    <v-card-subtitle>{{ target.name }}</v-card-subtitle>
    <v-card-text>
      {{target.info}}
    </v-card-text>
    <v-card-actions>
      <v-text-field v-model="target.captureCode" prepend-inner-icon="mdi-barcode" label="Capture code" variant="underlined"></v-text-field>
    </v-card-actions>
  </v-card>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {userStore} from "@/stores";

const target = ref({
  name: '',
  info: '',
  captureCode: ''
  }
)

onMounted(async ()=>{
  const store = userStore();
  await store.getTarget().then(response => {
    target.value.name = response.data.name;
    target.value.info = response.data.targetInfo;
  }).catch(error => {
    target.value.info = error.response.data.message
  })
})

</script>

<style scoped>

</style>
