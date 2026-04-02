package org.yash.quize_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yash.quize_app.dto.QuizRequest;
import org.yash.quize_app.entity.Quiz;
import org.yash.quize_app.service.QuizService;

@RestController
@RequestMapping("/api/admin")
public class QuizController {

    @Autowired
    private QuizService  quizService;

    @PostMapping("/quizzes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Quiz> createQuiz(
            @RequestBody QuizRequest request,
            Authentication authentication
    ) {
        String email = authentication.getName();
        return ResponseEntity.ok(quizService.createQuiz(request, email));
    }
}
