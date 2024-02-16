package com.ssafy.exhale.dto.logicDto;

import com.ssafy.exhale.domain.Board;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
     Integer id;
     String name;

     public static BoardDto of(Integer id, String name){
         return new BoardDto(id, name);
     }

     public static BoardDto from(Board entity){
         return BoardDto.of(
                 entity.getId(),
                 entity.getName()
         );
     }

     public Board toEntity(){
         return Board.of(id, name);
     }
}
