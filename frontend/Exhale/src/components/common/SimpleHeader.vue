<template>
  <div class="main-nav">
    <div class="navbar-logo-link">
      <RouterLink :to="{ name: 'MainPage' }">
        <img src="@/assets/logo_green.png" alt="logo" class="navbar-logo" />
      </RouterLink>
    </div>
    <div class="menu" @click="toggleMenu">
      <PostMenu v-if="!show" />
      <CommunityMenu v-else />
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

import CommunityMenu from "@/components/modals/CommunityMenu.vue";
import PostMenu from "@/components/posts/PostMenu.vue";

const show = ref(false);

function toggleMenu() {
  show.value = !show.value;
}
</script>

<style scoped></style>
