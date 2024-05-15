package com.semillero.ecosistema.services;

import com.semillero.ecosistema.client.CloudinaryRest;
import com.semillero.ecosistema.exceptions.ResourceNotFoundException;
import com.semillero.ecosistema.models.ImageModel;
import com.semillero.ecosistema.models.PublicationModel;
import com.semillero.ecosistema.models.SupplierModel;
import com.semillero.ecosistema.repositories.ImageRepository;
import com.semillero.ecosistema.request.ImageRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ImageService {
    @Autowired
     ImageRepository repository;

    @Autowired
     CloudinaryRest cloudinaryRest ;
    public ImageModel createdOrUpdateImage(ImageRequest createImage, SupplierModel supplierModel){
        try{

            ImageModel imageModel = createImage.toImageModel();
            imageModel.setSupplier(supplierModel);

            if(createImage.isBase64()){
                Map<String, String> cloudinaryResult =cloudinaryRest.postImageCloudinary(imageModel.getPath());
                imageModel.setPath(cloudinaryResult.get("url"));
                imageModel.setPublic_Id(cloudinaryResult.get("public_id"));
            }
           return repository.save(imageModel);
        } catch (Exception e){
            throw new ResourceNotFoundException("Could not convert imagen");
        }
    }
    public ImageModel createdOrUpdateImagePublication(ImageRequest createImage, PublicationModel publication){
        try{
            ImageModel imageModel = createImage.toImageModel();

            if(createImage.id()!=null){
                imageModel = repository.getReferenceById(createImage.id());
            }
            imageModel.setPublication(publication);
            if(createImage.isBase64()){
                Map<String, String> cloudinaryResult =cloudinaryRest.postImageCloudinary(createImage.path());
                imageModel.setPath(cloudinaryResult.get("url"));
                imageModel.setPublic_Id(cloudinaryResult.get("public_id"));
            }

            return repository.save(imageModel);

        } catch (Exception e){
            throw new ResourceNotFoundException("Could not convert imagen");
        }
    }
    public String deletedImage(Long idImage){
        if(!repository.existsById(idImage)){
            throw  new ResourceNotFoundException("This image does not exist");
        }
        ImageModel findImage = repository.getReferenceById(idImage);
        findImage.setSupplier(null);
        findImage.setPublication(null);
        findImage.setDeleted(true);
        repository.save(findImage);
        return "The image was successfully deleted";

    }

    public String imageDestroy(Long ImageId){
        try{
            if(!repository.existsById(ImageId)){
                throw  new ResourceNotFoundException("This image does not exist");
            }
            ImageModel findImage = repository.getReferenceById(ImageId);
            findImage.setSupplier(null);
            findImage.setPublication(null);
            findImage.setDeleted(true);
            cloudinaryRest.destroyImage(findImage.getPublic_Id());
            repository.save(findImage);
            return "The image was successfully deleted";

        }catch (Exception e){
            throw  new ResourceNotFoundException("This image does not exist");

        }
    }

    public List<ImageModel> findByPublication (PublicationModel publication){
        return repository.findByPublication(publication);
    }
    public List<ImageModel> findBySupplier (SupplierModel supplier){
        return repository.findBySupplier(supplier);
    }

    public void compareAndDeleteListImagePublication (List<ImageRequest> listImage, PublicationModel publicationModel ){
        List<ImageModel> imageModelList = this.findByPublication(publicationModel);
        if(listImage.stream()
                .allMatch(image -> image.id() == null)){
            for(ImageModel imageModel:imageModelList){
                this.deletedImage(imageModel.getId());
            }
            } else{

            for(ImageModel imageModel:imageModelList){
                try{
                    Long imageModelId = imageModel.getId();
                    if(imageModelId != null){
                        boolean exists = listImage.stream()
                                .anyMatch(image -> {
                                    Long listImageId = image.id();  // Obtén el ID de la imagen de la lista
                                    return listImageId != null && listImageId.equals(imageModelId);  // Verifica si los ID son iguales
                                });
                        if(!exists){
                            this.deletedImage(imageModel.getId());

                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void compareAndDeleteListImageSupplier (List<ImageRequest> listImage, SupplierModel supplierModel ){
        List<ImageModel> imageModelList = this.findBySupplier(supplierModel);
        if(listImage.stream()
                .allMatch(image -> image.id() == null)){
            for(ImageModel imageModel:imageModelList){
                this.deletedImage(imageModel.getId());
            }
        } else{

            for(ImageModel imageModel:imageModelList){
                try{
                Long imageModelId = imageModel.getId();
                if(imageModelId != null){
                    boolean exists = listImage.stream()
                            .anyMatch(image -> {
                                Long listImageId = image.id();  // Obtén el ID de la imagen de la lista
                                return listImageId != null && listImageId.equals(imageModelId);  // Verifica si los ID son iguales
                            });
                if(!exists){
                    this.deletedImage(imageModel.getId());

                }
                }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
