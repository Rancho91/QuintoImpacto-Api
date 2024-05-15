package com.semillero.ecosistema.repositories;

import com.semillero.ecosistema.enums.Status;
import com.semillero.ecosistema.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SupplierRepository extends JpaRepository <SupplierModel, Long>
 {
     @Query("SELECT s FROM SupplierModel s WHERE (s.name LIKE %:keyword% OR s.shortDescription LIKE %:keyword%) AND s.deleted = false")
     Page<SupplierModel> findAllByNameContainingOrShortDescriptionContainingAndDeletedFalse(String keyword,Pageable pageable);
     boolean existsById(Long id);
     boolean existsByEmail(String email);
     boolean existsByName(String name);

     List<SupplierModel> findAllByUserAndDeletedFalse(UserModel user);

     List<SupplierModel> findByNameAndDeletedFalse(String name);
     List<SupplierModel> findAllByCategoryEqualsAndDeletedFalseAndStatus(CategoryModel Category, Status acepted);

     List<SupplierModel> findAllByDeletedIsFalseAndStatusOrStatus(Status stats1, Status status2);
     Page<SupplierModel> findAllByDeletedFalseAndStatus(Status status, Pageable pageable);
     List<SupplierModel> findAllByDeletedFalseAndStatus(Status status);
     Integer countByUserAndDeletedFalse(UserModel user);

     List<SupplierModel> findSupplierModelByStatus(Status status);


     List<SupplierModel> findByCreateAtAfterAndStatus(LocalDate createAt, Status status);

     List<SupplierModel> findAllByStatusAndDeletedOrderByCreateAtDesc(Status status, boolean deleted);

     Page<SupplierModel> findAllByCategoryEqualsAndDeletedFalseAndStatusOrderByCreateAtDesc(CategoryModel category, Status status,Pageable pageable);

     List<SupplierModel> findByCountryAndProvinceAndCityAndDeletedFalseAndStatus(PaisModel country, ProvinciaModel province, String city, Status status);
     List<SupplierModel> findByCountryAndProvinceAndDeletedFalseAndStatus(PaisModel country, ProvinciaModel province, Status status);

 }
