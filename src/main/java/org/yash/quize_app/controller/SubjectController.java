package org.yash.quize_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yash.quize_app.entity.Subject;
import org.yash.quize_app.service.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Subject>create(@RequestBody  Subject subject) {
        return ResponseEntity.ok(subjectService.createSubject(subject));
    }

    @GetMapping
    public ResponseEntity<List<Subject>>getAll(){
        return ResponseEntity.ok(subjectService.getAllSubject());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id){
        subjectService.deleteSubject(id);
        return ResponseEntity.ok("Subject deleted");
    }

}
