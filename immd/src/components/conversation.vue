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
import {
  inject,
  reactive,
  ref,
  onMounted,
  onBeforeMount,
  onBeforeUnmount,
  watch,
} from "vue";
export default {
  name: "conversation",
  setup() {
    //输入框内容
    let inputValue = ref("");
    const user = inject("user");
    const router = useRouter();
    //存储标记对象,用于控制底部导航的选择状态
    const mark = inject("mark");
    //滚动区域
    const elScrollbar = ref(null);
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
    });

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
    //组件将要销毁事件
    onBeforeUnmount(() => {
      console.log("将要销毁事件");
      //解绑window.onresize事件，不然在其他页面会出现bug
      window.onresize = null;
    });
    //获取WebSocket链接对象
    const ws = inject("ws");
    const sendMessage = () => {
      console.log(ws);
      //输入为空就不往下执行了
      if (inputValue.value == "") {
        return ElMessage.error("输入不能为空");
      }
      //连接失败就不往下执行了
      if (ws === false) {
        return ElMessage.error("网络连接失败,请刷新重试");
      }
      //定义消息对象
      const ms = {
        contactId: contact.id,
        message: inputValue.value,
      };
      //发送消息
      ws.send(JSON.stringify(ms));
      //创建并添加元素
      const box03 = document.createElement("div");
      box03.style =
        "display: flex; align-items: center; justify-content: flex-end";
      const text = document.createElement("div");
      text.innerHTML = inputValue.value;
      //appendChild是向元素中添加元素，添加到末尾
      box03.appendChild(text);
      const contactProfilePhoto = document.createElement("img");
      contactProfilePhoto.src = user.profilePhoto;
      contactProfilePhoto.style =
        "width: 2.5rem; height: 2.5rem; border-radius: 0.5rem; margin: 0.5rem;";
      //appendChild是向元素中添加元素，添加到末尾
      box03.appendChild(contactProfilePhoto);
      //appendChild是向元素中添加元素，添加到末尾
      elScrollbar.value.wrapRef.appendChild(box03);
      inputValue.value = "";
    };
    let serverMessage = inject("serverMessage");
    let box03 = ref(null);
    //监听消息变化
    watch(serverMessage, () => {
      console.log("消息变化了");
      //创建并添加元素
      const box03 = document.createElement("div");
      box03.style = "display: flex; align-items: center;";
      const contactProfilePhoto = document.createElement("img");
      contactProfilePhoto.src = contact.profilePhoto;
      contactProfilePhoto.style =
        "width: 2.5rem; height: 2.5rem; border-radius: 0.5rem; margin: 0.5rem;";
      //appendChild是向元素中添加元素，添加到末尾
      box03.appendChild(contactProfilePhoto);
      const text = document.createElement("div");
      text.innerHTML = serverMessage.message;
      //appendChild是向元素中添加元素，添加到末尾
      box03.appendChild(text);
      //appendChild是向元素中添加元素，添加到末尾
      elScrollbar.value.wrapRef.appendChild(box03);
    });
    return {
      returnMessage,
      contact,
      elScrollbar,
      inputValue,
      sendMessage,
      box03,
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
</style>