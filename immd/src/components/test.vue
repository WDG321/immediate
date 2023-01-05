<template >
  <div class="a">
    <div class="box" v-for="i in 50">
      <img
        src="http://192.168.1.134:80/images/2022/12/30/9684a3c45ff44a5c8f4192d278ce695d.png"
        class="img"
      />
      <div class="triangle"></div>
      <div class="text">
        我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message我是message
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "test",
  setup() {
    return {};
  },
};
</script>

<style scoped>
.box {
  display: flex;
  align-items: center;
  margin-top: 1rem;
  margin-bottom: 1rem;
}
.img {
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 0.5rem;
  margin: 0.5rem;
}
.triangle {
  width: 0px;
  height: 0px;
  border-top: 0.4rem solid transparent;
  border-bottom: 0.4rem solid transparent;
  border-left: 0.4rem solid transparent;
  border-right: 0.4rem solid rgb(255, 255, 255);
}
.text {
  background-color: rgb(255, 255, 255);
  border-radius: 0.2rem;
  max-width: 60vw;
  padding: 0.75rem;
}
.a {
  width: 100vw;
  height: 100vh;
  background-color: rgba(205, 204, 204, 0.5);
  padding: 1px;
}
</style>