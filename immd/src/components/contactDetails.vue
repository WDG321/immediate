<template>
  我是联系人详情页面
  <button @click="button">返回联系人页面</button>
</template>

<script>
import { useRouter } from "vue-router";
export default {
  name: "contactDetails",
  setup() {
    const router = useRouter();
    const button = () => {
      //使用replace将不会留下历史记录
      router.replace("/index/Contact");
    };
    return { button };
  },
};
</script>

<style scoped>
</style>