<template>
  <v-container  v-if="!loading">
    <v-alert class="bg-transparent" v-if="errorMessage">{{errorMessage}}</v-alert>
    <v-form v-if="!registered">
      <v-file-input
        label="Profile picture"
        accept="image/*"
        variant="filled"
        @change="onFileUpload"
        append-inner-icon="mdi-camera"
      ></v-file-input>
      <v-text-field v-model="regData.firstName" :error-messages="errorFirstName()" @input="firstNameChange" label="First name"></v-text-field>
      <v-text-field v-model="regData.lastName" :error-messages="errorLastName()" @input="lastNameChange" label="Last Name"></v-text-field>
      <v-text-field v-model="regData.email" :error-messages="errorEmail()" append-inner-icon="mdi-email" type="email" @input="emailChange" label="Email"></v-text-field>
      <v-text-field v-model="regData.birthdate" :error-messages="errorBirthdate()" type="date" pattern="dd-mm-yyyy" @input="dateChange" label="Birth date"></v-text-field>
      <v-text-field v-model="regData.facebook" :error-messages="errorFacebook()" @input="facebookChange" label="Social media link"></v-text-field>
      <v-text-field v-model="regData.schoolAndSpeciality" :error-messages="errorSchoolAndSpeciality()" @input="schoolAndSpecialityChange" label="School and Speciality"></v-text-field>
      <v-text-field v-model="regData.workPlace" :error-messages="errorWorkplace()" @input="workplaceChange" label="Work place"></v-text-field>
      <v-text-field v-model="regData.hobbies" :error-messages="errorHobbies()" @input="hobbiesChange" label="Hobbies"></v-text-field>
      <v-text-field v-model="regData.mostVisitedPlaces" :error-messages="errorMostVisitedPlaces()" @input="mostVisitedPlacesChange" label="Your favorite places to go to"></v-text-field>
      <v-tooltip text="Only for the organizers">
        <template v-slot:activator="{ props }">
      <v-text-field v-bind="props" v-model="regData.phoneNumber" :error-messages="errorPhoneNumber()" @input="phoneNumberChange" label="Phone number"></v-text-field>
        </template>
      </v-tooltip>
      <v-text-field v-model="regData.username" :error-messages="errorUsername()" append-inner-icon="mdi-account" @input="usernameChange" type="username" label="Username"></v-text-field>
      <v-text-field v-model="regData.password" :error-messages="errorPassword()" append-inner-icon="mdi-lock-outline" @input="passwordChange" type="password" label="Password"/>
      <v-text-field v-model="regData.repeatedPassword" :error-messages="errorConfirmPassword()" append-inner-icon="mdi-lock-alert-outline" @input="confirmPasswordChange" type="password" label="Repeat Password"/>
      <v-btn @click="register">Register</v-btn>
    </v-form>
    <v-btn v-if="registered" to="/login">Log in</v-btn>
  </v-container>
  <v-container v-else>
    <Loading/>
  </v-container>
</template>
<script setup>

import {inject, computed, reactive, ref} from 'vue';
import {useVuelidate} from '@vuelidate/core'
import {required, email, minLength, sameAs, helpers} from '@vuelidate/validators'
import Loading from "@/components/Loading";
const axios = inject('axios');
const errorMessage = ref(null)
const loading = ref(false)
const registered = ref(false);
const formData = new FormData()

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


const rules = computed(()=> {
  return {
    firstName: {required, minlength: minLength(3)},
    lastName:{required, minlength: minLength(3)},
    email:{required, email},
    birthdate: {required},
    facebook: {required},
    schoolAndSpeciality: {required},
    workPlace: {required},
    hobbies: {required},
    mostVisitedPlaces: {required},
    phoneNumber: {required, minlength: minLength(6)},
    username:{required, minlength: minLength(6)},
    password:{required,minlength: minLength(6), passwordValidation: helpers.withMessage("Must contain ! or ? or # or + and a number!", passwordValidation)},
    repeatedPassword:{required, minlength: minLength(3), sameAs: sameAs(regData.password)},
  }
});

const regData = reactive({
  profileImage: null,
  firstName: '',
  lastName:'',
  email:'',
  birthdate: '',
  facebook: '',
  schoolAndSpeciality: '',
  workPlace: '',
  hobbies: '',
  mostVisitedPlaces: '',
  phoneNumber: '',
  username:'',
  password:'',
  repeatedPassword:'',
});

function onFileUpload(event){
  regData.profileImage = event.target.files[0]
}

const $v = useVuelidate(rules, regData)
async function register(){
  const valid = await $v.value.$validate()
  if (valid){
    if (!regData.profileImage){
      errorMessage.value = "Image is mandatory."
    }
    else {
      loading.value = true
      formData.append('profileImage', regData.profileImage)
      formData.append('firstName', regData.firstName)
      formData.append('lastName', regData.lastName)
      formData.append('email', regData.email)
      formData.append('birthdate', regData.birthdate)
      formData.append('facebook', regData.facebook)
      formData.append('schoolAndSpeciality', regData.schoolAndSpeciality)
      formData.append('workPlace', regData.workPlace)
      formData.append('hobbies', regData.hobbies)
      formData.append('mostVisitedPlaces', regData.mostVisitedPlaces)
      formData.append('phoneNumber', regData.phoneNumber)
      formData.append('username', regData.username)
      formData.append('password', regData.password)
      formData.append('repeatedPassword', regData.repeatedPassword)
      await axios.post("/register", formData)
        .then(() => {
          errorMessage.value = "You are registered. Proceed to the log in tab."
          registered.value = true
        })
        .catch(error => {
          errorMessage.value = error.response.data.message
          regData.profileImage = null
          formData.delete('profileImage')
          formData.delete('firstName')
          formData.delete('lastName')
          formData.delete('email')
          formData.delete('birthdate')
          formData.delete('facebook')
          formData.delete('schoolAndSpeciality')
          formData.delete('workPlace')
          formData.delete('hobbies')
          formData.delete('mostVisitedPlaces')
          formData.delete('phoneNumber')
          formData.delete('username')
          formData.delete('password')
          formData.delete('repeatedPassword')
        })
    }
  }
  loading.value = false
}
function emailChange(){
  $v.value.email.$touch()
}
function firstNameChange(){
  $v.value.firstName.$touch()
}
function lastNameChange(){
  $v.value.lastName.$touch()
}
function dateChange(){
  $v.value.birthdate.$touch()
}
function facebookChange(){
  $v.value.facebook.$touch()
}
function schoolAndSpecialityChange(){
  $v.value.schoolAndSpeciality.$touch()
}
function workplaceChange(){
  $v.value.workPlace.$touch()
}
function hobbiesChange(){
  $v.value.hobbies.$touch()
}
function mostVisitedPlacesChange(){
  $v.value.mostVisitedPlaces.$touch()
}
function phoneNumberChange(){
  $v.value.phoneNumber.$touch()
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

function errorEmail() {
  if ($v.value.email.$dirty) {
  $v.value.email.$validate()
  if ($v.value.email.$errors.length > 0) {
    return [$v.value.email.$errors[0].$message]
  }
}
  return []
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

function errorBirthdate() {
  if ($v.value.birthdate.$dirty) {
    $v.value.birthdate.$validate()
    if ($v.value.birthdate.$errors.length > 0) {
      return [$v.value.birthdate.$errors[0].$message]
    }
  }
  return []
}
function errorFacebook(){
  if ($v.value.facebook.$dirty) {
    $v.value.facebook.$validate()
    if ($v.value.facebook.$errors.length > 0) {
      return [$v.value.facebook.$errors[0].$message]
    }
  }
  return []
}
function errorSchoolAndSpeciality(){
  if ($v.value.schoolAndSpeciality.$dirty) {
    $v.value.schoolAndSpeciality.$validate()
    if ($v.value.schoolAndSpeciality.$errors.length > 0) {
      return [$v.value.schoolAndSpeciality.$errors[0].$message]
    }
  }
  return []
}
function errorWorkplace(){
  if ($v.value.workPlace.$dirty) {
    $v.value.workPlace.$validate()
    if ($v.value.workPlace.$errors.length > 0) {
      return [$v.value.workPlace.$errors[0].$message]
    }
  }
  return []
}
function errorHobbies(){
  if ($v.value.hobbies.$dirty) {
    $v.value.hobbies.$validate()
    if ($v.value.hobbies.$errors.length > 0) {
      return [$v.value.hobbies.$errors[0].$message]
    }
  }
  return []
}
function errorMostVisitedPlaces(){
  if ($v.value.mostVisitedPlaces.$dirty) {
    $v.value.mostVisitedPlaces.$validate()
    if ($v.value.mostVisitedPlaces.$errors.length > 0) {
      return [$v.value.mostVisitedPlaces.$errors[0].$message]
    }
  }
  return []
}
function errorPhoneNumber(){
  if ($v.value.phoneNumber.$dirty) {
    $v.value.phoneNumber.$validate()
    if ($v.value.phoneNumber.$errors.length > 0) {
      return [$v.value.phoneNumber.$errors[0].$message]
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
