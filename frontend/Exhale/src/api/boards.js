import axios from "axios";

const boardList = async (page, board_id) => {
  return await axios.get(`https://www.ssafyexhale.shop/api/boards/${board_id}?page=${page}`)
}


//게시글 검색 
const boardSearch = async (board_id, searchType, searchContent, page) => {
    return await axios.post('https://www.ssafyexhale.shop/api/boards/search', {
        board_id : board_id,
        search_type : searchType, // title, content, author
        search_content : searchContent,
        page : page // int 
})};

    
//글 상세 정보 조회 
const boardDetail = async (article_id) => {
    console.log('디테일호출',article_id)
    return await axios.get(`https://www.ssafyexhale.shop/api/articles/${article_id}`, {
    })};


//게시글 생성 
const articleCreate = async (title, content, thumbnail, board_id, accessToken) => {
  if (!title && !content && !board_id ) {
    alert('게시물 내용을 확인해주세요.')
    return
  }
  try {
    const response = await axios.post(
      "https://www.ssafyexhale.shop/api/articles",
      {
        title: title,
        content: content,
        thumbnail: thumbnail,
        board_id: board_id,
      },
      {
        headers: {
          Authorization: `${accessToken}`,
        },
      }
    );
    if (response.status === 200) {
      alert("게시글 등록 완료!");
    }

  } catch (error) {
    console.error("게시글 등록에 실패하였습니다.", error);
    alert("로그인 후 이용해주세요."); // 원래는 status분기를 갈라서 다른 에러메시지를 출력해야함.
  }
};

//이미지 저장 x
const saveImg = async (file) => {
 try {
    const response = await axios.post('https://www.ssafyexhale.shop/api/articles/image', {
    file : file
  }, { 
    headers : {
        "Content-Type": "multipart/form-data"
    }
  }
  )
 } catch (error) {
    console.error('이미지를 저장할 수 없어요', error)
 }}

//게시글 수정 
 const updatePost = async (title, content, thumbnail,accessToken, id) => {
    try {
    const response =  await axios.put(`https://www.ssafyexhale.shop/api/articles/${id}`,{
        title: title,
        content: content,
        thumbnail: null
    }, {
        headers: {
            'Authorization': `${accessToken}`
        }
    })
    alert('게시글 수정이 완료되었습니다.')

    } catch(error) {
        console.log('게시글 수정 실패',error)
        alert('수정 권한이 없습니다.')
    }
 }
 
 // 게시글 삭제 
 const deletePost = async (id, token) => {
  try {
    const response = await axios.delete(`https://www.ssafyexhale.shop/api/articles/${id}`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `${token}`
      },
      data: {
        article_id: id
      }
    });
    alert('삭제되었습니다.');
  } catch (error) {
    console.error(error);
  }
};





// 댓글목록
const getComments = async (article_id) => {
return await axios.get(`https://www.ssafyexhale.shop/api/comments/list/${article_id}`)
};

// 댓글수정
const editComments = async (comment_id, content, token) => {
    try {
      const response = await axios.put(`https://www.ssafyexhale.shop/api/comments/${comment_id}`, {
        content: content
      }, {
        headers: {
          'Authorization': `${token}`,
          'Content-Type': 'application/json'
        }
      });
      if (response.status === 200) {
        alert('수정되었습니다.');
        location.reload()
      }
    } catch (error) {
      console.error(error);
    }
  };

// 댓글 삭제
const deleteComments = async (comment_id, token) => {
  return await axios.delete(`https://www.ssafyexhale.shop/api/comments/${comment_id}`, { 
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `${token}`
    }
  })
};



//   try {
//     const response = await axios.post(
//       "https://www.ssafyexhale.shop/api/articles/image/{article_id}",
//       {},
//       {
//         headers: {
//           "Content-Type": "multipart/form-data",
//         },
//       }
//     );
//   } catch (error) {
//     console.error("이미지를 저장할 수 없어요", error);
//   }


export {
    deletePost,
    boardList,
    boardSearch,
    boardDetail,
    articleCreate,
    saveImg,
    updatePost,
    getComments,
    editComments,
    deleteComments,
};
