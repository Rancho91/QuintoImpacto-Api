package com.semillero.ecosistema.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter

@Table(name = "pais")
public class PaisModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String name;

    @OneToMany(mappedBy = "pais")
    private List<ProvinciaModel> provincias;
    public PaisModel( String name) {
        this.name = name;
    }
    public PaisModel() {

    }
}
