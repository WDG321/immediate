<template>
  <div class="box">
    <div class="message" @click="messageClick">
      <div class="messageImg" ref="messageImg"></div>
      <div ref="messageText">消息</div>
    </div>
    <div class="contact" @click="contactClick">
      <div class="contactImg" ref="contactImg"></div>
      <div ref="contactText">联系人</div>
    </div>
    <div class="me" @click="meClick">
      <div class="meImg" ref="meImg"></div>
      <div ref="meText">我</div>
    </div>
  </div>
</template>

<script>
import { ref, watch, onMounted, inject } from "vue";
import { useRouter } from "vue-router";
export default {
  name: "BottomBar",
  setup() {
    const router = useRouter();
    const title = inject("title");
    const messageText = ref(null);
    const messageImg = ref(null);
    let messageMark = ref(null);
    const contactImg = ref(null);
    const contactText = ref(null);
    let contactMark = ref(null);
    const meImg = ref(null);
    const meText = ref(null);
    let meMark = ref(null);
    //控制初始选择
    onMounted(() => {
      messageMark.value = true;
      contactMark.value = false;
      meMark.value = false;
    });
    const messageClick = () => {
      messageMark.value = true;
      contactMark.value = false;
      meMark.value = false;
    };
    const contactClick = () => {
      contactMark.value = true;
      messageMark.value = false;
      meMark.value = false;
    };
    const meClick = () => {
      meMark.value = true;
      messageMark.value = false;
      contactMark.value = false;
    };
    watch(messageMark, () => {
      if (messageMark.value == true) {
        messageText.value.style.color = "#409eff";
        messageImg.value.style.backgroundImage = "url(/interactive_fill.png)";
        title.value = "消息";
        //使用replace将不会留下历史记录
        router.replace("/index/Message");
      } else {
        messageText.value.style.color = "rgb(0,0,0)";
        messageImg.value.style.backgroundImage = "url(/interactive.png)";
      }
    });
    watch(contactMark, () => {
      if (contactMark.value == true) {
        contactText.value.style.color = "#409eff";
        contactImg.value.style.backgroundImage = "url(/group_fill.png)";
        title.value = "联系人";
        //使用replace将不会留下历史记录
        router.replace("/index/Contact");
      } else {
        contactText.value.style.color = "rgb(0,0,0)";
        contactImg.value.style.backgroundImage = "url(/group.png)";
      }
    });
    //使用inject函数接收祖先组件使用provide函数传递的数据
    let topBarVisibility = inject("topBarVisibility");
    watch(meMark, () => {
      if (meMark.value == true) {
        meText.value.style.color = "#409eff";
        meImg.value.style.backgroundImage = "url(/people_fill.png)";
        title.value = "我";
        //隐藏topBar
        topBarVisibility.value = "visibility: hidden";
        //使用replace将不会留下历史记录
        router.replace("/index/Me");
      } else {
        //显示topBar
        topBarVisibility.value = "visibility: visible";
        meText.value.style.color = "rgb(0,0,0)";
        meImg.value.style.backgroundImage = "url(/people.png)";
      }
    });
    return {
      messageText,
      messageImg,
      messageClick,
      contactImg,
      contactText,
      contactClick,
      meImg,
      meText,
      meClick,
    };
  },
};
</script>

<style scoped>
.box {
  width: 100%;
  background-color: rgb(228, 226, 226);
  display: flex;
  height: 3.5rem;
  font-size: 0.8rem;
  justify-content: space-around;
}
.message,
.me,
.contact {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.messageImg,
.contactImg,
.meImg {
  width: 2rem;
  height: 2rem;
  background-size: 100% 100%;
}
</style>