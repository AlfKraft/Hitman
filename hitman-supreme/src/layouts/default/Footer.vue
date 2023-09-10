<template>
  <v-footer class="justify-center bg-transparent">
    <v-row class="d-flex footer justify-center align-center bg-transparent ma-14">
      <v-col></v-col>
      <v-col class="d-flex text-white justify-center align-center">
        <p>{{ new Date().getFullYear() }} — <strong>Hitman</strong></p>
      </v-col>
      <v-img src="@/assets/studentDays.png">
      </v-img>
    </v-row>
  </v-footer>
</template>

<style scoped>
.footer{
  height: 100px;
}
</style>
