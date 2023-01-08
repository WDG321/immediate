<template>
  <div class="boxmax">
    <div class="box">
      <div @click="returnMessage" class="returnMessage"></div>
      <div>添加朋友</div>
      <!-- 空div用于占位 -->
      <div style="width: 1.7rem"></div>
    </div>
    <el-input
      v-model="input"
      placeholder="请输入id"
      class="input"
      oninput="value=value.replace(/[^\d]/g,'')"
    />
    <el-button
      type="primary"
      class="button"
      size="large"
      @click="search"
      :loading="loading"
      >{{ buttonText }}</el-button
    >
  </div>
</template>

<script>
import { inject, ref } from "vue";
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
    //输入框内容
    let input = ref("");
    //决定按钮是否为加载状态
    let loading = ref(false);
    let buttonText = ref("搜索");
    //点击搜索触发的函数
    const search = () => {
      console.log(input.value);
      buttonText.value = "";
      loading.value = true;
      //模拟请求
      setTimeout(() => {
        loading.value = false;
        buttonText.value = "搜索";
      }, 2000);
    };
    return { returnMessage, input, search, loading, buttonText };
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
:deep(.el-input__inner) {
  /* 输入文本居中 */
  text-align: center;
}
:deep(.el-input__wrapper) {
  /* 设置圆角 */
  border-radius: 0.7rem;
  font-size: 1rem;
}
.input {
  width: 90vw;
  height: 2rem;
  margin-top: 1rem;
  position: absolute;
  transform: translate(-50%);
  left: 50%;
}
.button {
  position: absolute;
  top: 6.3rem;
  transform: translate(-50%);
  left: 50%;
  font-size: 1rem;
  width: 3rem;
  height: 3rem;
  border-radius: 5rem;
}
</style>