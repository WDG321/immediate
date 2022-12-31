<template>
  <el-scrollbar class="elScrollbar" ref="elScrollbar" @scroll="scroll">
    <div class="box">
      <img :src="user.profilePhoto" class="profilePhoto" />
      <div class="box02">
        <div class="username">{{ user.username }}</div>
        <div class="id">id：{{ user.id }}</div>
      </div>
    </div>
  </el-scrollbar>
</template>

<script>
import { onMounted, ref, reactive, inject } from "vue";

export default {
  name: "Me",
  setup() {
    //后代组件有一个 `inject` 选项来开始使用这些数据
    let user = inject("user"); //使用inject函数接收祖先组件使用provide函数传递的数据
    const elScrollbar = ref(null);
    //后代组件有一个 `inject` 选项来开始使用这些数据
    let meScrollDistance = inject("meScrollDistance"); //使用inject函数接收祖先组件使用provide函数传递的数据
    const scroll = ({ scrollTop }) => {
      //记录滚动的距离
      meScrollDistance.value = scrollTop;
    };
    onMounted(() => {
      //document.documentElement.clientHeight为网页可见区域高
      //92.8的来源是顶部导航与底部导航的高度加起来任何乘以16，16为html的字体大小(px),因为使用了rem来设置高度
      elScrollbar.value.wrapRef.style.height =
        document.documentElement.clientHeight - 92.8 + "px";
      //更改滚动条位置
      elScrollbar.value.setScrollTop(meScrollDistance.value);
    });
    return { user, elScrollbar, scroll };
  },
};
</script>

<style scoped>
.box {
  display: flex;
  /* background-color: aqua; */
  align-items: center;
}
.profilePhoto {
  width: 4rem;
  height: 4rem;
  /* 设置圆角 */
  border-radius: 0.5rem;
  margin: 1rem;
  margin-left: 1.5rem;
  /* 隐藏元素，但保持占用空间 */
  /* visibility: hidden; */
}
.box02 {
  height: 3.5rem;
  display: flex;
  /*   background-color: rgb(255, 255, 255); */
  flex-direction: column;
  justify-content: space-between;
}
.username {
  font-size: 1.2rem;
  /* 加粗字体 */
  font-weight: 900;
}
.id {
  color: rgb(148, 148, 148);
}
</style>