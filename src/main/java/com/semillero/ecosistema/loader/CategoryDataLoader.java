package com.semillero.ecosistema.loader;

import com.semillero.ecosistema.client.CloudinaryRest;
import com.semillero.ecosistema.models.CategoryModel;
import com.semillero.ecosistema.repositories.ICategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class CategoryDataLoader implements CommandLineRunner{
    @Autowired
    ICategoriesRepository categoryRepository;
    //probando configuracion nueva de github
    @Override
    public void run(String... args) {
        loadUserData();
    }
    private void loadUserData() {
        if (categoryRepository.count() == 0) {
            List<CategoryModel> listCategories = new ArrayList<>();
            listCategories.add(new CategoryModel(1L,"Bienestar", "https://res.cloudinary.com/dpbuvii9v/image/upload/v1710858373/Ecosistema-semillero/Categorias/bienestar_onzpey.png"));
            listCategories.add(new CategoryModel(2L,"Capacitaciones","https://res.cloudinary.com/dpbuvii9v/image/upload/v1710858372/Ecosistema-semillero/Categorias/capacitaciones_rg8b9c.png"));
            listCategories.add(new CategoryModel(3L,"Construccion","https://res.cloudinary.com/dpbuvii9v/image/upload/v1710858372/Ecosistema-semillero/Categorias/construccion_m5unnp.png"));
            listCategories.add(new CategoryModel(4L,"Cultivos","https://res.cloudinary.com/dpbuvii9v/image/upload/v1710858370/Ecosistema-semillero/Categorias/cultivos_v129lk.png"));
            listCategories.add(new CategoryModel(5L, "Gastronomia","https://res.cloudinary.com/dpbuvii9v/image/upload/v1710858369/Ecosistema-semillero/Categorias/gastronomia_h6snfx.png"));
            listCategories.add(new CategoryModel( 6L,"Indumentaria","https://res.cloudinary.com/dpbuvii9v/image/upload/v1710858368/Ecosistema-semillero/Categorias/indumentaria_pmgayz.png"));
            listCategories.add(new CategoryModel(7L,"Merchandasing","https://res.cloudinary.com/dpbuvii9v/image/upload/v1710858367/Ecosistema-semillero/Categorias/merchandising_fu21uf.png"));
            listCategories.add(new CategoryModel(8L, "Reciclaje","https://res.cloudinary.com/dpbuvii9v/image/upload/v1711115269/Ecosistema-semillero/Categorias/reciclaje_lrmhfs.png"));
            listCategories.add(new CategoryModel(9L,"Tecnología","https://res.cloudinary.com/dpbuvii9v/image/upload/v1710858366/Ecosistema-semillero/Categorias/tecnologia_b76d4i.png"));
            listCategories.add(new CategoryModel(10L,"Transporte","https://res.cloudinary.com/dpbuvii9v/image/upload/v1710858366/Ecosistema-semillero/Categorias/transporte_hmwfln.png"));
            listCategories.add(new CategoryModel(11L,"Muebles/Deco","https://res.cloudinary.com/dpbuvii9v/image/upload/v1710858366/Ecosistema-semillero/Categorias/muebles-deco_ijwjyz.png"));

            categoryRepository.saveAll(listCategories);
        }
    }
}
