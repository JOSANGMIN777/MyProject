<template>
    <form class="search"  @submit.prevent="onSearch">
     
      <select class="SelectSearch" v-model="selectedOption" name="WhichSearch">
        <option id="title" value="title">제목</option>
        <option id="content" value="content">내용</option>
        <option id="author" value="author">작성자</option>
      </select>
      <button >
        <font-awesome-icon icon="chevron-down" class="search-icon2" />
      </button>

      <input class="keyword" v-model="keyword" type="text" name="search" maxlength="255" value="" autocomplete="off">

      <button class="img-button" type="submit" name="click" >
        <font-awesome-icon icon="magnifying-glass" class="search-icon"/>
      </button>

    </form>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { defineEmits } from 'vue'
import { useCrudStore } from '@/stores/crud';
const crud = useCrudStore()
const selectedOption = ref('title') // 기본값으로 'title'을 선택
const keyword = ref('') // 입력된 검색어를 저장할 ref 생성
const router = useRouter()

const onSearch = async() => {
  const key = keyword.value
  const select = selectedOption.value
  await crud.board_search(select, key)

};



watch(router.currentRoute, (newRoute) => {
  // 라우트가 변경될 때마다 선택된 옵션과 검색어를 초기화
  selectedOption.value = newRoute.query.searchBy || 'title'
  keyword.value = newRoute.query.keyword || ''
});


</script>

<style scoped>
.search-icon {
  display: flex;
  color: gray;
  position: absolute;
  margin-left: 20px;
  margin-top: -14px;
}

.keyword {
  border: none;
  border-bottom: 3px solid rgb(108, 159, 156);
  outline: none;
  background: transparent;
  width: 250px;
  color: black;
}

select {
  /* display: flex; */
  padding-left: 0px;
  margin-bottom: -15px;
  border: none;
  outline: none;
  width: 80px;
  background: transparent;
  color: rgb(61, 61, 61);
  z-index: 1;
}

.search-icon2 {
  color: gray;
  margin-right: 30px;
  margin-left: -25px;
  z-index: -1;
}

/* .modal {
  overflow-y: auto;
  max-height: 100%;
} */

/* 홈화면에서의 서치 요소(작동 안하고, 모달을 띄우는데만 사용) */
/* #home-search {
  display: flex;
  justify-content: center;
  margin-bottom: 80px;
}

#home-search-box input {
  font-family: 'NanumSquareNeo-Variable';
  outline: none;
  border: none;
  border-bottom: white solid;
  background-color: #ffffff00;
  color: black;
}

 #home-search-box input:hover {
  border-bottom: gray solid;
  transition: all 0.2s;
  transform: scale(1.03);
}

#home-search-box img {
  width: 35px;
  margin-right: 10px;
} */

/* 서치용 모달 내부 요소 */
/* #search-modal {
  z-index: 10;
  position: fixed;
  top: 0px;
  width: 100vw;
  height: 100vw;
}

#search-modal-search {
  z-index: 20;
  width: 85%;
  position: fixed;
  top: 330px;
  display: flex;
  align-items: center;
  flex-direction: column;
  flex-wrap: nowrap;
}

#search-modal-search-box {
  position: relative;
  z-index: 30;
  padding: 10px 60px;
  border-bottom: 3px solid rgba(108, 159, 156, 0.8);
  margin-top: -10px;
}

#search-modal-search-box input {
  font-family: 'NanumSquareNeo-Variable';
  outline: none;
  border: none;
  background-color: #ffffff00;
  color: black;
}

#search-modal-search-box input:hover {
  transition: all 0.2s;
  transform: scale(1.03);
} */

/* #search-modal-search-box img {
  width: 35px;
  margin-right: 10px;
}

#search-modal-result {
  width: 100%;
  max-width: 900px;
  z-index: 30;
} */

/* 배경 애니메이션 */
/* #search-modal-bg {
  z-index: 10;
  position: fixed;
  top: 0px;
  right: 0px;
  width: 100%;
  height: 100%;
  background-color: rgba(108, 159, 156, 0.9);
  /* backdrop-filter: blur(20px); */


</style>