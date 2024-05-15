package com.semillero.ecosistema.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question")
public class QuestionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private boolean deleted = false;
    //private CategoryQuestionModel categoryQuestionModel;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "answer_id", referencedColumnName = "id")
    private AnswerModel answer;


    @ManyToOne
    @JoinColumn(name = "answer_father")
    private AnswerModel answerFather;

    public QuestionModel(String question) {
        this.question = question;
    }
    public QuestionModel(Long id) {
        this.id = id;
    }

    public QuestionModel(Long id, String question, AnswerModel answer) {
        this.id = id;
        this.question = question;
        this.answerFather = answer;
    }
}
