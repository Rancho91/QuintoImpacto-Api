package com.semillero.ecosistema.services;

import com.semillero.ecosistema.dtos.PaisDto;
import com.semillero.ecosistema.dtos.ProvinciaDto;
import com.semillero.ecosistema.exceptions.ResourceNotFoundException;
import com.semillero.ecosistema.models.PaisModel;
import com.semillero.ecosistema.models.ProvinciaModel;
import com.semillero.ecosistema.repositories.PaisRepository;
import com.semillero.ecosistema.repositories.ProvinciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProvinciaService {
    private final PaisRepository paisRepository;
    private ProvinciaRepository provinciaRepository;

    public ProvinciaService(PaisRepository paisRepository, ProvinciaRepository provinciaRepository) {
        this.paisRepository = paisRepository;
        this.provinciaRepository = provinciaRepository;
    }

    public List<ProvinciaDto> getAll() {
        List<ProvinciaModel> provinciaModels = provinciaRepository.findAll();
        return provinciaModels.stream()
                .map(provinciaModel -> new ProvinciaDto(provinciaModel.getId(), provinciaModel.getNombre(),
                        new PaisDto(provinciaModel.getPais().getId(), provinciaModel.getPais().getName())))
                .collect(Collectors.toList());
    }


    public ProvinciaDto getById(Long id){
        var provincia = this.provinciaRepository.findById(id);
        if (provincia.isEmpty()){
            throw new ResourceNotFoundException("Provincia con id " + id +"no encontrada");
        }
        var provinciaModel =  provincia.get();
        return new ProvinciaDto(provinciaModel.getId(), provinciaModel.getNombre(),new PaisDto(provinciaModel.getPais().getId(), provinciaModel.getPais().getName()));
    }

    public List<ProvinciaDto> getProvinciasByPais(Long idPais) {
        PaisModel pais = paisRepository.findById(idPais)
                .orElseThrow(() -> new ResourceNotFoundException("País no encontrado con ID: " + idPais));

        List<ProvinciaModel> provincias = provinciaRepository.findByPais(pais);
        return provincias.stream()
                .map(provinciaModel -> new ProvinciaDto(provinciaModel.getId(), provinciaModel.getNombre(),
                        new PaisDto(pais.getId(), pais.getName())))
                .collect(Collectors.toList());
    }


}
