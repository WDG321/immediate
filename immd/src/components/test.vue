<template >
  <div class="box" ref="box">
    <div class="text">test</div>
    <div class="search"></div>
    <el-popover trigger="click" :hide-after="0">
      <div class="box2">
        <img src="/zhucetianjiahaoyou.png" class="addImg" />
        <box class="box3">
          <div class="addText">添加朋友</div>
        </box>
      </div>
      <template #reference>
        <div class="addTo" @click="addTo"></div>
      </template>
    </el-popover>
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
  width: 100%;
  height: 2.3rem;
  background-color: rgb(228, 226, 226);
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.search {
  width: 1.3rem;
  height: 1.3rem;
  background-image: url(/sousuo.png);
  background-size: 100%, 100%;
  margin-right: 0.8rem;
}
.addTo {
  width: 1.5rem;
  height: 1.5rem;
  background-image: url(/jiahao.png);
  background-size: 100%, 100%;
  margin-right: 0.8rem;
}
.text {
  position: absolute;
  transform: translate(-50%);
  left: 50%;
  font-size: 1.2rem;
}
.box2 {
  display: flex;
  align-items: center;
  height: 2rem;
  margin-top: 0.5rem;
}
.addImg {
  width: 1.5rem;
  height: 1.5rem;
  margin-right: 0.5rem;
}
.addText {
  color: rgb(255, 255, 255);
  font-size: 0.9rem;
}
.box3 {
  width: 100%;
  height: 2rem;
  display: flex;
  align-items: center;
  border-bottom: 0.4px solid rgba(205, 204, 204, 0.5);
}
</style>