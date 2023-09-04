<template>
  <Suspense>
    <template #default>
      <login-form class="bg-red-darken-4 rounded" id="reg-form"/>
    </template>
    <template #fallback>
      <loading class="bg-red-darken-4 rounded"/>
    </template>
  </Suspense>
</template>

<script setup>
import LoginForm from "@/components/LoginForm";
import Loading from "@/components/Loading";

</script>

<style scoped>

</style>
