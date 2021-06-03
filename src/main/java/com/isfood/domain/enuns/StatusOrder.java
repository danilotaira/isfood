package com.isfood.domain.enuns;

import java.util.Arrays;
import java.util.List;

public enum StatusOrder {
    CREATED("Criado"),
    CONFIRMED("Confirmado", CREATED),
    DELIVERED("Entregue", CONFIRMED),
    CANCELED("Cancelado", CREATED, CONFIRMED);

    private String description;
    private List<StatusOrder> statusBefore;

    StatusOrder(String description, StatusOrder... statusOrders){
        this.description = description;
        this.statusBefore = Arrays.asList(statusOrders);
    }

    public String getDescription(){
        return this.description;
    }

    public boolean cannotChangeTo(StatusOrder newStatus){
        return !newStatus.statusBefore.contains(this);
    }
}

