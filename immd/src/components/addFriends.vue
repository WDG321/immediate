<template>
  <div class="boxmax" v-if="vif">
    <div class="box">
      <div @click="returnMessage" class="returnMessage"></div>
      <div>添加朋友</div>
      <!-- 空div用于占位 -->
      <div style="width: 1.7rem"></div>
    </div>
    <el-input
      v-model="input"
      placeholder="请输入id"
      class="input"
      oninput="value=value.replace(/[^\d]/g,'')"
    />
    <el-button
      type="primary"
      class="button"
      size="large"
      @click="search"
      :loading="loading"
      >{{ buttonText }}</el-button
    >
  </div>
  <div class="boxmax" v-else>
    <div class="box2">
      <div @click="returnSearch" class="returnMessage"></div>
    </div>
    <div class="box3">
      <img class="img" :src="userInfo.profilePhoto" />
      <div class="text">{{ userInfo.username }}</div>
    </div>
    <div class="add" @click="add">添加到联系人</div>
  </div>
</template>

<script>
import { inject, ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
export default {
  name: "addFriends",
  setup() {
    //用于控制显示搜索用户页面还是显示展示用户详情添加用户页面
    let vif = ref(true);
    //保存查询到的用户信息
    let userInfo = ref({});
    const router = useRouter();
    //标记对象,用于控制底部导航的选择状态
    const mark = inject("mark");
    const returnMessage = () => {
      mark.messageMark = true;
      mark.contactMark = false;
      mark.meMark = false;
      //使用replace将不会留下历史记录
      router.replace("/index/Message");
    };
    //输入框内容
    let input = ref("");
    //决定按钮是否为加载状态
    let loading = ref(false);
    let buttonText = ref("搜索");
    //点击搜索触发的函数
    const search = async () => {
      console.log(input.value);
      if (input.value == "") {
        const elMessageError = ElMessage.error("id不能为空");
        //ElMessage一旦有鼠标或者手指放上去，就不会自动消失了
        //设置定时器让它消失
        setTimeout(function () {
          elMessageError.close();
        }, 3000);
        return;
      }
      //更改为加载中状态
      buttonText.value = "";
      loading.value = true;
      //配置请求信息
      let config = {
        method: "post",
        /* url: "http://192.168.1.134/loginApi", */
        url: "http://192.168.215.42/idQueryUserApi",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        data: {
          id: input.value,
        },
      };
      //发起请求
      let response = await axios(config);
      if (response.data != "") {
        //保存查询到的用户信息
        userInfo.value = response.data;
        //更改为未加载状态
        buttonText.value = "搜索";
        loading.value = false;
        //展示用户详情
        vif.value = false;
      } else {
        //更改为未加载状态
        buttonText.value = "搜索";
        loading.value = false;
        input.value = "";
        const elMessageError = ElMessage.error("该用户不存在");
        //ElMessage一旦有鼠标或者手指放上去，就不会自动消失了
        //设置定时器让它消失
        setTimeout(function () {
          elMessageError.close();
        }, 3000);
      }
      console.log(response.data);
    };
    const returnSearch = () => {
      //展示搜索联系人页面
      input.value = "";
      vif.value = true;
    };
    //执行添加联系人
    const add = async () => {
      console.log("添加联系人");
      //配置请求信息
      let config = {
        method: "post",
        /* url: "http://192.168.1.134/loginApi", */
        url: "http://192.168.215.42/addContactApi",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        data: {
          id: input.value,
        },
      };
      //发起请求
      let response = await axios(config);
      if (response.data == true) {
        alert("添加成功");
        //刷新页面以更新数据
        location.reload();
      } else {
        const elMessageError = ElMessage.error("添加失败");
        //ElMessage一旦有鼠标或者手指放上去，就不会自动消失了
        //设置定时器让它消失
        setTimeout(function () {
          elMessageError.close();
        }, 3000);
      }
    };
    return {
      returnMessage,
      input,
      search,
      loading,
      buttonText,
      vif,
      returnSearch,
      userInfo,
      add,
    };
  },
};
</script>

<style scoped>
.boxmax {
  width: 100vw;
  height: 100vh;
  background-color: rgba(205, 204, 204, 0.5);
}
.box {
  height: 2.3rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.returnMessage {
  /* 绘制三角箭头 */
  width: 0.7rem;
  height: 0.7rem;
  border-top: 0.1rem solid rgb(0, 0, 0);
  border-left: 0.1rem solid rgb(0, 0, 0);
  transform: rotate(-45deg);
  margin-left: 1rem;
}
:deep(.el-input__inner) {
  /* 输入文本居中 */
  text-align: center;
}
:deep(.el-input__wrapper) {
  /* 设置圆角 */
  border-radius: 0.7rem;
  font-size: 1rem;
}
.input {
  width: 90vw;
  height: 2rem;
  margin-top: 1rem;
  position: absolute;
  transform: translate(-50%);
  left: 50%;
}
.button {
  position: absolute;
  top: 6.3rem;
  transform: translate(-50%);
  left: 50%;
  font-size: 1rem;
  width: 3rem;
  height: 3rem;
  border-radius: 5rem;
}
.box2 {
  height: 2.3rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: rgb(255, 255, 255);
}
.box3 {
  display: flex;
  align-items: center;
  background-color: rgb(255, 255, 255);
}
.img {
  width: 3.2rem;
  height: 3.2rem;
  margin: 1.3rem;
  /* 设置圆角 */
  border-radius: 0.5rem;
}
.text {
  font-size: 1.3rem;
  font-weight: 900;
}
.add {
  margin-top: 0.5rem;
  background-color: rgb(255, 255, 255);
  font-size: 1rem;
  font-weight: 900;
  text-align: center;
  padding-top: 0.5rem;
  padding-bottom: 0.5rem;
  color: #409eff;
}
</style>