// ARC/MorphemeStatistics.js

import { compareMorpheme } from '@/components/ARC/Morpheme.js';

function generateStatistics(morphemeList, answer, text) {
    const differences = compareMorpheme(answer, text);
    const list = [];

    // 형태소 차이를 기반으로 통계 정보 업데이트
    differences.forEach(({ type, morpheme1, morpheme2 }) => {
        if(morpheme1!=''){
            // morpheme1과 morpheme2를 기반으로 해당하는 letter_id 찾기
            const letter_id = morphemeList.find(morpheme => morpheme.letter_char === morpheme1 && morpheme.type === type)?.letter_id;

            // 이미 list에 morpheme1이 존재하는 경우 해당 정보를 사용
            const existingItem = list.find(item => item.letter_id === letter_id);
            if (existingItem) {
                if (morpheme1 === morpheme2) {
                    existingItem.correct_cnt++;
                } else {
                    existingItem.wrong_cnt++;
                }
            } else {
                // morpheme1이 list에 존재하지 않는 경우 statistics 생성
                const statistics = {
                    letter_id: letter_id,
                    correct_cnt: morpheme1 === morpheme2 ? 1 : 0,
                    wrong_cnt: morpheme1 === morpheme2 ? 0 : 1
                };
                list.push(statistics);
            }
        }
    });

    return list; // 변환된 결과 리스트 반환
}


export {
    generateStatistics
};
