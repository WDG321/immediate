<template>
  <div class="box">
    <div @click="returnMessage" class="returnMessage"></div>
    <div>{{ contact.username }}</div>
    <img src="/gengduo.png" class="img" />
  </div>
  <!-- 滚动框 -->
  <el-scrollbar class="elScrollbar" ref="elScrollbar" :noresize="true">
    <!-- 聊天信息展示区 -->
  </el-scrollbar>
  <div class="box02">
    <input v-model="inputValue" class="input" />
    <el-button type="primary" class="button" @click="sendMessage"
      >发送</el-button
    >
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import moment from "moment";
import {
  inject,
  reactive,
  ref,
  onMounted,
  onBeforeMount,
  onBeforeUnmount,
  watch,
} from "vue";
import axios from "axios";
export default {
  name: "conversation",
  setup() {
    //输入框内容
    let inputValue = ref("");
    //自己的信息
    const user = inject("user");
    console.log("conversation", user);
    const router = useRouter();
    //标记对象,用于控制底部导航的选择状态
    const mark = inject("mark");
    //滚动区域
    const elScrollbar = ref(null);
    //返回之前的页面
    const returnMessage = () => {
      mark.messageMark = true;
      mark.contactMark = false;
      mark.meMark = false;
      //使用replace将不会留下历史记录
      router.replace("/index/Message");
    };
    //接收路由传参,获取点击的联系人的id值
    const id = JSON.parse(router.currentRoute.value.query.id);
    //定义联系人信息对象
    let contact = reactive({});
    //获取联系人信息
    for (let i = 0; i < user.contact.length; i++) {
      if (user.contact[i].id == id) {
        contact = user.contact[i];
        break;
      }
    }
    //获取初始窗口高度
    let initHeight =
      document.documentElement.clientHeight || document.body.clientHeight;
    //组件挂载完毕事件
    onMounted(() => {
      //document.documentElement.clientHeight为网页可见区域高
      //92.8的来源是顶部导航与底部导航的高度加起来任何乘以16，16为html的字体大小(px),因为使用了rem来设置高度
      elScrollbar.value.wrapRef.style.height = initHeight - 93 + "px";
      console.log("conversationonMounted", user);
      //判断不为空就进行遍历操作,为空就代表是第一次与该联系人聊天还没有数据,就不需要遍历了
      //这里的id是个变量,user.chatLog是个obj
      if (user.chatLog[id] != undefined) {
        //决定放哪边的变量
        let direction = null;
        //消息内容
        let message = null;
        //头像地址
        let src = null;
        //遍历user.chatLog里的数据并展示到页面上
        for (let i = 0; i < user.chatLog[id].length; i++) {
          //通过id判断是谁发的信息从而决定放哪边
          if (user.chatLog[id][i].id == user.id) {
            direction = "right";
            message = user.chatLog[id][i].message;
            src = user.profilePhoto;
          } else {
            direction = "left";
            message = user.chatLog[id][i].message;
            src = contact.profilePhoto;
          }
          addBox(direction, message, src);
        }
      }
      console.log(user.chatLog[id] == undefined);
      //更改滚动条位置,使之滚动到底
      //scrollHeight为获取对象的滚动高度
      elScrollbar.value.setScrollTop(elScrollbar.value.wrapRef.scrollHeight);
    });
    //定义一个向页面中添加元素的函数
    /* 
      参数：
        direction：方向，left或者right，left代表对方发送的信息，right代表自己发送的信息 
        message：聊天信息
        src：头像地址
    */
    const addBox = (direction, message, src) => {
      //创建一个div,用于包裹元素方便布局
      const box = document.createElement("div");
      //创建一个展示信息的div
      const text = document.createElement("div");
      //展示聊天信息
      text.innerHTML = message;
      //创建一个展示头像的img
      const photo = document.createElement("img");
      photo.src = src;
      photo.style =
        "width: 2.5rem; height: 2.5rem; border-radius: 0.5rem; margin: 0.5rem;";
      if (direction == "left") {
        box.style = "display: flex; align-items: center;";
        //appendChild是向元素中添加元素，添加到末尾
        //因为左右问题，所以添加顺序需要反着来
        box.appendChild(photo);
        box.appendChild(text);
      } else if (direction == "right") {
        box.style =
          "display: flex; align-items: center; justify-content: flex-end";
        //appendChild是向元素中添加元素，添加到末尾
        //因为左右问题，所以添加顺序需要反着来
        box.appendChild(text);
        box.appendChild(photo);
      } else {
        console.error("direction只能是left或者right");
        return;
      }
      elScrollbar.value.wrapRef.appendChild(box);
    };
    //window.onresize事件会在窗口或框架被调整大小时发生。
    window.onresize = function () {
      //键盘弹起或者收起来引起的窗口高度的变化，再次获取下窗口高度和进入页面的时候获取的的窗口进行对比
      let resizeHeight =
        document.documentElement.clientHeight || document.body.clientHeight;
      if (resizeHeight < initHeight) {
        //当前高度小于初始高度就判定为软键盘弹起，在此做一些操作
        //alert("软键盘弹起")
        console.log("软键盘弹起");
        ///变化滚动区域高度，因为软键盘弹起时，窗口高度会发生变化，滚动区域高度一旦超出变化后的窗口高度时布局就会混乱
        elScrollbar.value.wrapRef.style.height =
          initHeight - (initHeight - resizeHeight) - 93 + "px";
        //更改滚动条位置,使之滚动到底
        //scrollHeight为获取对象的滚动高度
        elScrollbar.value.setScrollTop(elScrollbar.value.wrapRef.scrollHeight);
      } else {
        //当前高度大于初始高度就判定为软键盘收起，在此做一些操作
        //alert("软键盘收起")
        console.log("软键盘收起");
        //把滚动区域高度变回去
        elScrollbar.value.wrapRef.style.height = initHeight - 93 + "px";
      }
    };
    //存储聊天记录的对象
    let chatLog = inject("chatLog");
    //组件将要销毁事件
    onBeforeUnmount(async () => {
      //解绑window.onresize事件，不然在其他页面会出现bug
      window.onresize = null;
      if (JSON.stringify(chatLog.value) != "{}") {
        //定义请求配置对象
        let config = {
          method: "post",
          url: "http://192.168.1.134/addChatLogApi",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
          data: {
            message: JSON.stringify(chatLog.value),
          },
        };
        //发送聊天记录
        await axios(config);
        //发送后需要清空,不然会出bug
        chatLog.value = {};
        console.log("组件将要销毁时发送成功", chatLog.value);
      }
    });

    //获取WebSocket链接对象
    const ws = inject("ws");
    const sendMessage = () => {
      console.log("sendMessage触发了");
      //输入为空就不往下执行了
      if (inputValue.value == "") {
        return ElMessage.error("输入不能为空");
      }
      //连接失败就不往下执行了
      if (ws === false) {
        const elMessageError = ElMessage.error("网络连接失败,请刷新重试");
        //ElMessage一旦有鼠标或者手指放上去，就不会自动消失了
        //设置定时器让它消失
        setTimeout(function () {
          elMessageError.close();
        }, 3000);
        return;
      }
      //定义发送的消息对象
      const ms = {
        id: contact.id, //需要发送信息的联系人的id
        message: inputValue.value,
        date: moment().format("lll"), // 时间
      };
      //发送消息
      ws.send(JSON.stringify(ms));
      //创建并添加元素到页面
      addBox("right", inputValue.value, user.profilePhoto);
      //更改滚动条位置,使之滚动到底
      //scrollHeight为获取对象的滚动高度
      elScrollbar.value.setScrollTop(elScrollbar.value.wrapRef.scrollHeight);
      //向聊天记录里面添加信息
      if (JSON.stringify(chatLog.value) != "{}") {
        for (let k in chatLog.value) {
          console.log("k", k);
          //找到联系人id才添加
          if (k == contact.id) {
            chatLog.value[k].push({
              message: inputValue.value,
              id: user.id,
              date: moment().format("lll"),
            });
          }
        }
      } else {
        chatLog.value[contact.id] = [
          {
            message: inputValue.value,
            id: user.id,
            date: moment().format("lll"),
          },
        ];
      }
      console.log("向聊天记录里面添加信息", chatLog.value);
      //清空输入框信息
      inputValue.value = "";
    };
    let serverMessage = inject("serverMessage");
    let box = ref(null);
    //监听消息变化
    watch(serverMessage, () => {
      console.log("聊天页面的监听触发了", serverMessage);
      //创建并添加元素到页面
      addBox("left", serverMessage.message, contact.profilePhoto);
      //更改滚动条位置,使之滚动到底
      //scrollHeight为获取对象的滚动高度
      elScrollbar.value.setScrollTop(elScrollbar.value.wrapRef.scrollHeight);
    });
    return {
      returnMessage,
      contact,
      elScrollbar,
      inputValue,
      sendMessage,
      user,
    };
  },
};
</script>

<style scoped>
.elScrollbar {
  background-color: rgba(205, 204, 204, 0.5);
}
.box {
  height: 2.3rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: rgba(205, 204, 204, 0.5);
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
.img {
  width: 1.2rem;
  height: 1.2rem;
  margin-right: 1rem;
}
.box02 {
  width: 100%;
  height: 3.5rem;
  background-color: rgba(205, 204, 204, 0.2);
  display: flex;
  justify-content: space-between;
  align-items: center;
  /* position: absolute;
  bottom: 0; */
}
.input {
  outline: none;
  width: 60vw;
  border: 0;
  height: 2.3rem;
  padding-left: 1rem;
  /* 设置圆角 */
  border-radius: 0.2rem;
  margin-left: 1rem;
}
.button {
  margin-right: 1rem;
  width: 20vw;
}
.userMessageBox {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.contactMessageBox {
  display: flex;
  align-items: center;
}
.userMessageText {
}
.userProfilePhoto {
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 0.5rem;
  margin: 0.5rem;
}
.contactProfilePhoto {
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 0.5rem;
  margin: 0.5rem;
}
.contactMessageText {
}
</style>