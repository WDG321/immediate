<template>
  <div class="box" ref="box">
    <div class="text">{{ title }}</div>
    <div class="search"></div>
    <div class="addTo"></div>
  </div>
</template>

<script>
import { ref, inject } from "vue";
export default {
  name: "TopBar",
  setup() {
    //后代组件有一个 `inject` 选项来开始使用这些数据
    let title = inject("title"); //使用inject函数接收祖先组件使用provide函数传递的数据
    const box = ref(null);
    return { title, box };
  },
};
</script>

<style scoped>
.box {
  width: 100%;
  height: 2.3rem;
  background-color: rgb(228, 226, 226);
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.search {
  width: 1.3rem;
  height: 1.3rem;
  background-image: url(/sousuo.png);
  background-size: 100%, 100%;
  margin-right: 0.8rem;
}
.addTo {
  width: 1.5rem;
  height: 1.5rem;
  background-image: url(/jiahao.png);
  background-size: 100%, 100%;
  margin-right: 0.8rem;
}
.text {
  position: absolute;
  transform: translate(-50%);
  left: 50%;
  font-size: 1.2rem;
}
</style>