package org.yash.quize_app.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class QuizRequest {

    private Long topicId;
    private Integer duration;
    private LocalDate scheduledDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private List<Long> questionIds; // 🔥 IMPORTANT
}