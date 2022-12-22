<template>
  index
  <button @click="login">登录</button>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
export default {
  name: "index",
  setup() {
    const router = useRouter();
    let login = () => {
      router.push("login");
    };
    return { login };
  },
};
</script>

<style>
</style>