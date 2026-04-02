package org.yash.quize_app.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class OverallResultDTO {
    private int totalAttempted;
    private double averageScore;
    private int bestScore;
    private List<QuizAttemptResultDTO> attempts;
}
