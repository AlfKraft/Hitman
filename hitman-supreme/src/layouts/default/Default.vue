<template>
  <v-app class="bg-transparent">
    <default-bar />
    <v-main class="mt-10">
      <router-view />
    </v-main>
    <default-footer />
  </v-app>
</template>

<script setup>
  import DefaultBar from './AppBar.vue'
  import DefaultFooter from './Footer'
</script>

<style scoped>

</style>
