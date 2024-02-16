<template>
  <div class="postcard" @click="handleClick">
    <div class="card-number">
    </div>
    <div class="card-body">
        <p class="card-title">{{ title }}</p>
        <p class="card-content">{{ content }}</p>
        <p class="card-author">{{ author }}</p>
        <p class="text-muted"><small class="text-body-secondary">{{ create_date.substring(0, 10)}}</small></p>
    </div>
    <div class="card-image-container">
    <div class="card-image">
      <img v-if="props.thumbnail" :src="props.thumbnail" :alt="title" style="width: 200px; height: 190px; border-radius: 10%; border: 2px solid rgb(108, 159, 156);" >
     <!-- :style="{ backgroundImage: 'url(' + imageUrl + ')' , backgroundSize: 'cover', backgroundPosition: 'center' }" > -->
      <img v-if="props.thumbnail === null" src="@/assets/dog3.jpg">
    </div>
  </div>
</div>
</template>

<script setup>
import { defineProps, defineEmits, ref , watchEffect } from 'vue'
const props = defineProps({
  thumbnail: String,
  title: String,
  content: String,
  author: String,
  create_date: String,
  id: Number,
})
const article_id = ref([props.id])
const imageUrl = ref('')

const emit = defineEmits(['board_detail'])
const handleClick = () => {
  emit('board_detail', article_id)
}

watchEffect(() => {
  imageUrl.value = props.thumbnail
  
  
})
</script>

<style lang="scss" scoped>
  @import "@/assets/scss/layout/_forms.scss";
  .postcard {
  display: flex;
  // flex-direction: column;
  align-items: center;
  padding: 20px;
  margin-top: -4vh;
  margin-bottom: 5vh;
}

.card-body {
  width: 120vw;
  margin-top: 16vh;
}

.card-image-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  margin: 0px 10px;
}

.card-image {
  flex: 0 1 auto; 
  padding-top: 7vw;
  padding-left: 3vw;
  padding-right: 15vw;
}
</style>