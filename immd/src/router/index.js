import { createWebHistory, createRouter } from "vue-router";
import login from "../components/login.vue";
import index from "../components/index.vue";
import test from "../components/test.vue";
const routes = [
  {
    path: "/",
    component: index,
  },
  {
    path: "/index",
    component: index,
    //配置子路由
    children: [{
      path: "test",
      component: test
    }]
  }, {
    path: "/login",
    component: login
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;