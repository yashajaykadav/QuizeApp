package org.yash.quize_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "attempts")
@Getter
@Setter
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    private Integer score = 0;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('COMPLETED','MISSED') default 'COMPLETED'")
    private AttemptStatus status = AttemptStatus.COMPLETED;
}
