package com.jaygibran.deliveryfood.api.model;

import com.jaygibran.deliveryfood.domain.model.Address;
import com.jaygibran.deliveryfood.domain.model.OrderItem;
import com.jaygibran.deliveryfood.domain.model.OrderStatus;
import com.jaygibran.deliveryfood.domain.model.PaymentMethod;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.model.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
