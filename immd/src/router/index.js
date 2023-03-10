import { createWebHistory, createWebHashHistory, createRouter } from "vue-router";
import login from "../components/login.vue";
import index from "../components/index.vue";
//import webSocketTest from "../components/webSocketTest.vue";
import Message from "../components/Message.vue"
import Contact from "../components/Contact.vue"
import Me from "../components/Me.vue"
import contactDetails from "../components/contactDetails.vue"
import conversation from "../components/conversation.vue"
import test from "../components/test.vue"
import addFriends from "../components/addFriends.vue"
import axios from "axios"
// 全局设置 axios 发送请求带上cookie
axios.defaults.withCredentials = true
const routes = [
  {
    path: "/",
    component: index,
  },
  /* {
    name: "webSocketTest",
    path: "/webSocketTest",
    component: webSocketTest
  }, */
  {
    name: "test",
    path: "/test",
    component: test
  },
  {
    name: "contactDetails",
    path: "/contactDetails",
    component: contactDetails,
    //路由独享的前置守卫
    beforeEnter: (to, from) => {
      //to: 即将要进入的目标
      //from: 当前导航正要离开的路由 
      //返回 false 以取消导航
      //返回一个路由地址: 通过一个路由地址跳转到一个不同的地址
      //阻止用户通过url直接进入此页面
      if (from.name != "Contact") {
        return "/index/Message"
      }
    },
  },
  {
    name: "conversation",
    path: "/conversation",
    component: conversation,
    //路由独享的前置守卫
    beforeEnter: (to, from) => {
      //to: 即将要进入的目标
      //from: 当前导航正要离开的路由 
      //返回 false 以取消导航
      //返回一个路由地址: 通过一个路由地址跳转到一个不同的地址
      //阻止用户通过url直接进入此页面
      if (from.name != "contactDetails" && from.name != "Message") {
        return "/index/Message"
      }
    },
  },
  {
    name: "addFriends",
    path: "/addFriends",
    component: addFriends,
    beforeEnter: (to, from) => {
      //to: 即将要进入的目标
      //from: 当前导航正要离开的路由 
      //返回 false 以取消导航
      //返回一个路由地址: 通过一个路由地址跳转到一个不同的地址
      //阻止用户通过url直接进入此页面
      //因为直接刷新页面无法获取from.matched[0].name属性，所以使用trycatch进行操作
      try {
        if (from.matched[0].name != "index") {
          return "/index/Message"
        }
      } catch {
        return "/index/Message"
      }
    }
  },
  {
    name: "index",
    path: "/index",
    component: index,
    //配置子路由
    children: [{
      name: "Message",
      path: "Message",
      component: Message,
    }, {
      name: "Contact",
      path: "Contact",
      component: Contact,
    }, {
      name: "Me",
      path: "Me",
      component: Me,
    }]
  },
  {
    path: "/login",
    name: "login",
    component: login
  }
];

const router = createRouter({
  history: createWebHistory(),
  //哈希模式
  /* history: createWebHashHistory(), */
  routes,
});
//全局全局前置路由守卫
router.beforeEach(async (to, from) => {
  //to: 即将要进入的目标
  //from: 当前导航正要离开的路由 
  //返回 false 以取消导航
  //返回一个路由地址: 通过一个路由地址跳转到一个不同的地址
  //进入非登录页面时需要验证权限
  if (to.name != "login") {
    //创建请求配置对象
    let config = {
      method: 'post',
      /* url: 'http://192.168.1.134/permissionsApi', */
      url: "http://192.168.215.42/permissionsApi"
    };
    //发送请求
    let response = await axios(config)
    //判断是否有权限
    if (response.data) {
      //从登录页面离开后延迟刷新下页面，防止出bug
      if (from.name == "login") {
        setTimeout(() => {
          location.reload()
        }, 10);
      }
      //放行
      return true
    } else {
      //无权限就跳到登录页面
      const elMessageError = ElMessage.error("未登录，请登录");
      //ElMessage一旦有鼠标或者手指放上去，就不会自动消失了
      //设置定时器让它消失
      setTimeout(function () {
        elMessageError.close();
      }, 3000);
      return "/login"
    }
  }

})
export default router;