package com.semillero.ecosistema.services;

import com.semillero.ecosistema.models.CategoryModel;
import com.semillero.ecosistema.repositories.ICategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private ICategoriesRepository categoryRepository  ;
    public List<CategoryModel> getAllCategories (){
        return categoryRepository.findAll();
    }



}
