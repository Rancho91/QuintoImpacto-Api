package com.semillero.ecosistema.dtos.supplier;

import com.semillero.ecosistema.dtos.CategoryDto;
import com.semillero.ecosistema.dtos.image.ImagePrivateDto;
import com.semillero.ecosistema.dtos.PaisDto;
import com.semillero.ecosistema.dtos.ProvinciaDto;
import com.semillero.ecosistema.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDto {

    private Long id;

    private String name;

    private String description;

    private String phoneNumber;
    private String email;

    private String facebook;
    private String instagram;

    private PaisDto country;

    private ProvinciaDto province;

    private String city;

    private CategoryDto category;

    private String feedback;

    private String image;

    private String shortDescription;

    private Status status;


    private List<ImagePrivateDto> images;


}
