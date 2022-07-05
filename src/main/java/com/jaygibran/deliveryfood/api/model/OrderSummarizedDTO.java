package com.jaygibran.deliveryfood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class OrderSummarizedDTO {

    private Long id;
    private BigDecimal subTotal;
    private BigDecimal total;
    private BigDecimal feeDelivery;
    private String status;
    private OffsetDateTime dateCreated;
    private RestaurantSummarizedDTO restaurant;
    private UserDTO user;
}
