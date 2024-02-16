<template>
<div :style="{ fontSize: fontSize + 'px' }">

  <div id="content">
  <header>
    <section class="main-nav">
      <div class="navbar-logo-link">
        <RouterLink :to="{ name: 'MainPage' }">
          <img src="@/assets/logo_green.png" alt="logo" class="navbar-logo" >
        </RouterLink>
      </div>
      <div class="menu" @click="toggleMenu">
        <PostMenu v-if="!show" />
        <CommunityMenu v-else />
      </div>
    </section>

      <section class="sub-nav1">
        <div id="breadcrum">
          <RouterLink class="breadlink" :to="{name: 'MainPage'}">메인 홈</RouterLink>
          >
          <RouterLink class="breadlink" :to="{name: 'PostWholeListView'}">커뮤니티</RouterLink>
          >
          <RouterLink class="breadlink" :to="{name: 'PostWholeListView'}">전체</RouterLink>
        </div>
        <button class="enlarge" @click="enlarge" style="margin-right: 84px; z-index: 10;">
        <img src="@/assets/plus.svg" class="plus">
        {{ msg }}
        </button> 
      </section>
    
      
<!-- vuetify -->
<div class="vutifyTabs">
    <v-tabs
      v-model="tab"
      color="#6C9F9C"
      align-tabs="center"
    >
      <v-tab 
        :value="all"
        @click="onClickTab(0)"
        :to="{name: 'PostWholeListView'}" 
        class="nav-link"
        :class="{ active: route.name === 'PostWholeListView'}"
        active-class="active"
      > 전체</v-tab>
      <v-tab 
        :value="1"
        @click="onClickTab(1)"
        :to="{name: 'PostInfoListView'}" 
        class="nav-link"
        :class="{ active: route.name === 'PostInfoListView' }"
        active-class="active"
      >정보 글</v-tab>
      <v-tab 
        :value="2"
        @click="onClickTab(2)" 
        :to="{name: 'PostReviewListView'}" 
        class="nav-link"
        :class="{ active: route.name === 'PostReviewListView' }"
        active-class="active"
      > 치료 후기</v-tab>
      <v-tab 
        :value="3"
        @click="onClickTab(3)"
        :to="{name: 'PostStoryListView'}" 
        class="nav-link"
        :class="{ active: route.name === 'PostStoryListView' }"
        active-class="active"
      > 환자 이야기</v-tab>
    </v-tabs>
  </div>

    </header>
    
    <main>
      <svg-icon type="mdi" :path="path"></svg-icon>
      
      <div class="box-container">
        
        <section>
         
            <PostSlider 
           
            />
          
        </section>
        
        <section class="box-item sub-nav3">
          <label><PostCreateBtn /></label> 
          <label><PostSearch/></label>
          <label>최신순</label>
        </section>
        
        <section class="box-item">
          <article>
          <div v-for="post in crud.posts" :key="post.id"  >
              <PostItem
              :thumbnail="post.thumbnail"
              :title="post.title"
              :content="post.content"
              :create_date="post.create_date"
              :id="post.id"
              @click="board_detail(post.id)"
              ></PostItem>
              
            </div>
          </article>
        </section>
        
        <section class="box-item">

          <div>
    <pagination/>
  </div>
        </section> 
      </div>
    </main>

    
  </div>
  
  
</div>
<scrollTop/>


<Footers style="margin-bottom: 5vh; margin-left: 1vw;" />

</template>

<script setup>
import { computed, onUpdated, ref, watch, onMounted,onUnmounted, defineProps } from 'vue'
import { useRoute, useRouter } from 'vue-router' 
import axios from 'axios'
import PostItem from '@/components/posts/PostItem.vue'
import Pagination from '@/components/functions/Pagination.vue'
import CommunityMenu from '@/components/modals/CommunityMenu.vue'
import PostMenu from '@/components/posts/PostMenu.vue'
import PostSlider from '@/components/posts/PostSlider.vue'
import PostSearch from '@/components/posts/PostSearch.vue'
import PostCreateBtn from '@/components/posts/PostCreateBtn.vue'
import Footers from '@/components/common/Footers.vue'
import {boardDetail, boardSearch} from '@/api/boards' 
import { useAuthStore } from "@/stores/auth";
import { useCrudStore } from '@/stores/crud';
import scrollTop from '@/components/functions/scrollTop.vue'
import { storeToRefs } from 'pinia'
const store = useAuthStore()
const crud = useCrudStore()
const {posts, curPage, tab, ITEM_PER_PAGE, PAGE_PER_SECTION, totalPage, isLoading} = storeToRefs(crud)

const accessToken =store.accessToken

const searchOption = ref(null);
const searchKeyword = ref('');


const filteredPosts = computed(() => {
  if (!searchOption.value || !searchKeyword.value) {
    return posts.value;
  }
  return posts.value.filter(post => {
    const valueToSearch = post[searchOption.value];
    return valueToSearch && valueToSearch.includes(searchKeyword.value);
  });
});


const show = ref(false)
function toggleMenu() {
  show.value = !show.value
}



const route = useRoute()
const router = useRouter()
const fontSize = ref(16);
const msg = computed(() => fontSize.value > 21 ? '원래대로' : '글자확대');
const enlarge = () => {
  fontSize.value ++;
  if (fontSize.value > 22) {
    fontSize.value = 16
  };
};

  const board_detail = async (article_id) => {
    try {
      const response = await boardDetail(
        article_id
      )
    
      router.push(`/posts/${article_id}`)
    } catch (error) {
      console.log('게시글을 불러올 수 없습니다.', error)
    }
}  

const onClickTab = () => {
  crud.curPage = 1
}

onMounted( async () => {
  
  await crud.board_list()
 
  
})

  onUnmounted( async () => {
    await crud.board_list()
    
  })

</script>
<style lang="scss" scoped>
  @import "@/assets/scss/layout/_article.scss";
  @import "@/assets/scss/layout/_grid.scss";
</style>