<template>
  <button @click="objbutton">更改obj</button>
</template>

<script>
import { reactive, onMounted, provide, watch, ref } from "vue";
export default {
  name: "test",
  setup() {
    let obj = ref({
      i: 1,
    });
    console.log(obj);
    watch(
      obj,
      () => {
        console.log("obj变化了", obj.value);
      },
      { deep: true }
    );
    let objbutton = () => {
      obj.value.a = "lll";
      //obj.q=5
      console.log("test更改obj了", obj.value);
    };
    return { objbutton };
  },
};
</script>

<style>
</style>