<template>
  <el-scrollbar class="elScrollbar" ref="elScrollbar" @scroll="scroll">
    <!-- 消息列表展示区 -->
  </el-scrollbar>
</template>

<script>
import { ref, onMounted, inject, watch } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
export default {
  name: "Message",
  setup() {
    const router = useRouter();
    const elScrollbar = ref(null);
    //后代组件有一个 `inject` 选项来开始使用这些数据
    let messageScrollDistance = inject("messageScrollDistance"); //使用inject函数接收祖先组件使用provide函数传递的数据
    let user = inject("user");
    const scroll = ({ scrollTop }) => {
      //记录滚动的距离
      messageScrollDistance.value = scrollTop;
    };
    //创建一个向页面添加元素的函数
    /* 
    参数：
      src:联系人头像地址 
      contactName:联系人昵称
      newMessage:最新消息
      sandDate:发送时间
      contactId:联系人id
    */
    const addNode = (src, contactName, newMessage, sandDate, contactId) => {
      console.log("向页面添加元素的函数");
      //创建一个最外层的div，方便布局
      const box = document.createElement("div");
      box.onclick = () => {
        console.log("contactId", contactId);
        //使用replace将不会留下历史记录
        router.replace({
          name: "conversation",
          //路由传参
          query: { id: contactId },
        });
      };
      box.style = "width: 100vw; display: flex; align-items: center;";
      //创建展示头像的img
      const img = document.createElement("img");
      img.style =
        "width: 3rem; height: 3rem; margin: 0.5rem; border-radius: 0.3rem;";
      img.src = src;
      //appendChild是向元素中添加元素，添加到末尾
      box.appendChild(img);
      //创建一个div，用于方便布局
      const box2 = document.createElement("div");
      box2.style =
        "display: flex; justify-content: space-between; width: calc(100vw - 3.5rem); border-bottom: 0.4px solid rgba(205, 204, 204, 0.5); height: 4rem;";
      //创建一个div，用于方便布局
      const box3 = document.createElement("div");
      //创建一个展示昵称的div
      const name = document.createElement("div");
      name.style = "font-size: 1.1rem; margin-top: 0.5rem;";
      name.innerHTML = contactName;
      //appendChild是向元素中添加元素，添加到末尾
      box3.appendChild(name);
      //创建一个展示消息的div
      const message = document.createElement("div");
      message.style =
        "font-size: 0.8rem; color: rgb(183, 183, 183); width: 12rem; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;";
      message.innerHTML = newMessage;
      box3.appendChild(message);
      //appendChild是向元素中添加元素，添加到末尾
      box2.appendChild(box3);
      //创建一个展示时间的div
      const date = document.createElement("div");
      date.style =
        "font-size: 0.8rem; color: rgb(183, 183, 183); margin-right: 0.5rem; margin-top: 0.5rem;";
      date.innerHTML = sandDate;
      //appendChild是向元素中添加元素，添加到末尾
      box2.appendChild(date);
      //appendChild是向元素中添加元素，添加到末尾
      box.appendChild(box2);
      //appendChild是向元素中添加元素，添加到末尾
      elScrollbar.value.wrapRef.appendChild(box);
    };
    //组件加载完毕事件
    onMounted(() => {
      //document.documentElement.clientHeight为网页可见区域高
      //92.8的来源是顶部导航与底部导航的高度加起来任何乘以16，16为html的字体大小(px),因为使用了rem来设置高度
      elScrollbar.value.wrapRef.style.height =
        document.documentElement.clientHeight - 92.8 + "px";
      //更改滚动条位置
      elScrollbar.value.setScrollTop(messageScrollDistance.value);
      //清空原来的
      elScrollbar.value.wrapRef.innerHTML = "";
      for (let obj in user.chatLog) {
        //保存当前联系人信息的变量
        let contact = null;
        //通过循环获取当前联系人信息
        for (let i = 0; i < user.contact.length; i++) {
          if (obj == user.contact[i].id) {
            contact = user.contact[i];
          }
        }
        addNode(
          contact.profilePhoto,
          contact.username,
          user.chatLog[obj][user.chatLog[obj].length - 1].message,
          user.chatLog[obj][user.chatLog[obj].length - 1].date,
          contact.id
        );
      }
    });
    let serverMessage = inject("serverMessage");
    watch(serverMessage, () => {
      console.log("变化了", serverMessage.code);
      //清空原来的
      elScrollbar.value.wrapRef.innerHTML = "";
      //遍历并展示数据
      for (let obj in user.chatLog) {
        //保存当前联系人信息的变量
        let contact = null;
        //通过循环获取当前联系人信息
        for (let i = 0; i < user.contact.length; i++) {
          if (obj == user.contact[i].id) {
            contact = user.contact[i];
          }
        }
        addNode(
          contact.profilePhoto,
          contact.username,
          user.chatLog[obj][user.chatLog[obj].length - 1].message,
          user.chatLog[obj][user.chatLog[obj].length - 1].date,
          contact.id
        );
      }
    });
    return { elScrollbar, scroll };
  },
};
</script>

<style scoped>
</style>