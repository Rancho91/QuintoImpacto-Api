package com.semillero.ecosistema.controllers;

import com.semillero.ecosistema.dtos.PaisDto;
import com.semillero.ecosistema.services.PaisService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pais")
public class PaisController {

    private final PaisService paisService;

    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }


    @GetMapping("/paises")
    public List<PaisDto> getAllPaises(){
        return paisService.getAll();
    }

    @GetMapping("/{id}")
    public PaisDto getById (@PathVariable("id") Long id){
        return paisService.getById(id);
    }


}




