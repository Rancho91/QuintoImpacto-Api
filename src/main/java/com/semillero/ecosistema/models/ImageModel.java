package com.semillero.ecosistema.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="imagenes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    @Length(max = 500)
    private String path;

    @Column
    @Length(max = 1000)
    private String public_Id;

    @ManyToOne
    @JoinColumn(name = "id_supplier")
    private SupplierModel supplier;

    @ManyToOne
    @JoinColumn(name = "id_publication")
    private PublicationModel publication;

    @Column
    private Boolean deleted = false;


}
