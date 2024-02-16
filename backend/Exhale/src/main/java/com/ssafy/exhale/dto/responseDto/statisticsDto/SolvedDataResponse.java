package com.ssafy.exhale.dto.responseDto.statisticsDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolvedDataResponse {
    @JsonProperty("course_id")
    private long courseId;
    @JsonProperty("course_name")
    private String courseName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("start_of_week")
    private LocalDateTime startOfWeek;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("end_of_week")
    private LocalDateTime endOfWeek;
    @JsonProperty("correct_count")
    private int correctCount;
    @JsonProperty("wrong_count")
    private int wrongCount;
    @JsonProperty("solved_time")
    private int solvedTime;
}
