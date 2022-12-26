<template>
  <TopBar></TopBar>
  <button @click="login">登录</button>
  <button @click="test">子路由测试</button>
  <router-view></router-view>
  <BottomBar class="BottomBar"></BottomBar>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
import TopBar from "./TopBar.vue";
import BottomBar from "./BottomBar.vue";

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
    return { login, test };
  },
  //注册组件
  components: {
    TopBar,
    BottomBar,
  },
};
</script>

<style scoped>
.BottomBar {
  position: absolute;
  bottom: 0;
}
</style>