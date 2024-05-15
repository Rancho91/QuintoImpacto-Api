package com.semillero.ecosistema.services;

import com.semillero.ecosistema.dtos.PaisDto;
import com.semillero.ecosistema.exceptions.ResourceNotFoundException;
import com.semillero.ecosistema.models.PaisModel;
import com.semillero.ecosistema.repositories.PaisRepository;
import com.semillero.ecosistema.repositories.ProvinciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class PaisService {
    private final PaisRepository paisRepository;
    public PaisService(PaisRepository paisRepository, ProvinciaRepository provinciaRepository) {
        this.paisRepository = paisRepository;

    }


    public List<PaisDto> getAll() {
        List<PaisModel> paisModels = paisRepository.findAll();
        return paisModels.stream()
                .map(paisModel -> new PaisDto(paisModel.getId(), paisModel.getName()))
                .collect(Collectors.toList());
    }
    public PaisDto getById (Long id){
        var pais = this.paisRepository.findById(id);
        if(pais.isEmpty()){
            throw new ResourceNotFoundException("Pais no encontrado");
        }
        var paisModel = pais.get();
        return new PaisDto(paisModel.getId(),paisModel.getName());
    }



}
