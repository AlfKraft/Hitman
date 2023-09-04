<template>
  <v-container>
    <v-alert class="bg-transparent" v-if="error">{{error}}</v-alert>
    <v-form v-if="!loading">
      <v-text-field v-model="loginData.username" :error-messages="errorUsername()" append-inner-icon="mdi-account" @input="usernameChange" type="username" label="Username"></v-text-field>
      <v-text-field v-model="loginData.password" :error-messages="errorPassword()" append-inner-icon="mdi-lock-outline" @input="passwordChange" type="password" label="Password"/>
    </v-form>
    <Loading v-else/>
    <v-btn v-if="!loading" class="mt-1"  @click="login">Log in</v-btn>
  </v-container>

</template>

<script setup>

import {computed, reactive, ref} from 'vue';
import { useAuthStore } from '@/stores/auth.store';
import {useVuelidate} from '@vuelidate/core'
import { required} from "@vuelidate/validators";
import Loading from '@/components/Loading'

const loading = ref(false)
const error = ref(null)

async function login(){
  loading.value = true
  const valid = await $v.value.$validate()
  if (valid){
    const authStore = useAuthStore();
    await authStore.login(loginData).catch(err => {
      error.value = err.response.data.message
    });
  }
  loading.value = false

}

const loginData = reactive({
  username:'',
  password:''
}
);

const rules = computed(()=> {
  return {
    username:{required},
    password:{required}
  }
});

const $v = useVuelidate(rules, loginData)

function usernameChange(){
  $v.value.username.$touch()
}
function passwordChange(){
  $v.value.password.$touch()
}
function errorUsername() {
  if ($v.value.username.$dirty) {
    $v.value.username.$validate()
    if ($v.value.username.$errors.length > 0) {
      return [$v.value.username.$errors[0].$message]
    }
  }
  return []
}
function errorPassword() {
  if ($v.value.password.$dirty) {
    $v.value.password.$validate()
    if ($v.value.password.$errors.length > 0) {
      return [$v.value.password.$errors[0].$message]
    }
  }
  return []
}

</script>

<style scoped>

</style>
