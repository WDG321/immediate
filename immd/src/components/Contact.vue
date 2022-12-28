<template>
  <el-scrollbar class="elScrollbar" ref="elScrollbar" @scroll="scroll">
    <div v-for="i in 50">我是联系人列表{{ i }}</div>
  </el-scrollbar>
</template>

<script>
import { ref, onMounted, inject, onActivated } from "vue";
export default {
  name: "Contact",
  setup() {
    let elScrollbar = ref(null);
    let contactScrollDistance = inject("contactScrollDistance");
    const scroll = ({ scrollTop }) => {
      //记录滚动的距离
      contactScrollDistance.value = scrollTop;
    };
    //路由激活组件时调用
    onActivated(() => {
      //更改滚动条位置
      elScrollbar.value.setScrollTop(contactScrollDistance.value);
    });
    onMounted(() => {
      //document.documentElement.clientHeight为网页可见区域高
      //92.8的来源是顶部导航与底部导航的高度加起来任何乘以16，16为html的字体大小(px),因为使用了rem来设置高度
      elScrollbar.value.wrapRef.style.height =
        document.documentElement.clientHeight - 92.8 + "px";
    });
    return { elScrollbar, scroll };
  },
};
</script>

<style scoped>
</style>