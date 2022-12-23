<template>
  index
  <el-button type="primary">Primary</el-button>
  <button @click="login">登录</button>
  <button @click="test">子路由测试</button>
  <router-view></router-view>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
export default {
  name: "index",
  setup() {
    const router = useRouter();
    let login = () => {
      router.push("/login");
    };
    let test = () => {
      router.push("/index/test");
    };
    return { login,test };
  },
};
</script>

<style>
</style>