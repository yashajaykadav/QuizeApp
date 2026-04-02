package org.yash.quize_app.dto;

import lombok.Builder;
import lombok.Data;
import org.yash.quize_app.entity.Question;

@Data
@Builder
public class QuestionResponse {
    private Long id;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private Integer marks;

    public static QuestionResponse fromEntity(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .optionA(question.getOptionA())
                .optionB(question.getOptionB())
                .optionC(question.getOptionC())
                .optionD(question.getOptionD())
                .marks(question.getMarks())
                .build();
    }
}
