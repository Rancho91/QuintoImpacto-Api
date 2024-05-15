package com.semillero.ecosistema.services;

import com.semillero.ecosistema.dtos.PublicationDto;
import com.semillero.ecosistema.dtos.image.ImagePublicDto;
import com.semillero.ecosistema.dtos.supplier.SupplierDto;
import com.semillero.ecosistema.exceptions.InvalidDataException;
import com.semillero.ecosistema.exceptions.ResourceNotFoundException;
import com.semillero.ecosistema.models.ImageModel;
import com.semillero.ecosistema.models.PublicationModel;
import com.semillero.ecosistema.models.UserModel;
import com.semillero.ecosistema.repositories.PublicationRepository;
import com.semillero.ecosistema.request.ImageRequest;
import com.semillero.ecosistema.request.PublicationRequest;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.semillero.ecosistema.enums.Role.ADMINISTRADOR;

@Service

public class PublicationService {
    private final PublicationRepository publicationRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    private ImageService image;

    public PublicationService(PublicationRepository publicationRepository, ModelMapper modelMapper, UserService userService) {
        this.publicationRepository = publicationRepository;

        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    //getAll
    public Page<PublicationDto> getAll(int size, int pageNumber){

        Pageable pageable = PageRequest.of(pageNumber,size);
        Page<PublicationModel> publicationModelsPage = publicationRepository.findAllByDeletedOrderByFechaCreacionDesc(false, pageable );

        return publicationModelsPage.map(this::getPubliacionDto);
    }


    //gtById
    public PublicationDto getById(Long id_publication) {
        var publication = this.publicationRepository.findById(id_publication);
        if (publication.isEmpty() || publication.get().isDeleted()) {
            throw new ResourceNotFoundException("Publication with id " + id_publication + " does not exist");
        }
        var publicationModel = publication.get();
        //increaseViews(1, user, publicationModel);
        PublicationDto publicationDto = modelMapper.map(publicationModel, PublicationDto.class);
        List<ImagePublicDto> imagePublicDtoList = new ArrayList<>();

        for(ImageModel imageModel:publicationModel.getImages()){
            imagePublicDtoList.add(modelMapper.map(imageModel, ImagePublicDto.class));
        }
        publicationDto.setImagePublicDtoList(imagePublicDtoList);

        return publicationDto;
    }

    //create
    @Transactional
    public PublicationDto createPublication(PublicationRequest publication, UserModel user) {

        if (publicationRepository.existsByTitle(publication.getTitle())) {
            throw new ResourceNotFoundException("Este titulo ya esta creado.");
        }
        // Verificar si la lista de imágenes está vacía
        if (publication.getImages() == null || publication.getImages().isEmpty()) {
            throw new InvalidDataException("La publicación debe tener al menos una imagen.");
        }

        PublicationModel publicacionModel = modelMapper.map(publication, PublicationModel.class);
        publicacionModel.setUser(user);
        publicacionModel.setDescription(publication.getDescription());

        PublicationModel savedPublication = publicationRepository.save(publicacionModel);
        List<ImageModel> listImageCloudinary = new ArrayList<>();
        // Crear las imágenes asociadas a la publicación
        for (ImageRequest imageRequest : publication.getImages()) {
            listImageCloudinary.add(image.createdOrUpdateImagePublication(imageRequest, savedPublication));
        }
        savedPublication.setImages(listImageCloudinary);
        PublicationDto publicationDto = modelMapper.map(savedPublication, PublicationDto.class);

        publicationDto.setImagePublicDtoList(listImageCloudinary.stream().map(imageModel -> modelMapper.map(imageModel, ImagePublicDto.class)).collect(Collectors.toList()));

        return publicationDto;

    }

    //edit
    @Transactional
    public PublicationModel updatePublication(Long id_publication, PublicationRequest publicationRequest){
        Optional<PublicationModel> optionalPublication = this.publicationRepository.findById(id_publication);
        if (optionalPublication.isEmpty()){
            throw new ResourceNotFoundException("This publication with id "+ id_publication +"does not exist");
        }
        if(!optionalPublication.get().getTitle().equals(publicationRequest.title())){
            if (publicationRepository.existsByTitle(publicationRequest.title())) {
                throw new ResourceNotFoundException("Este titulo ya esta creado.");
            }
        }
        PublicationModel publication = optionalPublication.get();
        publication.setTitle(publicationRequest.title());
        publication.setDescription(publicationRequest.description());

        List<ImageRequest> imageRequestList = publicationRequest.getImages();
        image.compareAndDeleteListImagePublication(imageRequestList,publication);

        List<ImageModel> listImageCloudinary = new ArrayList<>();
        for(ImageRequest imageRequest:imageRequestList){
                listImageCloudinary.add(image.createdOrUpdateImagePublication(imageRequest, publication));
        }
        return publicationRepository.save(publication);
    }

    // delete
    public PublicationModel deletePublication(Long id){
        var publication = this.publicationRepository.findById(id);
        if (publication.isEmpty()){
            throw new ResourceNotFoundException("This publication with id "+ id +"does not exist");
        }
        publication.get().setDeleted(true);
        publication.get().setTitle(null);
        return publicationRepository.save(publication.get());
    }

    //incrementar visualizaciones
    public void increaseViews(Long idUser, Long idPublication ) {
        int numberViews = 1;
        PublicationModel publicationModel = publicationRepository.findById(idPublication).get();

        if(idUser == 0){
            int visual = publicationModel.getQuantityViews() + numberViews;
            publicationModel.setQuantityViews(visual);
            System.out.println(publicationModel.getQuantityViews());
            publicationRepository.save(publicationModel);
            return;
        }
        UserModel user = userService.getById(idUser);
        if (user.getRole().equals(ADMINISTRADOR)){
            publicationRepository.save(publicationModel);
        }else {
            int visual = publicationModel.getQuantityViews() + numberViews;
            publicationModel.setQuantityViews(visual);
            System.out.println(publicationModel.getQuantityViews());
            publicationRepository.save(publicationModel);

        }

    }

    private PublicationDto getPubliacionDto(PublicationModel publicationModel){
        PublicationDto publicationDto = modelMapper.map(publicationModel, PublicationDto.class);
        List<ImagePublicDto> imagePublicDtoList = new ArrayList<>();
        for (ImageModel imageModel:publicationModel.getImages()){
            imagePublicDtoList.add(modelMapper.map(imageModel,ImagePublicDto.class));
        }
        publicationDto.setImagePublicDtoList(imagePublicDtoList);

    return publicationDto;
    }









}
