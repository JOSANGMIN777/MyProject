// hangul_diff.js

// 초성(19개)
const CHO_HANGUL = ['ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ','ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'];
// 중성(21개)
const JUNG_HANGUL = ['ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'];
// 종성(28개)
const JONG_HANGUL = ['', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'];
const CHO_PERIOD = Math.floor('까'.charCodeAt(0) - '가'.charCodeAt(0)); // 588 ( 28 * 21 )
const JUNG_PERIOD = Math.floor('개'.charCodeAt(0) - '가'.charCodeAt(0)); // 28
const HANGUL_START_CHARCODE = '가'.charCodeAt(0);
const HANGUL_END_CHARCODE = '힣'.charCodeAt(0);

// 조합 된 글자인지 체크 (가 ~ 힣 사이)
function isHangul(charCode) { 
  return HANGUL_START_CHARCODE <= charCode && charCode <= HANGUL_END_CHARCODE;
}

// 자음과 모음을 분리하는 함수
function divideHangul(letter) {
    const letterCode = letter.charCodeAt(0);
  
    if (!isHangul(letterCode)) {
      return letter;
    }
  
    const charCode = letterCode - HANGUL_START_CHARCODE;
  
    const choIndex = Math.floor(charCode / CHO_PERIOD);
    const jungIndex = Math.floor((charCode % CHO_PERIOD) / JUNG_PERIOD);
    const jongIndex = charCode % JUNG_PERIOD;
  
    return {
      cho: CHO_HANGUL[choIndex],
      jung: JUNG_HANGUL[jungIndex],
      jong: JONG_HANGUL[jongIndex],
    };
}

function compareMorpheme(text1, text2) {
    const result = [];

    const characters1 = text1.split('');
    const characters2 = text2.split('');

    for (let i = 0; i < characters1.length; i++) {
        const char1 = characters1[i] || '';
        const char2 = characters2[i] || '';

        const { cho: cho1, jung: jung1, jong: jong1 } = divideHangul(char1);
        const { cho: cho2, jung: jung2, jong: jong2 } = divideHangul(char2);

        result.push({ type: 'CONSONANT_LETTER', char1, char2, morpheme1: cho1, morpheme2: cho2 });
        result.push({ type: 'MIDDLE_VOWEL_LETTER', char1, char2, morpheme1: jung1, morpheme2: jung2 });
        result.push({ type: 'LAST_CONSONANT_LETTER', char1, char2, morpheme1: jong1, morpheme2: jong2 });
    }

    return result;
}


export {
    compareMorpheme
};
