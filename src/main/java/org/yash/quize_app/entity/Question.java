package org.yash.quize_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String questionText;

    @ManyToOne
    @JoinColumn(name = "topic_id" , nullable = false)
    private Topic topic;

    @OneToMany(mappedBy = "question" ,cascade = CascadeType.ALL)
    private List<Answer> answers;
}
