package com.semillero.ecosistema.controllers;

import com.semillero.ecosistema.dtos.questions.AnswerDto;
import com.semillero.ecosistema.dtos.questions.QuestionDto;
import com.semillero.ecosistema.models.AnswerModel;
import com.semillero.ecosistema.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/initial")
    public List<QuestionDto> getInitialQuestions (){
        return questionService.getInitialQuestions();
    }
    @GetMapping("/answer/{id}")
    public AnswerDto getAnswerQuestion ( @PathVariable Long id){
        return questionService.getById(id);
    }
//    @GetMapping("/question/{questionId}/answers")
//    public ResponseEntity<List<AnswerModel>> getAnswersByQuestionId(@PathVariable Long questionId) {
//        List<AnswerModel> answers = questionService.getAnswersByQuestionId(questionId);
//        return ResponseEntity.ok(answers);
//    }
}
