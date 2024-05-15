package com.semillero.ecosistema.controllers;

import com.semillero.ecosistema.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService service;

    @DeleteMapping("/deleted/{id}")
    public String deletedImage(@PathVariable("id") Long id){
        System.out.println("entre a la ruta");
       return service.imageDestroy(id);

    }
}
