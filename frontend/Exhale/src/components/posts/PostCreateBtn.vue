<template>
    <p @click="goPage">
      글쓰기
    </p>
</template>

<script setup>
  import { useRouter } from 'vue-router';

  const router = useRouter();
  const goPage = () => {
   router.push('/posts/create')
  };

</script>

<style scoped>
  p {
    color: gray;
    cursor: pointer;
    border: 2px solid gray;
    border-radius: 20px;
    padding: 5px 20px;
    margin-top: 2vh;
    margin-right: -1vw;
    font-family: 'NotoSansKR';
  }

</style>