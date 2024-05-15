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
public class SupplierSearchAndFilterDto {

    private Long id;

    private String name;

    private String description;

    private PaisDto country;

    private String city;

    private CategoryDto category;

    private String image;

    private ProvinciaDto province;

    private String facebook;

    private String numberPhone;

    private String instagram;

    private String email;

    private String shortDescription;

    private String status;

    private List<ImagePublicDto> images;
}
