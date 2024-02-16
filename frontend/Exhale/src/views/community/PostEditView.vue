<template>
  <form @submit.prevent="edit">
    <div class="imageBackground">
        <section class="sub-nav1">
            <div id="breadcrum">
                <RouterLink class="breadlink" :to="{name: 'MainPage'}">메인 홈</RouterLink>
                >
                <RouterLink class="breadlink" :to="{name: 'PostWholeListView'}">커뮤니티</RouterLink>
                >
                <RouterLink class="breadlink" :to="{name: 'PostEditView'}">게시글 수정</RouterLink>
            </div>
        </section>

            <div>
                <input v-model="title" class="titleInput" id="title" placeholder="제목을 입력하세요">
            </div>
            <div class="author">
                (작성자명)
            </div>
    </div>

<!-- ----------------------------------------------------------------------------------------------------- -->

    <div class="category">
        <div class="dropdown">
        <a class="btn dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            카테고리 선택
        </a>

        <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="#">정보 글</a></li>
            <li><a class="dropdown-item" href="#">치료 후기</a></li>
            <li><a class="dropdown-item" href="#">환자 이야기</a></li>
        </ul>
        </div>
    </div>

    <div class="contentBackground">
    <div>
        <div>
        <v-container class='toggles'>
        <v-row>

        <v-col
            cols="12"
            sm="6"
            class="py-2 px-10"
        >
            <v-btn-toggle
            v-model="toggle_multiple"
            background-color="primary"
            dark
            multiple
            >
            <v-btn>
                <v-icon>mdi-format-bold</v-icon>
            </v-btn>

            <v-btn>
                <v-icon>mdi-format-italic</v-icon>
            </v-btn>

            <v-btn>
                <v-icon>mdi-format-underline</v-icon>
            </v-btn>

            <v-btn>
                <v-icon>mdi-format-color-fill</v-icon>
            </v-btn>
            </v-btn-toggle>
        </v-col>

        <v-col
            cols="12"
            sm="6"
            class="py-2 px-10"
        >
            <v-btn-toggle
            v-model="toggle_one"
            shaped
            mandatory
            >
            <v-btn>
                <v-icon>mdi-format-align-left</v-icon>
            </v-btn>

            <v-btn>
                <v-icon>mdi-format-align-center</v-icon>
            </v-btn>

            <v-btn>
                <v-icon>mdi-format-align-right</v-icon>
            </v-btn>

            <v-btn>
                <v-icon>mdi-format-align-justify</v-icon>
            </v-btn>
            </v-btn-toggle>
        </v-col>
        </v-row>
    </v-container>

      <div class="mb-3">
      <label for="content" class="form-label">어떻게 쓰나요?</label>
      <textarea v-model="content" class="form-control" id="content" rows="3" placeholder="여기를 클릭해서 글을 작성하거나, 오른쪽 이미지나 이모티콘 아이콘을 클릭해서 첨부한 후 작성하면 됩니다."></textarea>
      </div>
      
      <div class="twoButton">
        <button type="button" class="buttons" @click="goDetailPage">취소</button>
        <button type="button" class="buttons" @click="edit">수정</button>
      </div>
    </div>
  </div>
</div>

      <div class="add-icons">
        <label @click.prevent="imoji_pop" class="emoji">
        <font-awesome-icon icon="face-smile" />
        <div class="emoji-picker" v-show="imoji">
                <EmojiPicker :native="true" @select="onSelectEmoji"/>
        </div>
        </label>

        <v-file-input
                    class="custom-file-input-icon"
                    :rules="rules"
                    accept="image/png, image/jpeg, image/bmp"
                    placeholder=""
                    prepend-icon="mdi-folder-image"
                    label="이미지 선택"
                    show-input="false"
                    >
        </v-file-input>
    </div>
      
    </form>
</template>

<script setup>
import { ref, onMounted, defineProps } from 'vue'
import { boardDetail } from '@/api/boards';
import { useRoute, useRouter } from 'vue-router'
import {updatePost} from '@/api/boards'
import { useAuthStore } from '@/stores/auth';
import { useCrudStore } from '@/stores/crud';
const store = useAuthStore()
const crud = useCrudStore()
const accessToken = store.jwtToken

const route = useRoute()
const router = useRouter()
const id = route.params.id

const title = ref('')
const content = ref('')
const thumbnail = ref('')

const edit = async () => {
  try {
    await updatePost(title.value, content.value, thumbnail.value,accessToken, id)
    router.push({ name: 'PostDetailView', params: { id} })
  } catch (error) {
    console.error(error)
  }
}



const goDetailPage = () => router.push({ name: 'PostDetailView', params: { id } })
onMounted(async () => {
  const response = await boardDetail(id);
  title.value = response.data.response.title; // API 응답 구조에 맞게 조정 필요
  content.value = response.data.response.content; // API 응답 구조에 맞게 조정 필요
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/pages/_postcreate.scss"
</style>