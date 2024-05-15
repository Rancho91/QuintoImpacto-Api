package com.semillero.ecosistema.request.supplier;

import com.semillero.ecosistema.enums.Status;
import com.semillero.ecosistema.enums.Role;
import com.semillero.ecosistema.models.*;
import com.semillero.ecosistema.request.ImageRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record SupplierRequest(
        @NotBlank(message = "Name cannot be null")
        String name,

        @NotBlank
        @Size(min = 15, max = 300, message = "The character count should be between 16 and 299")
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

        @NotNull(message = "User cannot be null")
        Long user,
        String feedback,
        @Size( max = 50, message = "The character count should not exceed 300")
        String shortDescription,

        @Size(max = 3,min = 1, message = "The number of images should between 1 and 3")
        @Valid
        List<ImageRequest> images


) {
    public SupplierModel ToSupplierModel(){

         SupplierModel supplier = new SupplierModel();
         supplier.setName(this.name);
         supplier.setDescription(this.description);
         supplier.setPhoneNumber(this.phoneNumber);
         supplier.setEmail(this.email);
         supplier.setFacebook(this.facebook);
         supplier.setInstagram(this.instagram);
         supplier.setCity(this.city);
         supplier.setFeedback(this.feedback);
         supplier.setShortDescription(this.shortDescription);

         //Add Category
         CategoryModel categoryModel = new CategoryModel();
         categoryModel.setId(this.category);
         supplier.setCategory(categoryModel);

         //add pais

        PaisModel paisModel = new PaisModel();
        paisModel.setId(this.country);
        supplier.setCountry(paisModel);

        //agrego provincia

        ProvinciaModel provinciaModel = new ProvinciaModel();
        provinciaModel.setId(this.province);
        supplier.setProvince(provinciaModel);

        List<ImageModel> images = new ArrayList<>();

        for (ImageRequest image:this.images){
            images.add(image.toImageModel());
        }
        supplier.setImages(images);
        //agrego user
        UserModel userModel = new UserModel();
        userModel.setId(this.user);
        supplier.setUser(userModel);
        return supplier;
    }



}
