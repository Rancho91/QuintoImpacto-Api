package com.semillero.ecosistema.dtos.supplier;

import com.semillero.ecosistema.dtos.CategoryDto;
import com.semillero.ecosistema.dtos.PaisDto;
import com.semillero.ecosistema.dtos.ProvinciaDto;
import com.semillero.ecosistema.dtos.image.ImagePublicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierLocationNearDto {
    private Long id;

    private String name;

    private String description;

    private String numberPhone;
    private String email;
    private String facebook;
    private String instagram;
    private PaisDto country;

    private ProvinciaDto province;

    private String city;

    private CategoryDto category;

    private String image;

    private List<ImagePublicDto> images;


    private String shortDescription;
}
