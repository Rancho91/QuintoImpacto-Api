package com.semillero.ecosistema.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "provincia")
public class ProvinciaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "pais_id")
    private PaisModel pais;

    public ProvinciaModel(String nombre, PaisModel pais) {
        this.nombre = nombre;
        this.pais = pais;
    }

    public ProvinciaModel() {

    }
}
