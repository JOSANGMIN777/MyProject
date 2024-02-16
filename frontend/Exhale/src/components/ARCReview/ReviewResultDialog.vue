<template>
      <v-dialog
        v-model="dialog"
        fullscreen
        :scrim="false"
        transition="dialog-bottom-transition"
      >        
        <template v-slot:activator="{ }">
          <button class="finishBtn" @click="isExit=true; dialog=true;">
            <img src="@/assets/quit.svg" class="quit">
            복습 종료</button>
          <button class="pauseBtn" @click="isPause=!isPause; dialog=true;">
            <img src="@/assets/pause.svg" class="pause">
            일시 정지</button>
        </template>
   
        <v-card class="custom-dialog-card">
          
            <!-- <v-btn
              icon
              dark
              @click="dialog = false"
            >
              <v-icon>mdi-close</v-icon>
            </v-btn> -->
            <div class="content">
              <div class="images">
                <div v-if="isExit">
                  <img src="../../assets/stop.svg"/>
                </div>
                <div v-else-if="isComplete">
                  <img src="../../assets/complete.svg"/>
                </div>
                <div v-else-if="isPause">
                  <img src="../../assets/stop.svg"/>  <!-- todo 사진 변경 -->
                </div>
                <div v-else-if="dialog">
                  <img v-if="isRight" src="../../assets/right.png"/>              
                  <img v-if="!isRight" src="../../assets/wrong.svg"/>
                </div>
              </div>
              <div class="sttText" v-if="!isExit && !isComplete && !isPause">
                <div :class="isRight? 'right':'wrong'">{{ sttText }}</div>
              </div>
              <div class="buttons">
                <div v-if="isExit">
                  <button @click="isClose = true; isExit=false; dialog=false;">종료하기</button>
                </div>
                <div v-else-if="isComplete">
                  <button @click="isClose = true; dialog=false;">창 닫기</button>
                </div>
                <div v-else-if="isPause">
                  <button @click="isReturn=true; dialog=false;">돌아가기</button>
                </div>
                <div v-else-if="dialog">
                  <button v-if="isRight" @click="reviewTick=true;">삭제하기</button>
                  <button v-if="!isRight" @click="againTick=true; dialog=false">다시풀기</button>
                  <button @click="nextTick = !nextTick;  dialog=false;">넘어가기</button>
                </div>
              </div>
            </div>
        </v-card>
      </v-dialog>
  </template>
    <script setup>
        import { isReactive } from 'vue';
import { ref, defineProps ,defineEmits, watch} from 'vue';
        const props = defineProps({
          dialog: Boolean,
          isRight: Boolean,
          reviewTick: Boolean,
          againTick: Boolean,
          isPause: Boolean,
          isReturn: Boolean,
          isComplete: Boolean,
          sttText: String,
        });  
        const dialog = ref(props.dialog);
        const isRight = ref(props.isRight);
        const isPause = ref(props.isPause);
        const isReturn = ref(props.isReturn);
        const isComplete = ref(props.isComplete);
        const nextTick = ref(false);
        const reviewTick = ref(props.reviewTick);
        const againTick = ref(props.againTick);
        const sttText = ref(props.sttText);

        const isExit = ref(false);
        const isClose = ref(false);

        const emit = defineEmits([
          "update:dialog",
          "update:nextTick", 
          "update:reviewTick",  
          "update:againTick", 
          "update:isClose", 
          "update:isPause",
          "update:isExit",
          "update:isReturn",
        ]);
        watch(dialog, () => {
          emit('update:dialog', dialog.value);
        });  
        watch(nextTick, () => {
          emit('update:nextTick', nextTick.value);
        });
        watch(reviewTick, () => {
          emit('update:reviewTick', reviewTick.value);
        });
        watch(againTick, () => {
          emit('update:againTick', againTick.value);
        });        
        watch(isClose, () => {
          emit('update:isClose', isClose.value);
        });
        watch(isPause, () => {
          emit('update:isPause', isPause.value);
        });
        watch(isExit, () => {
          emit('update:isExit', isExit.value);
        });
        watch(isReturn, () => {
          emit('update:isReturn', isReturn.value);
        });

        watch(props, () => {
            dialog.value = props.dialog;
            isRight.value = props.isRight;
            againTick.value = props.againTick;
            isPause.value = props.isPause;
            isReturn.value = props.isReturn;
            isComplete.value = props.isComplete;
        });
        watch(()=>props.sttText, () => {
          console.log(props)
          sttText.value = props.sttText;
        })
    </script>

  <style lang="scss" scoped>
  .dialog-bottom-transition-enter-active,
  .dialog-bottom-transition-leave-active {
    transition: transform .2s ease-in-out;
  }

  .custom-dialog-card {
    background-color: rgba(108, 159, 156, 0.9) !important;
  }
  .content{
    width: 500px;
    margin: auto;
    .images {
      display: flex;
      justify-content: center;
      margin-bottom: 50px;
    }
  }
  .buttons{
    display: flex;
    justify-content: space-around;
    > div > button{
      padding: 2px 20px;
      border: 5px solid rgba(255, 255, 255, 0.62);
      background: rgba(255, 255, 255,0);
      border-radius: 90px;
      color: rgba(255, 255, 255);
      font-weight: 900;
      margin: 30px 50px;
    }

  }

  .finishBtn{
    position: fixed;
    top: 135px;
    left: 1290px;
    border: 3px solid rgb(175, 175, 175);
    border-radius: 30px;
    font-size: 18px;
    width: 117px;
    height: 40px;
    padding: -5px 0px;
    font-family: 'NotoSansKR';
    color: gray;
    & .quit {
    width: 21px;
    padding-bottom: 3px;
    opacity: 40%;
  }
  }
  .pauseBtn{
    position: fixed;
    top: 575px;
    left: 1290px;
    border: 3px solid rgb(175, 175, 175);
    border-radius: 30px;
    font-size: 18px;
    width: 117px;
    height: 40px;
    font-family: 'NotoSansKR';
    color: gray;
    & .pause {
    width: 21px;
    padding-bottom: 3px;
    opacity: 40%;
    }
  }

  .sttText{
    display: flex;
    justify-content: center;
    color: rgba(255, 255, 255);
    font-family: 'NotoSansKR';
    font-size: 18px;
    font-weight: 900;

  }
  .wrong{
    color : rgba(231, 170, 170, 0.9);
    font-size: 150%;
  }
  .right{
    color : rgba(166, 247, 186, 0.9);
    font-size: 150%;
  }

  </style>

