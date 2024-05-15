package com.semillero.ecosistema.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.semillero.ecosistema.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="Proveedores")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    @Length(max = 300)
    private String description;

    @Column
    private String phoneNumber;

    @Column
    private String email;

    @Column
    private String facebook;

    @Column
    private String instagram;

    @ManyToOne
    @JoinColumn(name = "pais_id")
    private PaisModel country;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private ProvinciaModel province;

    @Column
    private String city;

    @Column
    private Status status = Status.valueOf("REVISION_INICIAL");

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoryModel category;

    @Column
    private Boolean deleted=false;

    @Column
    private String feedback = "Proveedor en revision";

    @Column
    private String image;

    @OneToMany(mappedBy = "supplier")
    private List<ImageModel> images;

    @Column(name = "short_description")
    @Length(max=50)
    private String shortDescription;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createAt = null;

}
