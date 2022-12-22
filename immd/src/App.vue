<template>
  <div>{{ hello }}</div>
</template>

<script>
import { ref } from "vue";
export default {
  name: "App",
  setup() {
    let hello = ref("hello world");
    return { hello };
  },
};
</script>

<style>
</style>
