<template>
  <TopBar :style="topBarVisibility"></TopBar>

  <!-- vue3.0配置缓存组件，不进行销毁 -->
  <!-- <router-view v-slot="{ Component }">
    <keep-alive>
      <component
        :is="Component"
        :key="$route.name"
        v-if="$route.meta.keepAlive"
      />
    </keep-alive>
    <component
      :is="Component"
      :key="$route.name"
      v-if="!$route.meta.keepAlive"
    />
  </router-view> -->
  <router-view></router-view>

  <BottomBar class="BottomBar"></BottomBar>
</template>

<script>
import { inject } from "vue";
import TopBar from "./TopBar.vue";
import BottomBar from "./BottomBar.vue";

export default {
  name: "index",
  setup() {
    //后代组件有一个 `inject` 选项来开始使用这些数据
    let topBarVisibility = inject("topBarVisibility"); //使用inject函数接收祖先组件使用provide函数传递的数据
    return { topBarVisibility };
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