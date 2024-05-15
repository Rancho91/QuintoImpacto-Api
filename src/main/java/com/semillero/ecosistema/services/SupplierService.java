package com.semillero.ecosistema.services;

import com.semillero.ecosistema.client.CloudinaryRest;
import com.semillero.ecosistema.dtos.CategoryDto;
import com.semillero.ecosistema.dtos.PaisDto;
import com.semillero.ecosistema.dtos.supplier.SupplierDto;
import com.semillero.ecosistema.dtos.supplier.SupplierEmailDto;
import com.semillero.ecosistema.dtos.supplier.SupplierLocationNearDto;
import com.semillero.ecosistema.dtos.supplier.SupplierSearchAndFilterDto;
import com.semillero.ecosistema.dtos.ProvinciaDto;
import com.semillero.ecosistema.enums.Status;
import com.semillero.ecosistema.exceptions.ResourceNotFoundException;
import com.semillero.ecosistema.models.*;
import com.semillero.ecosistema.repositories.IUserRepository;
import com.semillero.ecosistema.repositories.PaisRepository;
import com.semillero.ecosistema.repositories.ProvinciaRepository;
import com.semillero.ecosistema.repositories.SupplierRepository;
import com.semillero.ecosistema.request.ImageRequest;
import com.semillero.ecosistema.request.PageableRequest;
import com.semillero.ecosistema.request.supplier.SupplierRequest;
import com.semillero.ecosistema.request.supplier.SupplierStatusPatchRequest;
import com.semillero.ecosistema.request.supplier.SupplierUpdateRequest;
import jakarta.transaction.Transactional;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.swing.text.html.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.stream.Collectors;

import static com.semillero.ecosistema.enums.Status.ACEPTADO;

@Service

public class SupplierService {
    @Autowired
    private SupplierRepository repository;
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ImageService iamgeService;

    @Autowired
    private PaisRepository paisRepository;
    @Autowired
    private ProvinciaRepository provinciaRepository;

    //Filtro por contiene get y no esten eliminados;
    public Page<SupplierSearchAndFilterDto> getSearchByName(String name, int size,int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber,size);
        Page<SupplierModel>  suppliersPage = repository.findAllByNameContainingOrShortDescriptionContainingAndDeletedFalse(name, pageable);
        return suppliersPage.map(this::getSupplierSearchAndFilterDto);

    }

    public List<SupplierDto> findAllSupplier(){
        Status status = Status.valueOf("ACEPTADO");
        List<SupplierModel> listSupplier = repository.findAllByDeletedFalseAndStatus(status);

        List<SupplierDto> listSupplierDto = new ArrayList<>();
        for(SupplierModel supplier:listSupplier){
            listSupplierDto.add(getSupplierDto(supplier));
        }

        return listSupplierDto;
    }
    @Transactional
    public SupplierDto createSuppler (SupplierRequest newSupplier){
        SupplierModel validSupplier = newSupplier.ToSupplierModel();
        if(repository.countByUserAndDeletedFalse(validSupplier.getUser())>2){
            throw new ResourceNotFoundException("No puedes tener mas de 3 servicios.");
        }
        if(!userRepository.existsById(validSupplier.getUser().getId())){
            throw new ResourceNotFoundException("Username does not exist");

        }
        List<SupplierModel> validName = repository.findByNameAndDeletedFalse(newSupplier.name());

        if(!validName.isEmpty()) throw new ResourceNotFoundException("This name already exist");


        validSupplier.setStatus(Status.valueOf("REVISION_INICIAL"));
        SupplierModel supplier = repository.save(validSupplier);

        List<ImageModel> listImageCloudinary = new ArrayList<>();
       for (ImageRequest image :newSupplier.images()){

           listImageCloudinary.add(iamgeService.createdOrUpdateImage(image, supplier));
       }
        validSupplier.setImages(listImageCloudinary);

        return  getSupplierDto(supplier);

    }

    @Transactional
    public SupplierDto updateSuppler (SupplierUpdateRequest supplier){
        Optional<SupplierModel> optionalSupplier = this.repository.findById(supplier.id());
        if (optionalSupplier.isEmpty()){
            throw new ResourceNotFoundException("This provider with id "+ supplier.id() +"does not exist");
        }

        SupplierModel supplierModelDB = optionalSupplier.get();
        if(!supplierModelDB.getName().equals(supplier.name())){
            List<SupplierModel> validName = repository.findByNameAndDeletedFalse(supplier.name());
            if(!validName.isEmpty()) throw new ResourceNotFoundException("Este nombre ya existe.");
        }

        SupplierModel supplierModelUpdate = supplier.ToSupplierModel();
        supplierModelUpdate.setFeedback(supplierModelDB.getFeedback());
        supplierModelUpdate.setStatus(supplierModelDB.getStatus());
        supplierModelUpdate.setUser(optionalSupplier.get().getUser());

        List<ImageRequest> imageRequestList = supplier.getImages();
        iamgeService.compareAndDeleteListImageSupplier(imageRequestList,supplierModelUpdate);

        List<ImageModel> listImageCloudinary = new ArrayList<>();
        for(ImageRequest imageRequest:imageRequestList){
            listImageCloudinary.add(iamgeService.createdOrUpdateImage(imageRequest, supplierModelUpdate));
        }
        return this.getSupplierDto(repository.save(supplierModelUpdate));
    }

      public Page<SupplierSearchAndFilterDto> findByCategory(CategoryDto categoryDto, int size, int pageNumber){
        CategoryModel categoryModel = modelMapper.map(categoryDto,CategoryModel.class);
        Status status = Status.valueOf("ACEPTADO");
        Pageable pageable = PageRequest.of(pageNumber,size);
        Page<SupplierModel> listSupplierModelPage = repository.findAllByCategoryEqualsAndDeletedFalseAndStatusOrderByCreateAtDesc(categoryModel, status,pageable);

        return listSupplierModelPage.map(this::getSupplierSearchAndFilterDto);
    }

    public List<SupplierSearchAndFilterDto> findAllStatusAcept(){
        Status status = Status.valueOf("ACEPTADO");
        List<SupplierModel> listSupplierModel = repository.findAllByStatusAndDeletedOrderByCreateAtDesc(status, false);

        return this.listSupplier(listSupplierModel);
    }

    public SupplierDto patchStatus(Long idSupplier, SupplierStatusPatchRequest status){
       Optional<SupplierModel>  supplier = repository.findById(idSupplier);
        if(!supplier.isPresent()) throw new ResourceNotFoundException("this supplier does not exist");
        if((status.status().equals("DENEGADO")||status.status().equals("REQUIERE_CAMBIOS"))&&status.feedback().isEmpty()) {
            throw new ResourceNotFoundException("this supplier does not exist");
        }
        SupplierModel oSupplier =supplier.get();
        oSupplier.setStatus(status.toStatus());
        oSupplier.setFeedback(status.feedback());
        repository.save(oSupplier);
        //agregamos fecha de creacion cuando se acepta al proveedor
        // es para mandar a los emails
        if (status.equals(Status.ACEPTADO)){
            oSupplier.setCreateAt(LocalDate.now());
        }
        return  getSupplierDto(oSupplier);

    }

    public List<SupplierDto> findAllStatusReviewAndChange(){
        Status statusReview = Status.valueOf("CAMBIOS_REALIZADOS");
        Status statusChange = Status.valueOf("CAMBIOS_REALIZADOS");

        List<SupplierModel> supplierModel = repository.findAllByDeletedIsFalseAndStatusOrStatus(statusReview, statusChange);

        return  getSupplierDtos(supplierModel);
    }

    public List<SupplierDto> findAllStatusNew(){
        Status statusReview = Status.valueOf("REVISION_INICIAL");

        List<SupplierModel> supplierModel = repository.findAllByDeletedIsFalseAndStatusOrStatus(statusReview, statusReview);

        return  getSupplierDtos(supplierModel);
    }
    public List<SupplierDto> findAllDeniedSupplier(){
        Status statusDenied = Status.valueOf("DENEGADO");

        List<SupplierModel> supplierModel = repository.findAllByDeletedIsFalseAndStatusOrStatus(statusDenied, statusDenied);

        return  getSupplierDtos(supplierModel);
    }



    private List<SupplierDto> getSupplierDtos(List<SupplierModel> supplierModel) {
        List<SupplierDto> supplierDtosList = new ArrayList<>();

        for(SupplierModel supplier: supplierModel){
            SupplierDto supplierDto = modelMapper.map(supplier, SupplierDto.class);

            if(supplier.getCountry() != null)
                supplierDto.setCountry(modelMapper.map(supplier.getCountry(), PaisDto.class));

            if(supplier.getProvince() != null)
                supplierDto.setProvince(modelMapper.map(supplier.getProvince(), ProvinciaDto.class));

            if(supplier.getCategory() != null)
                supplierDto.setCategory(modelMapper.map(supplier.getCategory(), CategoryDto.class));


            supplierDtosList.add(supplierDto);
        }
        return supplierDtosList;
    }

    public List<SupplierDto> getByUser(Long user){
        Optional<UserModel> userModel = userRepository.findById(user);
        if(userModel.isEmpty()){
            throw new ResourceNotFoundException("this supplier does not exist");
        }

        List<SupplierModel> suppliers = repository.findAllByUserAndDeletedFalse(userModel.get());

        return this.getSupplierDtos(suppliers);
    }

    public SupplierDto getById(Long id){
       Optional<SupplierModel>  supplier = repository.findById(id);
       if(supplier.isEmpty()) {
           throw new ResourceNotFoundException("this user does not exist");
       }

        return this.getSupplierDto(supplier.get());
    }

    //Metodos Pivados

    private List<SupplierSearchAndFilterDto> listSupplier(List<SupplierModel> listSupplierModel){

        List<SupplierSearchAndFilterDto> listSupplierDto = new ArrayList<>();

        for(SupplierModel supplier:listSupplierModel){
            SupplierSearchAndFilterDto supplierDto = modelMapper.map(supplier, SupplierSearchAndFilterDto.class);
            if(supplier.getCountry() != null)
                supplierDto.setCountry(modelMapper.map(supplier.getCountry(), PaisDto.class));

            if(supplier.getProvince() != null)
                supplierDto.setProvince(modelMapper.map(supplier.getProvince(), ProvinciaDto.class));

            if(supplier.getCategory() != null)
                supplierDto.setCategory(modelMapper.map(supplier.getCategory(), CategoryDto.class));

            listSupplierDto.add(supplierDto);
        }
        return listSupplierDto;
    }

    private SupplierDto getSupplierDto(SupplierModel oSupplier) {
        SupplierDto supplierDto = modelMapper.map(oSupplier, SupplierDto.class);
        if(supplierDto.getCountry() != null)
            supplierDto.setCountry(modelMapper.map(oSupplier.getCountry(), PaisDto.class));

        if(supplierDto.getProvince() != null)
            supplierDto.setProvince(modelMapper.map(oSupplier.getProvince(), ProvinciaDto.class));

        if(supplierDto.getCategory() != null)
            supplierDto.setCategory(modelMapper.map(oSupplier.getCategory(), CategoryDto.class));
        return supplierDto;
    }

    private SupplierSearchAndFilterDto getSupplierSearchAndFilterDto(SupplierModel oSupplier) {
        SupplierSearchAndFilterDto supplierDto = modelMapper.map(oSupplier, SupplierSearchAndFilterDto.class);
        if(supplierDto.getCountry() != null)
            supplierDto.setCountry(modelMapper.map(oSupplier.getCountry(), PaisDto.class));

        if(supplierDto.getProvince() != null)
            supplierDto.setProvince(modelMapper.map(oSupplier.getProvince(), ProvinciaDto.class));

        if(supplierDto.getCategory() != null)
            supplierDto.setCategory(modelMapper.map(oSupplier.getCategory(), CategoryDto.class));
        return supplierDto;
    }


    //busca los proveedores aprobados en la ultima semana
    //con localDate para que calcule la fecha

    public List<SupplierEmailDto> getSuppliersLastWeek(){
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        List<SupplierModel> suppliersAddedLastWeek = repository.findByCreateAtAfterAndStatus(oneWeekAgo, Status.ACEPTADO);
        //System.out.println(oneWeekAgo);
        return suppliersAddedLastWeek.stream().map(supplierModel -> modelMapper.map(supplierModel, SupplierEmailDto.class)).collect(Collectors.toList());

    }
    //metodo para geocoding service
    public List<SupplierLocationNearDto> getSuppliersLocation(String pais, String provincia, String city){
        Status status = Status.valueOf("ACEPTADO");

        // Buscar el país por su nombre
        PaisModel country = paisRepository.findByName(pais);
        // Buscar la provincia por su nombre
        ProvinciaModel province = provinciaRepository.findByNombre(provincia);

        List<SupplierModel> listModel = repository.findByCountryAndProvinceAndCityAndDeletedFalseAndStatus(country, province, city, status);
        if (listModel.isEmpty()){
            listModel = repository.findByCountryAndProvinceAndDeletedFalseAndStatus(country,province, status);
        }
        return listModel.stream().map(supplierModel -> modelMapper.map(supplierModel, SupplierLocationNearDto.class)).collect(Collectors.toList());
    }

    public List<SupplierLocationNearDto> getSupplierRandom(){
        Status status = Status.valueOf("ACEPTADO");
        List<SupplierModel> list = repository.findAllByStatusAndDeletedOrderByCreateAtDesc(status, false);
        return list.stream().map(supplierModel -> modelMapper.map(supplierModel, SupplierLocationNearDto.class)).collect(Collectors.toList());
    }
}
