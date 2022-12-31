<template>
  <el-scrollbar class="elScrollbar" ref="elScrollbar" @scroll="scroll">
    <div v-for="item in 20" :key="item" class="messageList">
      <div class="headshot"></div>
      <div class="box">
        <div>
          <div class="username">我是昵称</div>
          <div class="messagePreview">
            我是最新消息的预览,我是最新消息的预览,我是最新消息的预览
          </div>
        </div>
        <div class="date">12月32日</div>
      </div>
    </div>
  </el-scrollbar>
</template>

<script>
import { ref, onMounted, inject } from "vue";
import axios from "axios";
export default {
  name: "Message",
  setup() {
    const elScrollbar = ref(null);
    //后代组件有一个 `inject` 选项来开始使用这些数据
    let messageScrollDistance = inject("messageScrollDistance"); //使用inject函数接收祖先组件使用provide函数传递的数据
    let user = inject("user");
    const scroll = ({ scrollTop }) => {
      //记录滚动的距离
      messageScrollDistance.value = scrollTop;
    };
    onMounted(() => {
      //document.documentElement.clientHeight为网页可见区域高
      //92.8的来源是顶部导航与底部导航的高度加起来任何乘以16，16为html的字体大小(px),因为使用了rem来设置高度
      elScrollbar.value.wrapRef.style.height =
        document.documentElement.clientHeight - 92.8 + "px";
      //更改滚动条位置
      elScrollbar.value.setScrollTop(messageScrollDistance.value);
    });
    return { elScrollbar, scroll };
  },
};
</script>

<style scoped>
.elScrollbar {
}
.messageList {
  width: 100%;
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
  margin-top: 0.5rem;
}
.messagePreview {
  font-size: 0.8rem;
  color: rgb(183, 183, 183);
  width: 12rem;
  /* 超出部分切除 */
  overflow: hidden;
  /* 强制文本不换行 */
  white-space: nowrap;
  /* 文本超出部分用省略号替代 */
  text-overflow: ellipsis;
}
.date {
  font-size: 0.8rem;
  color: rgb(183, 183, 183);
  margin-right: 0.5rem;
  margin-top: 0.5rem;
}
.box {
  display: flex;
  justify-content: space-between;
  width: calc(100vw - 3.5rem);
  border-bottom: 0.4px solid rgba(205, 204, 204, 0.5);
  height: 4rem;
}
</style>