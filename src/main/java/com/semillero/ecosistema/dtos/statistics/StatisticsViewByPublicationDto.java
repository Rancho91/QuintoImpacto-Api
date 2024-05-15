package com.semillero.ecosistema.dtos.statistics;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsViewByPublicationDto {

    private String title;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date fechaCreacion;
    private int views;

}
