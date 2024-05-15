package com.semillero.ecosistema.controllers;

import com.semillero.ecosistema.dtos.ProvinciaDto;
import com.semillero.ecosistema.services.PaisService;
import com.semillero.ecosistema.services.ProvinciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/provincias")
public class ProvinciaController {
    private final ProvinciaService provinciaService;

    @GetMapping("/all")
    public List<ProvinciaDto> getAllProvincias(){
        return provinciaService.getAll();
    }

    @GetMapping("/{id}")
    public ProvinciaDto getById(@PathVariable Long id){
        return provinciaService.getById(id);
    }
    @GetMapping("/provinciasByIdPais/{id}")
    public ResponseEntity<List<ProvinciaDto>> getProvinciasByPais(@PathVariable("id") Long id) {
        List<ProvinciaDto> provincias = provinciaService.getProvinciasByPais(id);
        return ResponseEntity.ok().body(provincias);
    }

    public ProvinciaController(PaisService paisService, ProvinciaService provinciaService) {
        this.provinciaService = provinciaService;
    }


}
