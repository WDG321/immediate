<template>
  <div v-if="vif">
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
    <div class="text2">
      没有账号？<span class="text3" @click="toRegistered">-->去注册</span>
    </div>
  </div>
  <div v-else>
    <div class="box">
      <div @click="returnLogin" class="returnLogin"></div>
      <div>注册</div>
      <!-- 空div用于占位 -->
      <div style="width: 1.7rem"></div>
    </div>
    <div class="box2">
      点击上传头像
      <el-upload
        class="upload"
        :limit="1"
        :auto-upload="false"
        accept=".jpg"
        list-type="picture-card"
        :on-change="onChange"
        :on-remove="onRemove"
        ref="upload"
      >
      </el-upload>
      <el-input
        placeholder="请输入用户名"
        class="registeredUserName"
        v-model="registeredInformation.username"
        @input="alter"
      />
      <el-input
        placeholder="请输入密码"
        class="registeredPassword"
        v-model="registeredInformation.password"
        show-password
        @input="alter2"
      />
      <el-input
        placeholder="请确认密码"
        class="registeredPassword2"
        v-model="registeredInformation.password2"
        show-password
      />
      <el-button type="primary" size="large" class="button2" @click="registered"
        >注册</el-button
      >
    </div>
  </div>
</template>

<script>
import { ref, onMounted, reactive } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
export default {
  name: "login",
  setup() {
    //封装一下错误提示信息:
    //参数:message:提示消息
    const errorTips = (message) => {
      const elMessageError = ElMessage.error(message);
      //ElMessage一旦有鼠标或者手指放上去，就不会自动消失了
      //设置定时器让它消失
      setTimeout(function () {
        elMessageError.close();
      }, 3000);
    };
    //封装一下成功提示信息:
    //参数:message:提示消息
    const successTips = (message) => {
      const elMessageSuccess = ElMessage.success(message);
      //ElMessage一旦有鼠标或者手指放上去，就不会自动消失了
      //设置定时器让它消失
      setTimeout(function () {
        elMessageSuccess.close();
      }, 3000);
    };
    let username = ref("");
    let password = ref("");
    //决定按钮是否为加载状态
    let loading = ref(false);
    let buttonText = ref("登录");
    const router = useRouter();
    const login = async () => {
      //判断账户密码是否为空，为空就退出登录函数
      if (username.value == "" || password.value == "") {
        return errorTips("账户名或密码不能为空");
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
          "Content-Type": "multipart/form-data",
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
        errorTips("账号名或密码错误");
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
    //用于控制显示登录页面还是注册页面
    let vif = ref(true);
    //显示注册页面
    const toRegistered = () => {
      vif.value = false;
    };
    //显示登录页面
    const returnLogin = () => {
      vif.value = true;
    };
    //注册信息对象
    const registeredInformation = reactive({
      username: null,
      password: null,
      password2: null,
      profilePhoto: null,
    });
    //文件上传组件
    const upload = ref(null);
    //文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用
    const onChange = (UploadFile, UploadFiles) => {
      console.log(UploadFile.raw.type);
      if (UploadFile.raw.type != "image/jpeg") {
        //手动移除文件
        upload.value.handleRemove(UploadFile);
        return errorTips("只能上传jpg格式的图片");
      }
      if (UploadFile.size > 1000000) {
        //手动移除文件
        upload.value.handleRemove(UploadFile);
        return errorTips("图片大小不能超过1M");
      }
      //UploadFile接收当前添加的文件对象
      //UploadFiles接收所以已经添加的文件数组
      console.log(UploadFile.size);
      console.log(UploadFiles);
      //保存上传的图片对象
      registeredInformation.profilePhoto = UploadFile.raw;
      console.log(6666666, registeredInformation.profilePhoto);
      //查看文件状态
      console.log(UploadFile.status);
      if (UploadFile.status == "ready") {
        console.log("文件添加完毕准备就绪");
        //获取上传框对象
        const box = document.querySelector(".el-upload--picture-card");
        console.log(box);
        //隐藏上传框
        box.style.display = "none";
      }
    };
    //文件移除时的钩子
    const onRemove = () => {
      //删除保存的文件对象
      registeredInformation.profilePhoto = null;
      console.log(6666666, registeredInformation.profilePhoto);
      //获取上传框对象
      const box = document.querySelector(".el-upload--picture-card");
      console.log(box);
      //显示上传框
      box.style.display = "inline-flex";
    };
    //点击注册按钮触发的函数
    const registered = async () => {
      console.log(registeredInformation);
      if (
        registeredInformation.username == null ||
        registeredInformation.username == ""
      ) {
        return errorTips("账号名不能为空");
      }
      if (
        registeredInformation.password == null ||
        registeredInformation.password == ""
      ) {
        return errorTips("密码不能为空");
      }
      if (registeredInformation.profilePhoto == null) {
        return errorTips("头像不能为空");
      }
      if (registeredInformation.password != registeredInformation.password2) {
        return errorTips("两次密码不一致");
      }
      //上传头像
      //配置请求信息
      let config = {
        method: "post",
        /* url: "http://192.168.1.134/loginApi", */
        url: "http://192.168.215.42/profilePhotoUploadApi",
        headers: {
          "Content-Type": "multipart/form-data",
        },
        data: {
          profilePhoto: registeredInformation.profilePhoto,
        },
      };
      //发起请求
      let response = await axios(config);
      console.log(response.data);
      //把返回的头像地址保存在注册信息对象里
      registeredInformation.profilePhoto = response.data;
      //删除password2属性,因为不需要上传
      delete registeredInformation.password2;
      //注册
      //配置请求信息
      let config2 = {
        method: "post",
        /* url: "http://192.168.1.134/loginApi", */
        url: "http://192.168.215.42/registeredApi",
        headers: {
          "Content-Type": "multipart/form-data",
        },
        data: {
          registeredInformation: JSON.stringify(registeredInformation),
        },
      };
      //发起请求
      let response2 = await axios(config2);
      if (response2.data == "注册成功") {
        successTips(response2.data);
        //复原对象
        registeredInformation.username = null;
        registeredInformation.password = null;
        registeredInformation.password2 = null;
        registeredInformation.profilePhoto = null;
        //显示登录页面
        vif.value = true;
      } else {
        errorTips(response2.data);
      }
    };
    //处理注册账号名输入框更改事件
    const alter = () => {
      //有空格就删除掉空格
      if (registeredInformation.username.indexOf(" ") != -1) {
        let str = registeredInformation.username.replace(/\s*/g, "");
        registeredInformation.username = str;
      }
    };
    //处理注册账号密码输入框更改事件
    const alter2 = () => {
      //有空格就删除掉空格
      if (registeredInformation.password.indexOf(" ") != -1) {
        let str = registeredInformation.password.replace(/\s*/g, "");
        registeredInformation.password = str;
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
      vif,
      toRegistered,
      returnLogin,
      onChange,
      onRemove,
      registered,
      registeredInformation,
      alter,
      alter2,
      upload,
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
  font-size: 1.2rem;
}
:deep(.el-input__inner) {
  /* 输入文本居中 */
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
.text2 {
  position: absolute;
  top: 35rem;
  transform: translate(-50%);
  left: 50%;
}
.text3 {
  color: #409eff;
}
.box {
  height: 2.3rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.returnLogin {
  /* 绘制三角箭头 */
  width: 0.7rem;
  height: 0.7rem;
  border-top: 0.1rem solid rgb(0, 0, 0);
  border-left: 0.1rem solid rgb(0, 0, 0);
  transform: rotate(-45deg);
  margin-left: 1rem;
}
.box2 {
  margin-top: 1rem;
  display: flex;
  align-items: center;
  flex-direction: column;
}
.registeredUserName,
.registeredPassword,
.registeredPassword2 {
  width: 65vw;
  height: 3rem;
  margin-bottom: 1.5rem;
}
.registeredUserName {
  margin-top: 1rem;
}
.button2 {
  font-size: 1.2rem;
  width: 4rem;
  height: 4rem;
  border-radius: 5rem;
}
.upload {
  /* margin-top: 1rem; */
}
/* 隐藏图片预览的放大镜按钮 */
:deep(.el-icon--zoom-in) {
  display: none;
}
/* 使删除按钮居中 */
:deep(.el-icon--delete) {
  position: absolute;
  transform: translate(-50%, -50%);
  left: 50%;
  top: 50%;
}
</style>