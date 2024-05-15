package com.semillero.ecosistema.dtos.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagePublicDto {

    private Long id;

    private String name;

    private String path;
}
