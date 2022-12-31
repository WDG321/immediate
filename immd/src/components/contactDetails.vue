<template>
  <div class="box05">
    <div class="box">
      <div class="box01">
        <div @click="returnSontact" class="returnSontact"></div>
      </div>
      <div class="box02">
        <img :src="contact.profilePhoto" class="profilePhoto" />
        <div>
          <div class="username">{{ contact.username }}</div>
          <div class="id">id：{{ contact.id }}</div>
        </div>
      </div>
    </div>
    <div class="box03" @click="sendMessage">
      <div class="box04">
        <img src="/xiaoxi_o.png" class="sendMessageImg" />
        <div class="sendMessage">发消息</div>
      </div>
    </div>
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import { inject, onMounted, reactive } from "vue";
export default {
  name: "contactDetails",
  setup() {
    const user = inject("user");
    const router = useRouter();
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
    const returnSontact = () => {
      //使用replace将不会留下历史记录
      router.replace("/index/Contact");
    };
    //前往聊天页面
    const sendMessage = () => {
      //使用replace将不会留下历史记录
      router.replace({
        path: "/conversation",
        //路由传参
        query: { id: `${contact.id}` },
      });
    };
    return { returnSontact, contact, sendMessage };
  },
};
</script>

<style scoped>
.box05 {
  background-color: rgba(205, 204, 204, 0.5);
  height: 100vh;
}
.box {
  /* 设置下边框 */
  border-bottom: 0.5px solid rgba(205, 204, 204, 0.5);
  background-color: rgb(255, 255, 255);
}
.box01 {
  height: 2.3rem;
  display: flex;
  align-items: center;
}
.returnSontact {
  /* 绘制三角箭头 */
  width: 0.7rem;
  height: 0.7rem;
  border-top: 0.1rem solid rgb(0, 0, 0);
  border-left: 0.1rem solid rgb(0, 0, 0);
  transform: rotate(-45deg);
  margin-left: 1rem;
}
.box02 {
  display: flex;
  /* background-color: aqua; */
}
.profilePhoto {
  width: 4rem;
  height: 4rem;
  /* 设置圆角 */
  border-radius: 1.5rem;
  padding: 1rem;
}
.username {
  font-size: 1.3rem;
  /* 加粗字体 */
  font-weight: 900;
  padding-top: 1rem;
}
.id {
  color: rgb(148, 148, 148);
}
.box03 {
  width: 100vw;
  height: 3.5rem;
  display: flex;
  align-items: center;
  /* 设置下边框 */
  border-bottom: 0.5px solid rgba(205, 204, 204, 0.5);
  background-color: rgb(255, 255, 255);
}
.box04 {
  display: flex;
  align-items: center;
  /* 设置居中 */
  position: absolute;
  transform: translate(-50%);
  left: 50%;
}
.sendMessageImg {
  width: 1.5rem;
  height: 1.5rem;
}
.sendMessage {
  color: #409eff;
  /* 加粗字体 */
  font-weight: 700;
}
</style>