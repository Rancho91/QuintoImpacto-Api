package com.semillero.ecosistema.repositories;

import com.semillero.ecosistema.models.PublicationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PublicationRepository extends JpaRepository<PublicationModel, Long> {

    boolean existsByTitle(String title);
    //List<PublicationModel> findByDeleted(boolean deleted);
    //List<PublicationModel> findByDeletedFalseOrderByQuantityViewsDesc();
    @Query("SELECT p FROM PublicationModel p WHERE p.deleted = :deleted ORDER BY p.fechaCreacion DESC")
    List<PublicationModel> findAllOrderByFechaCreacionDescAndDeleted(boolean deleted);
    Page<PublicationModel> findAllByDeletedOrderByFechaCreacionDesc(boolean deleted, Pageable pageable);
    List<PublicationModel> findByDeletedFalseOrderByQuantityViewsDescFechaCreacionDesc();

}
