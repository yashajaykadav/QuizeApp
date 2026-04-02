package org.yash.quize_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Column(name = "option_a", nullable = false)
    private String optionA;

    @Column(name = "option_b", nullable = false)
    private String optionB;

    @Column(name = "option_c", nullable = false)
    private String optionC;

    @Column(name = "option_d", nullable = false)
    private String optionD;

    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer; // A / B / C / D

    private Integer marks = 1;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;
}