import { ref, computed, watch } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import axios from 'axios'

export const useCounterStore = defineStore('counter', () => {
  const router = useRouter()
  const tab = ref('all')
  
  const go_tab = watch(tab, (newValue) => {
    // 탭 값에 따라 board_id를 업데이트하는 로직
    switch(newValue) {
      case `all`:
        tab.value = `all`; // "전체" 탭에 해당하는 board_id 설정
        break;
      case 1:
        tab.value = 1; // "정보 글" 탭에 해당하는 board_id 설정
        break;
      case 2:
        tab.value = 2; // "치료 후기" 탭에 해당하는 board_id 설정
        break;
      case 3:
        tab.value = 3; // "환자 이야기" 탭에 해당하는 board_id 설정
        break;
      default:
        tab.value = `all`; // 기본값
    }
  })
  return { tab }
})
