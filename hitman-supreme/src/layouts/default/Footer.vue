<template>
  <v-footer class="mt-5 justify-center bg-transparent">
    <v-row class="d-flex footer justify-center align-center bg-transparent ma-14">
      <v-col class="text-white justify-center align-center">
        <v-row>Organizers:</v-row>
        <v-row>Wiljar Lobjakas - wiljar.lobjakas@gmail.com</v-row>
        <v-row>Rasmus Rohtla - rasmusr16@hotmail.com</v-row>
        <v-row>Alfred Kraft - alfredkraftofficial@gmail.com</v-row>
      </v-col>
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
