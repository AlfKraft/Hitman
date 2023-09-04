<template>
  <v-container>
    <v-row>
      <v-col class="d-flex justify-center bg-transparent">
        <Start class="homeView"/>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import Start from "@/components/Start"
</script>

<style scoped lang="scss">

.homeView {
  border: thin solid darkred;
  width: 420px;
}

</style>
