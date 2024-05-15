package com.semillero.ecosistema.dtos.supplier;

import com.semillero.ecosistema.dtos.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierEmailDto {

    private String name;

    private String city;

    private CategoryDto category;
    private String shortDescription;
}
