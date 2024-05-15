package com.semillero.ecosistema.repositories;

import com.semillero.ecosistema.models.PaisModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<PaisModel, Long> {

    PaisModel findByName(String name);
}
