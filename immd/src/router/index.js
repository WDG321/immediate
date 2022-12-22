import { createWebHistory, createRouter } from "vue-router";
import login from "../components/login.vue";
import index from "../components/index.vue";
const routes = [
  {
    path: "/",
    component: index,
  }, {
    path: "/login",
    name: "login",
    component: login
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;