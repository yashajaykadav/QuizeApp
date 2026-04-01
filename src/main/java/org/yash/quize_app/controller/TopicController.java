package org.yash.quize_app.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yash.quize_app.entity.Topic;
import org.yash.quize_app.service.TopicService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping("/subjects/{subjectId}/topics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Topic> createTopic(@PathVariable Long subjectId, @Valid @RequestBody Topic topic){
        return ResponseEntity.ok(topicService.createTopic(subjectId, topic));
    }

    @GetMapping("/topics")
    public ResponseEntity<List<Topic>> getAllTopics(){
        return ResponseEntity.ok(topicService.getAllTopic());
    }
}
