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
      v-model="password"
      placeholder="密码"
      class="passworld"
      size="large"
      show-password
      :disabled="loading"
      maxlength="12"
      @input="passwordInput"
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
import axios from "axios";
export default {
  name: "login",
  setup() {
    let username = ref("");
    let password = ref("");
    let loading = ref(false);
    let buttonText = ref("登录");
    const router = useRouter();
    const login = async () => {
      //判断账户密码是否为空，为空就退出登录函数
      if (username.value == "" || password.value == "") {
        const elMessageError = ElMessage.error("账号名或密码不能为空");
        //ElMessage一旦有鼠标或者手指放上去，就不会自动消失了
        //设置定时器让它消失
        setTimeout(function () {
          elMessageError.close();
        }, 3000);
        return;
      }
      //转为加载中状态
      loading.value = true;
      //清空按钮文本
      buttonText.value = "";
      //配置请求信息
      let config = {
        method: "post",
        /* url: "http://192.168.1.134/loginApi", */
        url: "http://192.168.215.42/loginApi",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        data: {
          username: username.value,
          password: password.value,
        },
      };
      //发起请求
      let response = await axios(config);
      //判断是否登录成功
      if (response.data == true) {
        //播放加载中动画
        //ElLoading.service();
        //登录成功跳到主页，使用replace将不会留下历史记录
        router.replace("/");
      } else {
        //登录失败恢复原状态
        loading.value = false;
        buttonText.value = "登录";
        const elMessageError = ElMessage.error("账号名或密码错误");
        //ElMessage一旦有鼠标或者手指放上去，就不会自动消失了
        //设置定时器让它消失
        setTimeout(function () {
          elMessageError.close();
        }, 3000);
      }
    };
    //处理账号Input内容更改事件
    const usernameInput = () => {
      //有空格就删除掉空格
      if (username.value.indexOf(" ") != -1) {
        let str = username.value.replace(/\s*/g, "");
        username.value = str;
      }
    };
    //处理密码Input内容更改事件
    const passwordInput = () => {
      //有空格就删除掉空格
      if (password.value.indexOf(" ") != -1) {
        let str = password.value.replace(/\s*/g, "");
        password.value = str;
      }
    };
    return {
      username,
      password,
      loading,
      login,
      buttonText,
      usernameInput,
      passwordInput,
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
  background-image: url(/2233.png);
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
  /* 设置圆角 */
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
