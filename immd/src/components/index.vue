<template>
  <TopBar :style="topBarVisibility"></TopBar>
  <router-view></router-view>
  <BottomBar class="BottomBar"></BottomBar>
</template>

<script>
import { inject, onBeforeUnmount } from "vue";
import TopBar from "./TopBar.vue";
import BottomBar from "./BottomBar.vue";
import axios from "axios";
export default {
  name: "index",
  setup() {
    //后代组件有一个 `inject` 选项来开始使用这些数据
    let topBarVisibility = inject("topBarVisibility"); //使用inject函数接收祖先组件使用provide函数传递的数据
    let chatLog = inject("chatLog");
    onBeforeUnmount(async () => {
      console.log("index销毁了");
      if (chatLog.length > 0) {
        //定义请求配置对象
        let config = {
          method: "post",
          url: "http://192.168.1.134/addChatLogApi",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
          data: {
            message: JSON.stringify(chatLog),
          },
        };
        //发送聊天记录
        await axios(config);
        //发送后需要清空,不然会出bug
        chatLog.length = 0;
      }
    });
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