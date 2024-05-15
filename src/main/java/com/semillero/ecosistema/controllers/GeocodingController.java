package com.semillero.ecosistema.controllers;

import com.semillero.ecosistema.dtos.supplier.SupplierLocationNearDto;
import com.semillero.ecosistema.services.GeocodingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class GeocodingController {

    private final GeocodingService geocodingService;

    public GeocodingController(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @GetMapping("/location/{lat}/{lng}/{accept}")
    public ResponseEntity<List<SupplierLocationNearDto>> getSupplierByLocation(@PathVariable Double lat, @PathVariable Double lng, @PathVariable Boolean accept) throws Exception {
        try{
            List<SupplierLocationNearDto> locationNearDtoList = geocodingService.getSupplierNearby(lat, lng, accept);
            if(locationNearDtoList.isEmpty()){
                return ResponseEntity.ok(geocodingService.getSupplierNearby(lat, lng, false));
            }
            return ResponseEntity.ok(locationNearDtoList);
        }catch (Exception e){
            System.err.println("An error occurred while getting nearby suppliers" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }


    }
}
