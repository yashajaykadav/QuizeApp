package org.yash.quize_app.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class QuizAttemptResultDTO {
    private Long attemptId;
    private String subjectName;
    private String topicName;
    private LocalDate quizDate;
    private Integer totalMarks;
    private Integer marksObtained;
    private Double percentage;
    private String status;
}
