package com.isfood.api.model.mixin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isfood.domain.entity.Address;
import com.isfood.domain.entity.FormOfPayment;
import com.isfood.domain.entity.Kitchen;
import com.isfood.domain.entity.Product;

public class RestaurantMixin {
  @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "name"}, allowGetters = true)
  private Kitchen kitchen;

  @JsonIgnore
  private List<FormOfPayment> formOfPayments = new ArrayList<>();

  @JsonIgnore
  private Address address;

  @JsonIgnore
  private LocalDateTime dateCreated;

  @JsonIgnore
  private LocalDateTime dateModified;

  @JsonIgnore
  private List<Product> products = new ArrayList<>();
}
