package com.semillero.ecosistema.request.supplier;

import com.semillero.ecosistema.enums.Role;
import com.semillero.ecosistema.enums.Status;
import com.semillero.ecosistema.models.CategoryModel;
import com.semillero.ecosistema.models.PaisModel;
import com.semillero.ecosistema.models.ProvinciaModel;
import com.semillero.ecosistema.models.SupplierModel;
import com.semillero.ecosistema.request.ImageRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record SupplierUpdateRequest(
        @NotNull
        Long id,
        @NotBlank(message = "Name cannot be null")
        String name,

        @NotBlank
        @Size(min = 15, max = 2500, message = "The character count should be between 16 and 2499")
        String description,
        @NotBlank (message = "Phone Number cannot be null")
        String phoneNumber,
        @Email
        @NotBlank(message = "Email cannot be null")
        String email,
        String facebook,
        String instagram,
        @NotNull(message = "Country cannot be null")
        Long country,
        @NotNull(message = "Province cannot be null")
        Long province,
        @NotBlank(message = "City cannot be null")
        String city,
        @NotNull(message = "Category cannot be null")
        Long category,

        String shortDescription,

        @Size(max = 3,min = 1, message = "The number of images should between 1 and 3")
        @Valid
        List<ImageRequest> images
) {
   public SupplierModel ToSupplierModel(){

        SupplierModel supplier = new SupplierModel();
        supplier.setId(this.id);
        supplier.setName(this.name);
        supplier.setDescription(this.description);
        supplier.setPhoneNumber(this.phoneNumber);
        supplier.setEmail(this.email);
        supplier.setFacebook(this.facebook);
        supplier.setInstagram(this.instagram);
        supplier.setCity(this.city);
        supplier.setShortDescription(this.shortDescription);

        //Add Category
       if(this.category!=null){
           CategoryModel categoryModel = new CategoryModel();
           categoryModel.setId(this.category);
           supplier.setCategory(categoryModel);

       }

        //add pais
    if(this.country != null){
        PaisModel paisModel = new PaisModel();
        paisModel.setId(this.country);
        supplier.setCountry(paisModel);
    }


        //agrego provincia
       if(this.province !=null){
           ProvinciaModel provinciaModel = new ProvinciaModel();
           provinciaModel.setId(this.province);
           supplier.setProvince(provinciaModel);
       }


       return supplier;

    }

    public List<ImageRequest> getImages() {
        return this.images;
    }

}
