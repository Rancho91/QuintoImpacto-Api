package com.semillero.ecosistema.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaisDto {

    private Long id;
    private String name;

    public PaisDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
