package org.yash.quize_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.yash.quize_app.dto.SubmitAttemptRequest;
import org.yash.quize_app.entity.Attempt;
import org.yash.quize_app.service.AttemptService;

@RestController
@RequestMapping("/api/student/attempts")
public class StudentAttemptController {

    @Autowired
    private AttemptService attemptService;

    @PostMapping("/start/{quizId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Attempt> startAttempt(
            @PathVariable Long quizId,
            Authentication authentication
    ) {
        String email = authentication.getName();
        return ResponseEntity.ok(attemptService.startAttempt(email, quizId));
    }

    @PostMapping("/{attemptId}/submit")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> submitAttempt(
            @PathVariable Long attemptId,
            @RequestBody SubmitAttemptRequest request
    ) {
        attemptService.submitAttempt(attemptId, request.getSelectedOptions());
        return ResponseEntity.ok("Quiz submitted successfully!");
    }
}
