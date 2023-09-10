<template>
  <router-view/>
</template>

<script setup>
//
</script>

<style>
@import url("https://fonts.googleapis.com/css?family=Inconsolata:400,700&subset=latin-ext");

@font-face {
  font-family: "OCRA";
  src: url("./assets/OCRA.woff") format("woff"),
    /* Chrome 6+, Firefox 3.6+, IE 9+, Safari 5.1+ */ url("./assets/OCRA.ttf")
  format("truetype"); /* Chrome 4+, Firefox 3.5, Opera 10+, Safari 3—5 */
}

body {
  margin: 0;
  background: #000;
  background-image: url("./assets/plus.svg");
}

input {
  letter-spacing: 1px;
  font-size: 16px;
  padding: 4px 0 0 0;
}

#nprogress .bar {
  background: #ffffff !important;
  height: 5px !important;
}

#nprogress .spinner-icon {
  border-top-color: #ffffff !important;
  border-left-color: #ffffff !important;
}

#nprogress .spinner {
  display: block !important;
  position:  relative !important;
  top: 21px !important;
  left: 90px !important;
}


input:-webkit-autofill,
input:-webkit-autofill:hover,
input:-webkit-autofill:focus select:-webkit-autofill,
select:-webkit-autofill:hover,
select:-webkit-autofill:focus {
  -webkit-text-fill-color: white;
  -webkit-box-shadow: 0 0 0px 1000px #1a1a1a inset;
  transition: background-color 5000s ease-in-out 0s;
}

#app {
  font-family: "Inconsolata", monospace;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background-image: url("./assets/plus.svg");
}

.mainComponent{
  width: 420px;
}
</style>
