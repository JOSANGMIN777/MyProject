<template>
  <div v-if="lists.length > 0">
  <div v-for="list in lists" :key="list.id" >
    <div v-if="editMode && currentComment.id === list.id">
    <input type="text" v-model="currentComment.content">
    <button @click="submitEdit" >저장</button> |
    <button @click="cancelEdit">취소</button>
  </div>
  <div class="commentContent"  v-else>
    <div class="mainComment">
    <h4 style="display: inline; font-size: 100%; color: rgb(170, 170, 170); margin-bottom: 20px; margin-right: 20px;">{{ list.nickname }}</h4>
    
    <h4 style="display: inline; font-size: 120%;">{{ list.content }}</h4>
    <br>
    <p style="margin-top: 15px; color: gray;">{{ list.createDate.substring(0, 10) }}</p>
    
    <br>
  </div>
    <div class="editDelete" style="display: flex;" v-if="memberId == list.memberId">
      <a @click="startEdit(list)" style="cursor: pointer; margin-right: 1vw;">수정</a> | 
      <p @click="delete_Comments(list)" style="display: inline; cursor: pointer; margin-left: 1vw;" >삭제</p>
    </div>
  </div>
    <hr style="width: 70%; margin-left: 15vw;">

  </div>
</div>
  <div v-else>
    <p style="text-align: center;">아직 댓글이 없습니다. 댓글을 남겨보세요!</p>
    <hr>
  </div>
</template>

<script setup>
import { getComments, editComments, deleteComments } from '@/api/boards';
import { onMounted,ref,reactive } from 'vue';
import { useRoute } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
const store = useAuthStore()
const token = store.jwtToken
const memberId = ref(store.memberId);

const route = useRoute()
const article_id = route.params.id
const lists = ref([])
const editMode = ref(false);
const currentComment = reactive({ id: null, content: ' ' });
onMounted(async () => {
  try {
    const response = await getComments(article_id);
    lists.value = response.data.response;  
   
    console.log(lists.value)
  } 
  catch (error) {
    console.error('댓글을 불러오는데 실패했습니다', error);
  }
});

const startEdit = (comment) => {
  editMode.value = true;
  currentComment.id = comment.id;
  currentComment.content = comment.content;
};

// 댓글 삭제
const delete_Comments = async (comment) => {
  if(confirm('댓글을 삭제하시겠습니까?')){
    try{
      const data = await deleteComments(
        comment.id,
        token
      );
      if (data.status === 200) {
        alert('삭제되었습니다.');
        location.reload()
      }
    }catch(error){

    }
    
    console.log(data);
    // lists.value = lists.value.filter((item) => item.id !== comment.id);

  }
};


const submitEdit = async () => {
  if (editMode.value && currentComment.id && currentComment.content) {
    await editComments(currentComment.id, currentComment.content, token);
    // 성공적으로 수정된 후에는 editMode를 비활성화합니다.
    editMode.value = false;
  }
};
</script>

<style lang="scss" scoped>


.commentContent {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  // align-items: center;
  margin-bottom: 3vh;
  margin-top: 5vh;
  width: 93%;
  margin-left: 4.5vw;
}
</style>