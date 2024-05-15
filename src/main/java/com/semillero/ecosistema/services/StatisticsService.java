package com.semillero.ecosistema.services;


import com.semillero.ecosistema.dtos.statistics.StatisticsSupplierByCategoryDto;
import com.semillero.ecosistema.dtos.statistics.StatisticsSupplierByStatusDto;
import com.semillero.ecosistema.dtos.statistics.StatisticsViewByPublicationDto;
import com.semillero.ecosistema.enums.Status;
import com.semillero.ecosistema.models.CategoryModel;
import com.semillero.ecosistema.models.PublicationModel;
import com.semillero.ecosistema.models.SupplierModel;
import com.semillero.ecosistema.repositories.PublicationRepository;
import com.semillero.ecosistema.repositories.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StatisticsService {

    private SupplierRepository supplierRepository;
    private final SupplierService supplierService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final PublicationRepository publicationRepository;


    public StatisticsService(SupplierService supplierService, SupplierRepository supplierRepository, CategoryService categoryService, ModelMapper modelMapper, PublicationRepository publicationRepository) {
        this.supplierService = supplierService;
        this.supplierRepository = supplierRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;

        this.publicationRepository = publicationRepository;
    }



    public ResponseEntity<StatisticsSupplierByStatusDto> getStatisticsSupplierByStatus(){
        StatisticsSupplierByStatusDto statisticsDto = new StatisticsSupplierByStatusDto();
        // Obtener el número de proveedores por cada estado
        statisticsDto.setNewSuppliers(supplierRepository.findAll().size());
        statisticsDto.setAccepted(getSupplierByStatus(Status.valueOf("ACEPTADO")));

        statisticsDto.setReview(getSupplierByStatus(Status.valueOf("REVISION_INICIAL")) + getSupplierByStatus(Status.valueOf("REQUIERE_CAMBIOS")) + getSupplierByStatus(Status.valueOf("CAMBIOS_REALIZADOS")));
        statisticsDto.setDenied(getSupplierByStatus(Status.valueOf("DENEGADO")));
        return ResponseEntity.ok(statisticsDto);
    }

    //Obtener lista de cantidad de proveedores por categoria
    public List<StatisticsSupplierByCategoryDto> getSupplierByCategory() {
        List<StatisticsSupplierByCategoryDto> supplierByCategoryList = new ArrayList<>();

        // Obtengo la lista de categorías disponibles
        List<CategoryModel> categoriesModel = categoryService.getAllCategories();

        // Para cada categoría, obtener la cantidad de proveedores
        for (CategoryModel category : categoriesModel) {
            int quantity = getSupplierByCategory(category);
            supplierByCategoryList.add(new StatisticsSupplierByCategoryDto(category.getCategory() , quantity));
        }

        return supplierByCategoryList;
    }


    //Obtener las publicaciones con mayor visualizaciones
    public List<StatisticsViewByPublicationDto> getPublicationByViews(){
        List<PublicationModel> publicationModelList = publicationRepository.findByDeletedFalseOrderByQuantityViewsDescFechaCreacionDesc();
        List<StatisticsViewByPublicationDto> listStatisticsDto = new ArrayList<>();
        for(PublicationModel publicationModel : publicationModelList){
            listStatisticsDto.add(modelMapper.map(publicationModel, StatisticsViewByPublicationDto.class));
        }

        return listStatisticsDto;
    }

    //Obtener la cantidad de proveedores dependiendo la cantidad
    public int getSupplierByStatus(Status status){
        List<SupplierModel> listSupplier = supplierRepository.findSupplierModelByStatus(status);
        return listSupplier.size();
    }

    //Obtener la cantidad de proveedores por categoria y que esten en estado de aceptado
    public int getSupplierByCategory(CategoryModel category){
        Status status = Status.valueOf("ACEPTADO");
        List<SupplierModel> list = supplierRepository.findAllByCategoryEqualsAndDeletedFalseAndStatus(category, status);
        return list.size();
    }

}
