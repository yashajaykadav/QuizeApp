package org.yash.quize_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer duration;

    private LocalDate scheduledDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
}