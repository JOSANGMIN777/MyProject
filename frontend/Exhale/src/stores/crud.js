import { defineStore } from "pinia";
import { useRouter } from "vue-router";
import {ref, computed, watch} from 'vue'
import { boardList, boardSearch  } from "@/api/boards";


export const useCrudStore = defineStore('crud', ()=> {
  const tab = ref('all')
  const curPage = ref(1)
  const ITEM_PER_PAGE = ref(10);
  const PAGE_PER_SECTION = ref(5);
  const totalPage = ref('')
  const isLoading = ref(false)
  const posts = ref([])

  const thumbnails = ref([])



  const board_list = async () => {
    try {
      const response = await boardList(
        curPage.value,
        tab.value
      )
      posts.value = response.data.response.article_list
      totalPage.value = response.data.response.article_total_count
      ITEM_PER_PAGE.value = response.data.response.page_size
      PAGE_PER_SECTION.value = response.data.response.page_total_count  
      scrollToTop()
      function scrollToTop() {
        window.scrollTo({
          top: 100,
          behavior: 'auto'
        });
      }
          
    }
    catch (error) {
      console.log(error)
    }
  }

  const board_search = async (select, key) => {
    try {
      const response = await boardSearch(
        tab.value,
        select,
        key,
        curPage.value,
      )
      posts.value = response.data.response.article_list
      totalPage.value = response.data.response.article_total_count
      ITEM_PER_PAGE.value = response.data.response.page_size
      PAGE_PER_SECTION.value = response.data.response.page_total_count
      } catch (err) {
        console.log(err)
      }
    }



  function setCurrentPage(newPage) {
      curPage.value = newPage;
      board_list(newPage, tab.value)
  }


  // watch (tab, (newValue)=> {
  //     if (tab.value === newValue)
  //     {curPage.value = 1}
  // })

return {tab, curPage, setCurrentPage, ITEM_PER_PAGE, PAGE_PER_SECTION,totalPage,isLoading, board_list, board_search, posts}
})