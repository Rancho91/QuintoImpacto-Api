package com.semillero.ecosistema.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.semillero.ecosistema.dtos.image.ImagePublicDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PublicationDto {

    private Long id;
    private String title;
    private String description;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date fechaCreacion;
    private UserDto user;
    private List<ImagePublicDto> imagePublicDtoList;
    private int quantityViews;
}
