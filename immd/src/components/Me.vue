<template>
  <div>
    <img :src="profilePhoto" />
    <div>
      <div>{{ username }}</div>
      <div>id:{{ id }}</div>
    </div>
  </div>
</template>

<script>
import { onMounted, ref } from "vue";
import axios from "axios";
export default {
  name: "Me",
  setup() {
    //昵称
    let username = ref(null);
    //id
    let id = ref(null);
    //头像地址
    let profilePhoto = ref(null);
    onMounted(async () => {
      //创建请求配置对象
      let config = {
        method: "post",
        url: "http://192.168.1.134/meApi",
      };
      //发送请求
      let response = await axios(config);
      console.log(response.data);
      //获取数据
      username.value = response.data.username;
      id.value = response.data.id;
      profilePhoto.value = response.data.profilePhoto;
    });
    return { username, id, profilePhoto };
  },
};
</script>

<style scoped>
</style>