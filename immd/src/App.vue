<template>
  <router-view></router-view>
</template>

<script>
import { ref, reactive, provide, onMounted, watch, computed } from "vue";
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
      chatLog: null, //聊天记录
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
    provide("user", user);
    provide("mark", mark); //provide接收两个参数，参数1为数据起名，参数二为需要传入的数据
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
      let value = await axios(config);
      if (value.data != "") {
        //保存用户数据
        user.username = value.data.username;
        user.id = value.data.id;
        user.profilePhoto = value.data.profilePhoto;
        user.contact = JSON.parse(value.data.contact);
        user.chatLog = JSON.parse(value.data.chatLog);
      }
      console.log("app", user);
    });
    //保存接收到服务端WebSocket的消息对象
    //code用于保证能监听到变了，因为watch监听不到重新赋相同的值
    let serverMessage = reactive({
      code: true,
      message: null,
      id: null,
      date: null,
    });
    provide("serverMessage", serverMessage);
    //定义存储聊天记录的对象
    let chatLog = ref({});
    provide("chatLog", chatLog);
    //保存WebSocket链接对象
    let ws = null;
    try {
      //建立WebSocket链接
      ws = new WebSocket("ws://192.168.1.134/websocket");
      //接收到服务端的消息事件
      ws.onmessage = (event) => {
        //更新服务端的消息对象
        serverMessage.message = JSON.parse(event.data).message;
        serverMessage.id = JSON.parse(event.data).id;
        serverMessage.date = JSON.parse(event.data).date;
        serverMessage.code = !serverMessage.code;
        console.log("event", serverMessage);
      };
      provide("ws", ws);
    } catch (error) {
      //连接失败就赋值为false
      ws = false;
      provide("ws", ws);
      //ElMessage.error("网络连接失败,请刷新重试");
      const elMessageError = ElMessage.error(error.message);
      //ElMessage一旦有鼠标或者手指放上去，就不会自动消失了
      //设置定时器让它消失
      setTimeout(function () {
        elMessageError.close();
      }, 3000);
    }
    //监听消息变化
    watch(serverMessage, () => {
      //向聊天记录里面添加信息
      if (JSON.stringify(chatLog.value) != "{}") {
        chatLog.value[serverMessage.id].push({
          message: serverMessage.message,
          id: serverMessage.id,
          date: serverMessage.date,
        });
      } else {
        chatLog.value[serverMessage.id] = [
          {
            message: serverMessage.message,
            id: serverMessage.id,
            date: serverMessage.date,
          },
        ];
      }
      //还需要更新user里的聊天记录,以保证本地的记录实时更新
      if (JSON.stringify(user.chatLog) != "{}") {
        user.chatLog[serverMessage.id].push({
          message: serverMessage.message,
          id: serverMessage.id,
          date: serverMessage.date,
        });
      } else {
        user.chatLog[serverMessage.id] = [
          {
            message: serverMessage.message,
            id: serverMessage.id,
            date: serverMessage.date,
          },
        ];
      }
    });

    //定义离开或刷新页面时执行的函数
    function logData() {
      //值为hidden时此时页面对用户不可见. 即文档处于背景标签页或者窗口处于最小化状态，或者操作系统正处于 ‘锁屏状态’
      if (document.visibilityState === "hidden") {
        console.log("logData", JSON.stringify(chatLog.value));
        if (JSON.stringify(chatLog.value) != "{}") {
          const data = new FormData();
          data.append("message", JSON.stringify(chatLog.value));
          //navigator.sendBeacon() 方法可用于通过 HTTP POST 将少量数据 异步 传输到 Web 服务器。
          //它主要用于将统计数据发送到 Web 服务器，同时避免了用传统技术（如：XMLHttpRequest）发送分析数据的一些问题。
          navigator.sendBeacon("http://192.168.1.134/addChatLogApi", data);
          //发送后需要清空,不然会出bug
          chatLog.value = {};
        }
      }
      //关闭WebSocket连接
      //ws.close();
    }
    //离开或刷新页面事件
    document.addEventListener("visibilitychange", logData);
    return {};
  },
};
</script>

<style>
</style>
