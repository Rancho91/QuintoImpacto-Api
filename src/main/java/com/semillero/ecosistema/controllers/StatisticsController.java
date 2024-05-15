package com.semillero.ecosistema.controllers;


import com.semillero.ecosistema.dtos.ErrorResponseDto;
import com.semillero.ecosistema.dtos.statistics.StatisticsSupplierByCategoryDto;
import com.semillero.ecosistema.dtos.statistics.StatisticsSupplierByStatusDto;
import com.semillero.ecosistema.dtos.statistics.StatisticsViewByPublicationDto;
import com.semillero.ecosistema.services.StatisticsService;
import com.semillero.ecosistema.services.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;
    private final SupplierService supplierService;


    public StatisticsController(StatisticsService statisticsService, SupplierService supplierService) {
        this.statisticsService = statisticsService;
        this.supplierService = supplierService;
    }


    @GetMapping("/quantitySupplierByStatus")
    public ResponseEntity<StatisticsSupplierByStatusDto> getSupplierByStatus() {
        try {
            StatisticsSupplierByStatusDto statisticsDto = statisticsService.getStatisticsSupplierByStatus().getBody();
            return ResponseEntity.ok(statisticsDto);
        }catch (Exception e){
            System.err.println("An error occurred while fetching supplier statistics." + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @GetMapping("/quantitySupplierByCategory")
    public ResponseEntity<List<StatisticsSupplierByCategoryDto>> getSupplierByCategory(){
        try{
            List<StatisticsSupplierByCategoryDto> list = statisticsService.getSupplierByCategory();
            return ResponseEntity.ok(list);
        }catch (Exception e){
            System.err.println("An error occurred while fetching supplier statistics." + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/publicationByQuantityViews")
    public ResponseEntity<List<StatisticsViewByPublicationDto>> getPublicationByViews(){
        try {
            List<StatisticsViewByPublicationDto> list = statisticsService.getPublicationByViews();
            return ResponseEntity.ok(list);
        }catch (Exception e){
            System.err.println("An error occurred while fetching publication statistics." + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
