package com.jaygibran.deliveryfood.api.v1.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDTO {

    private String code;

    private BigDecimal subTotal;

    private BigDecimal total;

    private BigDecimal feeDelivery;

    private String status;

    private OffsetDateTime dateCreated;

    private OffsetDateTime dateUpdated;

    private OffsetDateTime dateCancelation;

    private OffsetDateTime dateDelivered;

    private RestaurantSummarizedDTO restaurant;

    private UserDTO user;

    private PaymentMethodDTO paymentMethod;

    private AddressDTO address;

    private List<OrderItemDTO> items;
}
