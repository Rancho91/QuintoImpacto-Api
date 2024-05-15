package com.semillero.ecosistema.request;

import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public record PageableRequest(
        Integer size,

        Integer sort

) {


    public Optional<Pageable> toPageable (){
        if (this.size != null && this.sort != null){
            Pageable pageable = (Pageable) PageRequest.of(sort,size);
            return Optional.of(pageable);
        }

        return Optional.empty();
    }
}
