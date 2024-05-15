package com.semillero.ecosistema.request;


import com.semillero.ecosistema.models.ImageModel;
import com.semillero.ecosistema.models.PublicationModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


import java.util.ArrayList;
import java.util.List;


public record PublicationRequest(

        Long id,
        @NotBlank(message = "Title cannot be null")
        String title,
        @NotBlank(message = "Description cannot be null")
        @Size(min = 15, max = 2000, message = "The character count should be between 300 and 1999")
        String description,
        @NotEmpty(message = "At least one image is required")
        @Size(min = 1, max = 3, message = "Exactly three images are required")
        List<ImageRequest> images


) {

        public String getTitle() {
                return this.title;
        }

        public PublicationModel toPubliModel() {
                List<ImageRequest> imageRequests = new ArrayList<>();
                for (ImageRequest imageRequest : this.images) {
                        imageRequests.add(imageRequest); // Agrega la imagen a la lista
                }
                PublicationModel publicationModel = new PublicationModel(
                        this.title,  // Asigna el título
                        this.description,  // Asigna la descripción
                        imageRequests  // Asigna la lista de imágenes
                );
                return publicationModel;

        }
        public List<ImageRequest> getImages() {
                return this.images;
        }

        public String getDescription() {
                return this.description;
        }
}
