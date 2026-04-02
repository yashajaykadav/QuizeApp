package org.yash.quize_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yash.quize_app.entity.Question;
import org.yash.quize_app.service.QuestionService;

@RestController
@RequestMapping("/api/admin")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/topics/{topicId}/questions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Question>createQuestion(
            @PathVariable Long topicId,
            @RequestBody Question question
    ){
return ResponseEntity.ok(questionService.createQuestion(topicId, question));
    }

    @GetMapping("/topics/{topicId}/questions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<java.util.List<Question>> getQuestionsByTopic(@PathVariable Long topicId) {
        return ResponseEntity.ok(questionService.getQuestionsByTopic(topicId));
    }
}
