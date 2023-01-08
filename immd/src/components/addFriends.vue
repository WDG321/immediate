<template>
  <div class="boxmax">
    <div class="box">
      <div @click="returnMessage" class="returnMessage"></div>
      <div>添加朋友</div>
      <div></div>
    </div>
  </div>
</template>

<script>
import { inject } from "vue";
import { useRouter } from "vue-router";
export default {
  name: "addFriends",
  setup() {
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
    return { returnMessage };
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
</style>