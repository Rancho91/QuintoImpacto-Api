package com.semillero.ecosistema.controllers;

import com.semillero.ecosistema.models.CategoryModel;
import com.semillero.ecosistema.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping
      public List<CategoryModel> getAllCategory(){
        return this.categoryService.getAllCategories();
    }


}
