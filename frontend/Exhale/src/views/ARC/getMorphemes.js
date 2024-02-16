import { getMorphemeList } from '@/api/course.js';
import { token } from './FollowUpSpeech.vue';

const getMorphemes = async () => {
const { data } = await getMorphemeList(token);
// const morphemeList = morphemeListData.response;
// const statistics = generateStatistics(morphemeList, answer, text);
};
