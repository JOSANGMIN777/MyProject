<template>
  <html>
    <head>
      <title>날;숨</title>
    </head>
  </html>
  <div class="head">
    <main>
      <RouterView/>
    </main>
  </div>

</template>


<script>
import { RouterLink, RouterView } from 'vue-router';
import {createApp, onMounted, ref} from 'vue';
import {reactive} from 'vue';
import axios from 'axios';

</script>

<style>

.head {
  /* background-image: url("@/assets/background_origin1.png");
  background-size: cover; */
  height: auto;
  min-height: 100%;
}
</style>
