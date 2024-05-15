package com.semillero.ecosistema.request;

import com.semillero.ecosistema.models.ImageModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ImageRequest(

        Long id,

        @NotNull
        Boolean isBase64,

        @NotBlank
        String name,
        @NotBlank
        String path
) {
        public ImageModel toImageModel (){
                ImageModel image = new ImageModel();
                image.setId(id);
                image.setName(this.name);
                image.setPath(this.path);
                return image;

        }
        public Boolean getIsBase64 (){
                return this.isBase64();
        }

        public Boolean getId(){
                if(this.id !=null){
                        return true;
                } else{
                        return false;
                }
        }
}
