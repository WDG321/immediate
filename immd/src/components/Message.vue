<template>
  <el-scrollbar class="elScrollbar" ref="elScrollbar" @scroll="scroll">
    <div v-for="item in 50" :key="item" class="messageList">
      <div class="headshot"></div>
      <div>
        <div class="username">我是昵称</div>
        <div class="messagePreview">我是最新消息的预览</div>
      </div>
    </div>
  </el-scrollbar>
</template>

<script>
import { ref, onMounted, inject, onActivated } from "vue";
export default {
  name: "Message",
  setup() {
    const elScrollbar = ref(null);
    //后代组件有一个 `inject` 选项来开始使用这些数据
    let scrollDistance = inject("scrollDistance"); //使用inject函数接收祖先组件使用provide函数传递的数据
    const scroll = ({ scrollTop }) => {
      //记录滚动的距离
      scrollDistance.value = scrollTop;
    };
    //路由激活组件时调用
    onActivated(() => {
      //更改滚动条位置
      elScrollbar.value.setScrollTop(scrollDistance.value);
    });
    return { elScrollbar, scroll };
  },
};
</script>

<style scoped>
.elScrollbar {
  height: calc(100vh - 5.8rem);
}
.messageList {
  display: flex;
  align-items: center;
}
.headshot {
  width: 3rem;
  height: 3rem;
  background-color: rgb(228, 226, 226);
  margin: 0.5rem;
  border-radius: 0.3rem;
}
.username {
  font-size: 1.1rem;
}
.messagePreview {
  font-size: 0.8rem;
  color: rgb(183, 183, 183);
}
</style>