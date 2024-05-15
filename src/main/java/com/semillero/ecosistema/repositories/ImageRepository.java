package com.semillero.ecosistema.repositories;

import com.semillero.ecosistema.models.ImageModel;
import com.semillero.ecosistema.models.PublicationModel;
import com.semillero.ecosistema.models.SupplierModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel,Long> {
    List<ImageModel> findByPublication(PublicationModel publicationModel);
    List<ImageModel> findBySupplier(SupplierModel supplier);

}
