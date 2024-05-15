package com.semillero.ecosistema.services;

import com.semillero.ecosistema.dtos.questions.AnswerDto;
import com.semillero.ecosistema.dtos.questions.QuestionDto;
import com.semillero.ecosistema.models.AnswerModel;
import com.semillero.ecosistema.models.QuestionModel;
import com.semillero.ecosistema.repositories.AnswerRepository;
import com.semillero.ecosistema.repositories.QuestionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private  AnswerRepository answerRepository;
    @Autowired
    private  QuestionRepository questionRepository;

    @Autowired
    ModelMapper modelMapper;

        public List<QuestionDto> getInitialQuestions() {
            try{
            List<QuestionModel>  questions = questionRepository.findAllByAnswerFatherIsNull();
            List<QuestionDto> questionsDto = new ArrayList<>();
            for(QuestionModel question: questions){
                questionsDto.add(modelMapper.map(question, QuestionDto.class));
            }
            return questionsDto ;
    }catch(Exception e){
            throw e;
        }

    }

    public AnswerDto getById(Long id) {
            QuestionModel question = new QuestionModel(id);
        AnswerModel answer = answerRepository.getByQuestion(question);
        AnswerDto answerDto = modelMapper.map(answer, AnswerDto.class);
        List<QuestionDto> questionsDto = new ArrayList<>();

        for(QuestionModel questionModel: answer.getQuestions()){
        questionsDto.add(modelMapper.map(questionModel, QuestionDto.class));
    }
        answerDto.setQuestions(questionsDto);
        return answerDto;

    }

}
