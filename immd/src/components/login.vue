<template>
  <div class="logo">
    <div class="img"></div>
    <span class="text">IMMD</span>
  </div>
  <div class="input">
    <el-input v-model="input" placeholder="账号" class="username" />
    <el-input v-model="input" placeholder="密码" class="passworld" />
  </div>
</template>

<script>
import { ref } from "vue";
export default {
  name: "login",
  setup() {
    let input = ref("");
    return { input };
  },
};
</script>

<style scoped>
.logo {
  display: flex;
  align-items: center;
  position: absolute;
  transform: translate(-50%);
  left: 50%;
  top: 2rem;
}
.img {
  width: 5rem;
  height: 5rem;
  background-image: url(2233.png);
  background-size: 100%, 100%;
}
.text {
  font-size: 3rem;
}
.input {
  display: flex;
  flex-direction: column;
  position: absolute;
  transform: translate(-50%);
  left: 50%;
  top: 15rem;
}
.username {
  width: 15rem;
  height: 3.5rem;
}
:deep(.el-input__wrapper) {
  border-radius: 1.5rem;
}

.passworld {
  width: 15rem;
  height: 3.5rem;
}
</style>
