<template>
  <el-scrollbar class="elScrollbar" ref="elScrollbar" @scroll="scroll">
    <div v-for="contact in user.contact" class="box" @click="click(contact)">
      <img :src="contact.profilePhoto" class="profilePhoto" />
      <div class="username">{{ contact.username }}</div>
    </div>
  </el-scrollbar>
</template>

<script>
import { useRouter } from "vue-router";
import { ref, onMounted, inject } from "vue";
export default {
  name: "Contact",
  setup() {
    const router = useRouter();
    const user = inject("user");
    let elScrollbar = ref(null);
    let contactScrollDistance = inject("contactScrollDistance");
    const scroll = ({ scrollTop }) => {
      //记录滚动的距离
      contactScrollDistance.value = scrollTop;
    };
    onMounted(() => {
      //document.documentElement.clientHeight为网页可见区域高
      //92.8的来源是顶部导航与底部导航的高度加起来任何乘以16，16为html的字体大小(px),因为使用了rem来设置高度
      elScrollbar.value.wrapRef.style.height =
        document.documentElement.clientHeight - 92.8 + "px";
      //更改滚动条位置
      elScrollbar.value.setScrollTop(contactScrollDistance.value);
    });
    const click = (contact) => {
      console.log("点击了联系人" + contact.username);
      //使用replace将不会留下历史记录
      router.replace({
        path: "/contactDetails",
        //路由传参
        query: { id: `${contact.id}` },
      });
    };
    return { elScrollbar, scroll, user, click };
  },
};
</script>

<style scoped>
.box {
  display: flex;
  align-items: center;
  /* background-color: aqua; */
}
.profilePhoto {
  margin: 0.5rem;
  margin-left: 1rem;
  margin-right: 1rem;
  /* 设置圆角 */
  border-radius: 0.5rem;
  width: 2.5rem;
  height: 2.5rem;
}
.username {
  display: flex;
  align-items: center;
  height: 3rem;
  /* 设置下边框 */
  border-bottom: 0.4px solid rgba(205, 204, 204, 0.5);
  width: calc(100vw - 4.5rem);
}
</style>