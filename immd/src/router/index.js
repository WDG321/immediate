import { createWebHistory, createWebHashHistory, createRouter } from "vue-router";
import login from "../components/login.vue";
import index from "../components/index.vue";
import test from "../components/test.vue";
import Message from "../components/Message.vue"
import Contact from "../components/Contact.vue"
import Me from "../components/Me.vue"
const routes = [
  {
    path: "/",
    component: index,
  },
  {
    name: "index",
    path: "/index",
    component: index,
    //配置子路由
    children: [{
      name: "test",
      path: "test",
      component: test
    }, {
      name: "Message",
      path: "Message",
      component: Message,
      //配置是否缓存
      meta: { keepAlive: true }
    }, {
      name: "Contact",
      path: "Contact",
      component: Contact,
      meta: { keepAlive: true }
    }, {
      name: "Me",
      path: "Me",
      component: Me,
      meta: { keepAlive: true }
    }]
  },
  {
    path: "/login",
    name: "login",
    component: login
  }
];

const router = createRouter({
  /* history: createWebHistory(), */
  //哈希模式
  history: createWebHashHistory(),
  routes,
});
//全局路由守卫
router.beforeEach((to, from) => {
  //to: 即将要进入的目标
  //from: 当前导航正要离开的路由 
  //返回 false 以取消导航
  //返回一个路由地址: 通过一个路由地址跳转到一个不同的地址
  if (to.name != "login") {
    //判断是否有权限
    if (true) {
      //放行
      return true
    } else {
      //无权限就跳到登录页面
      ElMessage.error("未登录，请登录");
      return "/login"
    }
  }

})
export default router;