<template>
<form @submit.prevent="save">
<div class="imageBackground "  :style="{ backgroundImage: 'url(' + imageUrl + ')' , backgroundSize: 'cover', backgroundPosition: 'center' }">

        <section class="sub-nav1">
            <div id="breadcrum">
                <RouterLink class="breadlink" :to="{name: 'MainPage'}">메인 홈</RouterLink>
                >
                <RouterLink class="breadlink" :to="{name: 'PostWholeListView'}">커뮤니티</RouterLink>
                >
                <RouterLink class="breadlink" :to="{name: 'PostCreateView'}">글쓰기</RouterLink>
            </div>
            
        </section>
        
        <div>
            <input v-model="title" class="titleInput" id="title" placeholder="제목을 입력하세요">
        </div>
        <div class="author">
            {{ nickname }} 
        </div>
    
</div>
<!-- <div class="dropdown">
    <button class=" dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false"  @change="onCategoryChange">
      {{ category_name }}
    </button>
    <ul class="dropdown-menu" >
      <li><a class="dropdown-item"  @click.prevent="selectCategory('정보 글', 1)">정보 글</a></li>
      <li><a class="dropdown-item"  @click.prevent="selectCategory('치료 후기', 2)">치료 후기</a></li>
      <li><a class="dropdown-item"  @click.prevent="selectCategory('환자 이야기', 3)">환자 이야기</a></li>
    </ul>
  </div> -->


<div class="category">
    <div role="button" class="dropdown-toggle" aria-expanded="false" aria-placeholder="categoty_name">
        <select v-model="selectedCategory" @change="onCategoryChange">
            <option disabled value="">카테고리를 선택해주세요</option>
            <option value= 1 class="dropdown-item" >정보 글</option>
            <option value= 2 class="dropdown-item">치료 후기</option>
            <option value= 3 class="dropdown-item">환자 이야기</option>
        </select>
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
                <p class="howtowrite">어떻게 쓰나요?</p>
                <textarea v-model="content" class="textcontent" placeholder="여기를 클릭해서 글을 작성하거나, 오른쪽 이미지나 이모티콘 아이콘을 클릭해서 첨부한 후 작성하면 됩니다."></textarea>
            </div>
            
            <div class="twoButton">
                <button class="buttons" type="button" @click="goListPage">목록</button>
                <button class="buttons" type="button" @click="article_create">저장</button>
            </div>
        </div>

        </div>
    </div>
        
    <div class="add-icons">
        <div @click.prevent="imoji_pop" class="emoji">
        <font-awesome-icon icon="face-smile" />
        <div class="emoji-picker" v-if="imoji">
                <EmojiPicker :native="true" @select="onSelectEmoji"/>
        </div>
        </div>

        <!-- <v-file-input
                    class="custom-file-input-icon"
                    :rules="rules"
                    accept="image/png, image/jpeg, image/bmp"
                    placeholder=""
                    prepend-icon="mdi-folder-image"
                    label="이미지 선택"
                    show-input="true"
                    >
        </v-file-input> -->
<div class="file-upload">
  <input type="file" @change="onFileChange" accept="image/*" />
</div>

    </div>
</form>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';
import EmojiPicker from 'vue3-emoji-picker';
import 'vue3-emoji-picker/css';
import { watch } from 'vue';
import { mdiConsolidate, mdiGateArrowRight } from '@mdi/js';
import { articleCreate, saveImg } from '@/api/boards';
import { useAuthStore } from "@/stores/auth";
import { storeToRefs } from "pinia";
import axios from "axios";
import { render } from 'vue';

const category_name = ref("카테고리를 선택해주세요");
const isOpen = ref(false);

function toggleDropdown() {
  isOpen.value = !isOpen.value;
}

function setCategory(name) {
  category_name.value = name;
  isOpen.value = false; // 선택 후 드롭다운 닫기
}

const baseURL = import.meta.env.VITE_APP_BASE_URL;
const store = useAuthStore()
const accessToken = store.jwtToken;
const { nickname } = storeToRefs(store);

const selectedCategory = ref('');
const router = useRouter()
const title = ref('');
const content = ref('')
const thumbnail = ref(null);
const board_id = ref('')

let pic = null
const file = ref(null); // 선택된 파일을 저장할 ref 생성
const imageUrl = ref(''); // 이미지 URL을 저장할 ref 생성

// 파일이 선택되었을 때 호출될 메서드
const onFileChange = async (event) => {
    
  const files = event.target.files;

    if (files.length > 0) {
        const file = files[0];
        const formData = new FormData();
        formData.append("file", file);
        
        const response = await axios.post(baseURL + `/api/articles/image`,
        formData
        );
        imageUrl.value = response.data.response
        thumbnail.value = response.data.response
    }



//   if (files.length > 0) {
//     // 첫 번째 선택된 파일을 저장
//     file.value = files[0];
    
//     // 이미지 미리보기를 위한 URL 생성
//     imageUrl.value = URL.createObjectURL(file.value);
//   }
};
const props = defineProps({
    tab: Number
})
const emit = defineEmits(['update:tab'])


// const inConing = (event) => {
//     const files = event.target?.files;
//     if (files.length > 0) {
//         file = files[0];

//         const reader = new FileReader();

//         render.onload = (e) => {
//             images.value = e.target.result
//         };
//         reader.readAsDataURL(file)
//     }
// };

// const pictures = async() => {
//     try {
//         const pic = new FormData();
//         pic.append("pic", file)
//         console.log(pic)
//     } catch (error) {
//         console.log(error)
//     }
// }

const save_img = async() => {
    const formData = new FormData();
    formData.append('image',  imageUrl.value)
    await saveImg(
        formData
    )}

watch(() => props.tab, (newVal) => {
  localTab.value = newVal
})

const rules = ref([
  value => {
    return !value || !value.length || value[0].size < 2000000 || '2MB 이하의 사진파일만 첨부 가능해요'
  },
])

const text = ref('center')
const icon = ref('justify')
const toggle_none = ref(null)
const toggle_one = ref(0)
const toggle_exclusive = ref(2)
const toggle_multiple = ref([0, 1, 2])




// 목록
const goListPage = () => router.push({ name:'PostWholeListView' })


// 이모티콘 로직 모음 --------------------------------------------
const imoji = ref(false)

// 이모티콘 버튼 누를 시 이모지창 팝업
const imoji_pop = () => {
  imoji.value = true
}

// 이모티콘을 입력하거나 이모지창 내부를 클릭하면 반응안하고 창 외부 클릭시 창 사라짐 
const onDocumentClick = (event) => {
    const emojiPickerEl = event.target.closest('.emoji-picker');
    const emojiIconEl = event.target.closest('.emoji');
  if (!emojiPickerEl && !emojiIconEl && imoji.value) {
        imoji.value = false
  }};

// 클릭 이벤트 생성
onMounted(() => {
  document.addEventListener('click', onDocumentClick);
});

// 클릭 이벤트 해제
onUnmounted(() => {
  document.removeEventListener('click', onDocumentClick);
});

// 이모티콘 인풋창에 입력
const onSelectEmoji = (emoji) => {
    content.value += emoji.i
} 
// --------------------------------------------------------------    


// 게시글 생성
const article_create = async () => {
    try {
       const response = articleCreate(
            title.value,
            content.value,
            thumbnail.value,
            board_id.value,
            accessToken
       ) 
    } catch (error) {
        console.error('게시글 등록에 실패하였습니다.', error)
        
    } finally { router.push('/post-whole-list') } 
}

// 게시물 종류 선택시 게시물 아이디 정해짐
const onCategoryChange = () => {
    board_id.value = parseInt(selectedCategory.value, 10);
    if(selectedCategory.value === '1'){
        category_name.value = "정보 글"
    }
    if(selectedCategory.value === '2'){
        category_name.value = "치료 후기"
    }
    if(selectedCategory.value === '3'){
        category_name.value = "환자 이야기"
    }
    console.log(category_name.value)
};


</script>

<style lang="scss" scoped>
.imageBackground {
  width: 100%;
  height: 100%;
  z-index: 1; 
 
}



@import "@/assets/scss/pages/_postcreate.scss"
</style>