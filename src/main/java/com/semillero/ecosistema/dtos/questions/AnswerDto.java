package com.semillero.ecosistema.dtos.questions;

import com.semillero.ecosistema.models.QuestionModel;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    private Long id;
    private String answer;
    private List<QuestionDto> questions;
}
