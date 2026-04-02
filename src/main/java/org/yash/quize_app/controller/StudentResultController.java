package org.yash.quize_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yash.quize_app.dto.OverallResultDTO;
import org.yash.quize_app.dto.QuizAttemptResultDTO;
import org.yash.quize_app.entity.Attempt;
import org.yash.quize_app.entity.User;
import org.yash.quize_app.repository.AttemptRepository;
import org.yash.quize_app.repository.QuizQuestionRepository;
import org.yash.quize_app.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/student/results")
public class StudentResultController {

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<OverallResultDTO> getResults(Authentication authentication) {
        String email = authentication.getName();
        User student = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        List<Attempt> attempts = attemptRepository.findByStudentId(student.getId());

        int totalAttempted = attempts.size();
        int sumScores = 0;
        int bestScore = 0;

        List<QuizAttemptResultDTO> attemptResults = new ArrayList<>();

        for (Attempt attempt : attempts) {
            int marksObtained = attempt.getScore() != null ? attempt.getScore() : 0;
            sumScores += marksObtained;
            if (marksObtained > bestScore) {
                bestScore = marksObtained;
            }

            // Calculate total marks for a quiz
            int totalMarks = quizQuestionRepository.findByQuizId(attempt.getQuiz().getId())
                    .stream()
                    .mapToInt(qq -> qq.getQuestion().getMarks() != null ? qq.getQuestion().getMarks() : 1)
                    .sum();

            double percentage = totalMarks > 0 ? ((double) marksObtained / totalMarks) * 100 : 0;

            String subjectName = "Unknown Subject";
            String topicName = "Unknown Topic";
            
            if (attempt.getQuiz() != null && attempt.getQuiz().getTopic() != null) {
                topicName = attempt.getQuiz().getTopic().getName();
                if (attempt.getQuiz().getTopic().getSubject() != null) {
                    subjectName = attempt.getQuiz().getTopic().getSubject().getName();
                }
            }

            QuizAttemptResultDTO dto = QuizAttemptResultDTO.builder()
                    .attemptId(attempt.getId())
                    .subjectName(subjectName)
                    .topicName(topicName)
                    .quizDate(attempt.getQuiz() != null ? attempt.getQuiz().getScheduledDate() : null)
                    .totalMarks(totalMarks)
                    .marksObtained(marksObtained)
                    .percentage(Math.round(percentage * 100.0) / 100.0)
                    .status(attempt.getStatus() != null ? attempt.getStatus().name() : "UNKNOWN")
                    .build();

            attemptResults.add(dto);
        }

        double averageScore = totalAttempted > 0 ? (double) sumScores / totalAttempted : 0;

        OverallResultDTO overallResult = OverallResultDTO.builder()
                .totalAttempted(totalAttempted)
                .averageScore(Math.round(averageScore * 100.0) / 100.0)
                .bestScore(bestScore)
                .attempts(attemptResults)
                .build();

        return ResponseEntity.ok(overallResult);
    }
}
