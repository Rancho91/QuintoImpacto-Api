package com.semillero.ecosistema.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.semillero.ecosistema.dtos.PublicationDto;
import com.semillero.ecosistema.request.ImageRequest;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "publication")
public class PublicationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Id, Título, Descripción, Deleted, Fecha de Creación, Imágenes, Usuario Creador, Cantidad de Visualizaciones
    private String title;
    @Column(length = 2500)
    private String description;
    private boolean deleted = false;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date fechaCreacion = new Date();


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @OneToMany(mappedBy = "publication")
    private List<ImageModel> images;
    private int quantityViews = 0;



    public PublicationModel(String title, String description, List<ImageRequest> imageRequests) {
        this.title = title;
        this.description = description;
        final ModelMapper modelMapper = new ModelMapper();
        this.images = imageRequests.stream()
                .map(imageRequest -> modelMapper.map(imageRequest, ImageModel.class))
                .collect(Collectors.toList());

    }



    public void setUsuarioCreador(UserModel usuarioCreador) {
        this.user = usuarioCreador;
    }
}
