package com.semillero.ecosistema.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProvinciaDto {
    private Long id;
    private String nombre;
    private PaisDto pais;

    public ProvinciaDto(Long id, String nombre, PaisDto paisDto) {
        this.id = id;
        this.nombre = nombre;
        this.pais = paisDto;
    }
}
