package com.semillero.ecosistema.dtos.statistics;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticsSupplierByCategoryDto {

    private String category;
    private int quantity;

    public StatisticsSupplierByCategoryDto(String category, int quantity) {
        this.category = category;
        this.quantity = quantity;
    }
}
