package com.semillero.ecosistema.repositories;

import com.semillero.ecosistema.models.AnswerModel;
import com.semillero.ecosistema.models.QuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository <AnswerModel, Long>{
    AnswerModel getByQuestion(QuestionModel questionModel);
}
