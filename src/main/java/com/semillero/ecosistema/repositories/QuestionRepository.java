package com.semillero.ecosistema.repositories;

import com.semillero.ecosistema.models.QuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionModel, Long> {

    public List<QuestionModel> findAllByAnswerFatherIsNull();
}
