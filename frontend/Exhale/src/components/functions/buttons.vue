<template>
        <button class="enlarge" @click="enlarge" style="position: fixed; right: 0px; z-index: 10;">
        <img src="@/assets/plus.svg" class="plus">
        {{ msg }}
        </button>     
      <div class="p" :style="{ fontSize: fontSize + 'px' }">
    </div>
</template>

<script setup>
import { computed, ref } from 'vue';
const fontSize = ref(16)
const msg = computed(() => fontSize.value > 21 ? '원래대로' : '글자확대');
const enlarge = () => {
  fontSize.value ++;
  if (fontSize.value > 22) {
    fontSize.value = 16
  }};
</script>

<style lang="scss" scoped>

</style>