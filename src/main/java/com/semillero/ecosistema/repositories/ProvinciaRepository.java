package com.semillero.ecosistema.repositories;

import com.semillero.ecosistema.models.PaisModel;
import com.semillero.ecosistema.models.ProvinciaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProvinciaRepository extends JpaRepository<ProvinciaModel, Long> {
    List<ProvinciaModel> findByPais(PaisModel pais);
    ProvinciaModel findByNombre(String nombre);
}
