package com.semillero.ecosistema.repositories;

import com.semillero.ecosistema.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriesRepository extends JpaRepository<CategoryModel, Long> {
}
