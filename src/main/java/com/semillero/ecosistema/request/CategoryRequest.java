package com.semillero.ecosistema.request;

import com.semillero.ecosistema.models.CategoryModel;
import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank(message = "category cannot be null")
        String category
) {
    public CategoryModel ToCategoryModel(){
        return  new CategoryModel(this.category);
    }

}
