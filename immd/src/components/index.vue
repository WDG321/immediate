<template>
  <TopBar></TopBar>

  <!-- vue3.0配置缓存组件，不进行销毁 -->
  <router-view v-slot="{ Component }">
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
  </router-view>

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
    //设置顶部导航栏的标题名
    let title = ref(null);
    //设置message页面的滚动距离
    let scrollDistance = ref(0);
    //父组件有一个 `provide` 选项来提供数据给后代组件
    provide("title", title); //provide接收两个参数，参数1为数据起名，参数二为需要传入的数据
    provide("scrollDistance", scrollDistance); 
    return {};
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