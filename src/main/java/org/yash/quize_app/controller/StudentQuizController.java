package org.yash.quize_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yash.quize_app.entity.Quiz;
import org.yash.quize_app.entity.QuizQuestion;
import org.yash.quize_app.dto.QuestionResponse;
import org.yash.quize_app.repository.QuizRepository;
import org.yash.quize_app.repository.QuizQuestionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student/quizzes")
public class StudentQuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @GetMapping("/today")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<Quiz>> getTodayQuizzes() {
        return ResponseEntity.ok(quizRepository.findByScheduledDate(LocalDate.now()));
    }

    @GetMapping("/{quizId}/questions")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<QuestionResponse>> getQuizQuestions(@PathVariable Long quizId) {
        List<QuizQuestion> quizQuestions = quizQuestionRepository.findByQuizId(quizId);
        List<QuestionResponse> questions = quizQuestions.stream()
                .map(qq -> QuestionResponse.fromEntity(qq.getQuestion()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(questions);
    }
}
