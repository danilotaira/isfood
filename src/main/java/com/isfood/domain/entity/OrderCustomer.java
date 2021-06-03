package com.isfood.domain.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

import javax.persistence.*;

import com.isfood.domain.enuns.StatusOrder;
import com.isfood.domain.exception.ControllerException;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "order_customer")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderCustomer {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder = StatusOrder.CREATED;

    @Embedded
    @JsonIgnore
    private Address address;

    @Column
    private BigDecimal subtotal;

    @Column
    private BigDecimal taxShipping;

    @Column
    private BigDecimal grandTotal;

    @CreatedDate
    @CreationTimestamp
    @Column( nullable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateConfirmation;
    private OffsetDateTime dateCancellation;
    private OffsetDateTime dateDelivery;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private FormOfPayment formOfPayment;

    @ManyToOne
    @JoinColumn(name = "user_access_id", nullable = false)
    private UserAccess userAccess;

    @OneToMany(mappedBy = "orderCustomer", cascade = CascadeType.ALL)
    private Set<ItemOrder> itens = new HashSet<>();

    public void calculateTotalValue(){
        getItens().forEach(ItemOrder::calculateGrandTotal);

        this.subtotal = getItens().stream()
                            .map(item -> item.getPriceTotal())
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.grandTotal = this.subtotal.add(this.taxShipping);
    }

    public void setShipping(){
        setTaxShipping(getRestaurant().getTaxShipping());
    }

    public void assignOrderToItens(){
        getItens().forEach(item -> item.setOrderCustomer(this));
    }

    private void setStatusOrder(StatusOrder statusOrder){

    }

    public void confirm(){
        setStatusOrder(StatusOrder.CONFIRMED);
        setDateConfirmation(OffsetDateTime.now());
    }

    public void deliver(){
        setStatusOrder(StatusOrder.DELIVERED);
        setDateDelivery(OffsetDateTime.now());
    }

    public void cancel(){
        setStatusOrder(StatusOrder.CANCELED);
        setDateCancellation(OffsetDateTime.now());
    }

    private void setStatus(StatusOrder newStatus){
        if (getStatusOrder().cannotChangeTo(newStatus)) {
            throw new ControllerException(
                String.format("Order status %s cannot be changed from %s to %s.",
                    getUuid(), getStatusOrder().getDescription(), newStatus.getDescription()));
        }
        this.statusOrder = newStatus;
    }

    @PrePersist
    private void generateUuid(){
        setUuid(UUID.randomUUID().toString());
    }
}

