package org.yash.quize_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.quize_app.entity.*;
import org.yash.quize_app.repository.*;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class AttemptService {

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Attempt startAttempt(String email, Long quizId) {
        User student = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));

        Attempt attempt = new Attempt();
        attempt.setStudent(student);
        attempt.setQuiz(quiz);
        attempt.setStartTime(LocalDateTime.now());
        attempt.setScore(0);
        attempt.setStatus(AttemptStatus.COMPLETED);

        return attemptRepository.save(attempt);
    }

    public void submitAttempt(Long attemptId, Map<Long, String> selectedOptions) {
        Attempt attempt = attemptRepository.findById(attemptId).orElseThrow(() -> new RuntimeException("Attempt not found"));

        int score = 0;

        for (Map.Entry<Long, String> entry : selectedOptions.entrySet()) {
            Long questionId = entry.getKey();
            String selectedOption = entry.getValue();

            Question question = questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));

            Answer answer = new Answer();
            answer.setAttempt(attempt);
            answer.setQuestion(question);
            answer.setSelectedOption(selectedOption);

            if (question.getCorrectAnswer().equalsIgnoreCase(selectedOption)) {
                score += (question.getMarks() != null ? question.getMarks() : 1);
            }

            answerRepository.save(answer);
        }

        attempt.setEndTime(LocalDateTime.now());
        attempt.setScore(score);
        attempt.setStatus(AttemptStatus.COMPLETED);
        attemptRepository.save(attempt);
    }
}
