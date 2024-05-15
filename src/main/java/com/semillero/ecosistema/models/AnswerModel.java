package com.semillero.ecosistema.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answer")
public class AnswerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @Length(max = 2000)
    private String answer;
    @Column
    private boolean deleted = false;

    @OneToOne(mappedBy = "answer")
    private QuestionModel question;


    @OneToMany(mappedBy = "answerFather")
    private List<QuestionModel> questions;



    public AnswerModel( String answer) {
        this.answer = answer;
    }

    public AnswerModel( Long id) {
        this.id = id;
    }

}
