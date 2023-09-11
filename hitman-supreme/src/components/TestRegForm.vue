<template>
  <v-container v-if="!loading">
    <v-alert class="bg-transparent" v-if="errorMessage">{{errorMessage}}</v-alert>
    <v-form>
      <v-text-field v-model="regData.firstName" :error-messages="errorFirstName()" @input="firstNameChange" label="First name"></v-text-field>
      <v-text-field v-model="regData.lastName" :error-messages="errorLastName()" @input="lastNameChange" label="Last Name"></v-text-field>
      <v-text-field v-model="regData.aboutInfo" :error-messages="errorAboutInfo()" @input="aboutInfoChange" label="Tell us about yourself"></v-text-field>
      <v-text-field v-model="regData.username" :error-messages="errorUsername()" append-inner-icon="mdi-account" @input="usernameChange" type="username" label="Username"></v-text-field>
      <v-text-field v-model="regData.password" :error-messages="errorPassword()" append-inner-icon="mdi-lock-outline" @input="passwordChange" type="password" label="Password"/>
      <v-text-field v-model="regData.repeatedPassword" :error-messages="errorConfirmPassword()" append-inner-icon="mdi-lock-alert-outline" @input="confirmPasswordChange" type="password" label="Repeat Password"/>
      <v-btn @click="register">Register</v-btn>
    </v-form>
  </v-container>
  <v-container v-else>
    <Loading/>
  </v-container>
</template>
<script setup>
import {inject, computed, reactive, ref} from 'vue';
import {useVuelidate} from '@vuelidate/core'
import {required, minLength, sameAs, helpers} from '@vuelidate/validators'
import Loading from "@/components/Loading";



const axios = inject('axios');
const registered = ref(false);
const errorMessage = ref(null)
const loading = ref(false)
const passwordValidation = (value) => {
  let number = false;
  for (let i = 1; i < 10; i++) {
    if (value.includes(i)){
      number = true
      break
    }
  }
  return (value.includes("!") || value.includes("?") || value.includes("#") || value.includes("+")) && number
}



const regData = reactive({
  firstName: '',
  lastName:'',
  aboutInfo: '',
  username:'',
  password:'',
  repeatedPassword:'',
});

const rules = computed(()=> {
  return {
    firstName: {required, minlength: minLength(3)},
    lastName:{required, minlength: minLength(3)},
    aboutInfo: {required},
    username:{required, minlength: minLength(6)},
    password:{required,minlength: minLength(6), passwordValidation: helpers.withMessage("Must contain ! or ? or # or + and a number!", passwordValidation)},
    repeatedPassword:{required, minlength: minLength(3), sameAs: sameAs(regData.password)},
  }
});


const $v = useVuelidate(rules, regData)

async function register(){
  const valid = await $v.value.$validate()
  if (valid){
    loading.value = true
    await axios.post("/register", regData)
      .then(() => {
          errorMessage.value = "You are registered. Proceed to the log in tab."
      })
      .catch(error => {
        errorMessage.value = error.response.data.message
      })
  }
  loading.value = false

}


function firstNameChange(){
  $v.value.firstName.$touch()
}
function lastNameChange(){
  $v.value.lastName.$touch()
}

function aboutInfoChange(){
  $v.value.aboutInfo.$touch()
}

function usernameChange(){
  $v.value.username.$touch()
}
function passwordChange(){
  $v.value.password.$touch()
}
function confirmPasswordChange(){
  $v.value.repeatedPassword.$touch()
}


function errorFirstName() {
  if ($v.value.firstName.$dirty) {
    $v.value.firstName.$validate()
    if ($v.value.firstName.$errors.length > 0) {
      return [$v.value.firstName.$errors[0].$message]
    }
  }
  return []
}

function errorLastName() {
  if ($v.value.lastName.$dirty) {
    $v.value.lastName.$validate()
    if ($v.value.lastName.$errors.length > 0) {
      return [$v.value.lastName.$errors[0].$message]
    }
  }
  return []
}

function errorAboutInfo(){
  if ($v.value.aboutInfo.$dirty) {
    $v.value.aboutInfo.$validate()
    if ($v.value.aboutInfo.$errors.length > 0) {
      return [$v.value.aboutInfo.$errors[0].$message]
    }
  }
  return []
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
function errorConfirmPassword() {
  if ($v.value.repeatedPassword.$dirty) {
    $v.value.repeatedPassword.$validate()
    if ($v.value.repeatedPassword.$errors.length > 0) {
      return [$v.value.repeatedPassword.$errors[0].$message]
    }
  }
  return []
}

</script>

<style scoped>

</style>

