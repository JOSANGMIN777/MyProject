<template>
    <div>
        <button @click="newWidow">이름대기</button>
        <button @click="openFluency">유창성</button>
        <button @click="openFollowUp">따라말하기</button>
        <button @click="openMatchingImage">듣기이해력(그림짝짓기)</button>
        <button @click="openMatchingText">듣기이해력(텍스트고르기)</button>
    </div>
</template>

<script setup>
// const getCourses = async () => {
//   try {
//     const { data } = await getCourseList(token);
//     console.log(data);
//   } catch (error) {
//     console.error(error); 
//   }
// };
// getCourses();
const appURL = import.meta.env.VITE_APP_API_URL;
const newWidow = () => {
    window.open(`${appURL}/say-object`);
}
const openFluency = () => {
    window.open(`${appURL}/fluency`);
}
const openFollowUp = () => {
    window.open(`${appURL}/follow-up-speech`);
}
const openMatchingImage = () => {
    window.open(`${appURL}/matching-image`);
}
const openMatchingText = () => {
    window.open(`${appURL}/matching-text`);
}
</script>

<style lang="scss" scoped>

</style>