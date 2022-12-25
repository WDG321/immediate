<template>
  <div class="logo">
    <div class="img"></div>
    <span class="text">IMMD</span>
  </div>
  <div class="input">
    <el-input
      v-model="username"
      placeholder="账号"
      class="username"
      size="large"
      :disabled="loading"
      maxlength="12"
      @input="usernameInput"
    />
    <el-input
      v-model="passworld"
      placeholder="密码"
      class="passworld"
      size="large"
      show-password
      :disabled="loading"
      maxlength="12"
      @input="passworldInput"
    />
  </div>
  <el-button
    type="primary"
    :loading="loading"
    class="button"
    size="large"
    @click="login"
    >{{ buttonText }}</el-button
  >
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
export default {
  name: "login",
  setup() {
    let username = ref("");
    let passworld = ref("");
    let loading = ref(false);
    let buttonText = ref("登录");
    const router = useRouter();
    const login = () => {
      //转为加载中状态
      loading.value = true;
      //清空按钮文本
      buttonText.value = "";
      //模拟登录
      setTimeout(() => {
        if (username.value != "" && passworld.value != "") {
          ElMessage({ message: "登录成功", type: "success" });
          //登录成功跳到主页
          router.push("/");
        } else {
          //登录失败恢复原状态
          loading.value = false;
          buttonText.value = "登录";
          ElMessage.error("账号名或密码错误");
        }
      }, 1000);
    };
    //处理账号Input内容更改事件
    const usernameInput = () => {
      //有空格就删除掉空格
      if (username.value.indexOf(" ") != -1) {
        let str = username.value.replace(/\s*/g, "");
        username.value = str;
      }
      if (passworld.value.indexOf(" ") != -1) {
        let str = passworld.value.replace(/\s*/g, "");
        passworld.value = str;
      }
    };
    //处理密码Input内容更改事件
    const passworldInput = () => {
      //有空格就删除掉空格
      if (passworld.value.indexOf(" ") != -1) {
        let str = passworld.value.replace(/\s*/g, "");
        passworld.value = str;
      }
    };
    return {
      username,
      passworld,
      loading,
      login,
      buttonText,
      usernameInput,
      passworldInput
    };
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
  top: 10rem;
}
.username {
  width: 15rem;
  height: 3.5rem;
}
.passworld {
  margin-top: 1rem;
  width: 15rem;
  height: 3.5rem;
}
:deep(.el-input__wrapper) {
  border-radius: 1.5rem;
  font-size: 1.5rem;
}
:deep(.el-input__inner) {
  text-align: center;
}
.button {
  position: absolute;
  top: 25rem;
  transform: translate(-50%);
  left: 50%;
  font-size: 1.5rem;
  width: 4rem;
  height: 4rem;
  border-radius: 5rem;
}
</style>
