<template>
  <router-view></router-view>
</template>

<script>
import { ref, reactive, provide, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
export default {
  name: "App",
  setup() {
    //存储用户信息的对象
    const user = reactive({
      username: null, //昵称
      id: null, //id
      profilePhoto: null, //头像地址
      contact: null, //联系人列表
    });
    //存储标记对象,用于控制底部导航的选择状态
    const mark = reactive({
      messageMark: true,
      contactMark: false,
      meMark: false,
    });
    //设置message页面的滚动距离
    let messageScrollDistance = ref(0);
    //设置Contact页面的滚动距离
    let contactScrollDistance = ref(0);
    //设置Me页面的滚动距离
    let meScrollDistance = ref(0);
    //设置顶部导航栏的标题名
    let title = ref(null);
    //设置TopBar是否隐藏,默认不隐藏
    let topBarVisibility = ref("visibility: visible");

    //父组件有一个 `provide` 选项来提供数据给后代组件
    provide("user", user); //provide接收两个参数，参数1为数据起名，参数二为需要传入的数据
    provide("mark", mark);
    provide("title", title);
    provide("messageScrollDistance", messageScrollDistance);
    provide("contactScrollDistance", contactScrollDistance);
    provide("meScrollDistance", meScrollDistance);
    provide("topBarVisibility", topBarVisibility);
    onMounted(async () => {
      //创建请求配置对象
      let config = {
        method: "post",
        url: "http://192.168.1.134/meApi",
      };
      //发送请求获取用户数据
      let response = await axios(config);
      console.log(response.data);
      if (response.data != "") {
        //保存用户数据
        user.username = response.data.username;
        user.id = response.data.id;
        user.profilePhoto = response.data.profilePhoto;
        user.contact = JSON.parse(response.data.contact);
        console.log(user.contact);
      }
    });
    //保存接收到服务端的消息对象
    let serverMessage = reactive({
      message: "",
      code: true,
    });
    provide("serverMessage", serverMessage);
    //保存WebSocket链接对象
    let ws = null;
    try {
      //建立WebSocket链接
      ws = new WebSocket("ws://192.168.1.134/websocket");
      //接收到服务端的消息事件
      ws.onmessage = (event) => {
        //更新服务端的消息对象
        serverMessage.message = event.data;
        serverMessage.code = !serverMessage.code;
      };
      provide("ws", ws);
    } catch {
      //连接失败就赋值为false
      ws = false;
      provide("ws", ws);
      ElMessage.error("网络连接失败,请刷新重试");
      console.log(ws, "ws");
    }
    return {};
  },
};
</script>

<style>
</style>
