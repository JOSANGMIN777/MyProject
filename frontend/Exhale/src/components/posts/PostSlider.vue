<template>
<v-sheet
    class="mx-auto"
    width="100%"
  >
    <v-slide-group
      v-model="model"
      class="v-slide-group"
      show-arrows
    >
      <!-- <v-slide-group-item
      
      v-for="slot in 10"
      :key = slot
        v-slot="{ isSelected, toggle, selectedClass }"
      >
     -->
     <v-slide-group-item v-for="(post, index) in crud.posts" :key="index">
        <v-card
        :style="{ backgroundImage: 'url(' + getThumbnail(post.thumbnail) + ')', backgroundSize: 'cover', backgroundPosition: 'center' }"
          color="#E0E0E0"
          class="v-card"
          :class="[selectedClass]"
          height="230"
          width="230"
          
          @click.prevent="go_detail(post.id)">
        
          <div class="d-flex fill-height align-center justify-center">    
            <div>
              <p class="card-text"></p>
            </div>  
            <v-scale-transition>
              <v-icon
                v-if="isSelected"
                color="black"
                size="48"
                icon="mdi-close-circle-outline"
              ></v-icon>
            </v-scale-transition>
          </div>
        </v-card>
      </v-slide-group-item>
    </v-slide-group>
  </v-sheet>


</template>

<script setup>
import { ref, onMounted, defineProps } from 'vue'
import {storeToRefs} from 'pinia'
import { boardList } from '@/api/boards';
import PostDetailView from '@/views/community/PostDetailView.vue';
import { useCrudStore } from '@/stores/crud';
import { boardDetail } from '@/api/boards';
import router from '@/router';
const model = ref(null)
const file = ref(null); // 선택된 파일을 저장할 ref 생성
const imageUrl = ref(''); // 이미지 URL을 저장할 ref 생성
const crud = useCrudStore()

onMounted( async () => {
    const response = await boardList(1, 0);

})

const props = defineProps({
  thumbnail: String,
})


const go_detail = (id) => {
  console.log('고 투 디테일',id)
  router.push(`/posts/${id}`)

}


function getThumbnail(thumbnail) {
  return thumbnail || '@/assets/dog3.jpg'; // thumbnail이 없는 경우 기본 이미지 경로 반환
}
</script>

<style scoped>
  /* @import "@/styles/main.scss"; */
  .v-card {
    border-radius: 0px;
    position: relative;
  }
  .v-slide-group {
    background-color: #ffffff;
    width: 100vw;
  }
  .card-text {
    position: absolute;
    z-index: 5;
    top: 50%;
    left: 0;
    width: 100%;
    text-align: center;
  }

  .v-slide-group__next i {
    display: none;
  }
</style>