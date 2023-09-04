<template>
  <v-footer class="justify-center bg-transparent ma-14">
    <v-row class="justify-center bg-transparent ma-14">
      <v-col></v-col>
      <v-col class="text-white d-flex justify-center align-center">
        {{ new Date().getFullYear() }} — <strong>Hitman</strong>
      </v-col>
      <v-col class="d-flex justify-center align-center">
        <v-img src="@/assets/studentDays.png">
        </v-img>
      </v-col>
    </v-row>
  </v-footer>
</template>

<style scoped>
.footer{
  height: 100px;
}
</style>
