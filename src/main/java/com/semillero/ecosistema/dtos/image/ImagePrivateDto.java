package com.semillero.ecosistema.dtos.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagePrivateDto {

    private Long id;
    private String name;
    private String path;


}
