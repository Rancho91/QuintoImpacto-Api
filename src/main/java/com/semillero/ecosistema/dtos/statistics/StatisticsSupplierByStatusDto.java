package com.semillero.ecosistema.dtos.statistics;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StatisticsSupplierByStatusDto {

    private int newSuppliers;

    private int accepted;

    private int review;

    private int denied;

}
