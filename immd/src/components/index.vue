<template>
  <TopBar></TopBar>
  <router-view></router-view>
  <BottomBar class="BottomBar"></BottomBar>
</template>

<script>
import { ref, provide } from "vue";
import { useRouter } from "vue-router";
import TopBar from "./TopBar.vue";
import BottomBar from "./BottomBar.vue";

export default {
  name: "index",
  setup() {
    const router = useRouter();
    let title = ref(null);
    //父组件有一个 `provide` 选项来提供数据
    provide("title", title); //provide接收两个参数，参数1为数据起名，参数二为需要传入的数据
    return { };
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