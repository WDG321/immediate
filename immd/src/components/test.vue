<template>
  <input type="text" ref="message" />
  <button @click="send">发送消息</button>
  <button @click="close">关闭连接</button>
  聊天内容显示在下面
  <div class="box" ref="box"></div>
</template>

<script>
import { onMounted, ref } from "vue";
export default {
  name: "test",
  setup() {
    let box = ref(null);
    let message = ref(null);
    // 建立链接
    let ws = new WebSocket("ws://192.168.1.134/websocket");
    /* ws.readyState的值为以下其中之一：
        0 正在链接中
        1 已经链接并且可以通讯
        2 连接正在关闭
        3 连接已关闭或者没有链接成功 
        */
    //console.log(ws.readyState);
    //发送消息
    const send = () => {
      //ws.send()给服务器发送信息
      ws.send(JSON.stringify(message.value.value));
    };
    //关闭连接
    const close = () => {
      ws.close();
    };
    //连接成功事件
    ws.onopen = () => {
      console.log("连接成功");
    };
    //连接关闭事件
    ws.onclose = () => {
      console.log("连接关闭了");
    };
    //接收到服务端的消息事件
    ws.onmessage = (event) => {
      box.value.innerHTML += event.data;
    };
    return { box, send, message, close };
  },
};
</script>

<style scoped>
.box {
  width: 500px;
  height: 600px;
  background-color: #bfa;
}
</style>