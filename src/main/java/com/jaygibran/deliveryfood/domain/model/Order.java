package com.jaygibran.deliveryfood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jaygibran.deliveryfood.domain.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "order_")
public class Order {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    private BigDecimal subTotal;

    private BigDecimal total;

    private BigDecimal feeDelivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;

    @Embedded
    private Address address;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dateCreated;

    private OffsetDateTime dateConfirmation;
    private OffsetDateTime dateCancelation;
    private OffsetDateTime dateDelivered;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderItem> orderItems = new ArrayList<>();

    public void calculateTotal() {
        getOrderItems().forEach(OrderItem::calculateItemTotal);

        this.subTotal = getOrderItems().stream().map(orderItem -> orderItem.getTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.total = this.subTotal.add(this.feeDelivery);
    }

    public void confirm() {
        setStatus(OrderStatus.CONFIRMED);
        setDateConfirmation(OffsetDateTime.now());
    }

    public void deliver() {
        setStatus(OrderStatus.DELIVERED);
        setDateDelivered(OffsetDateTime.now());
    }

    public void cancel() {
        setStatus(OrderStatus.CANCELED);
        setDateCancelation(OffsetDateTime.now());
    }

    private void setStatus(OrderStatus newOrderStatus) {
        if (getStatus().canNotChangeTo(newOrderStatus)) {
            throw new BusinessException(String.format("Status of order %s can't be changed from %s to %s",
                    getCode(), getStatus().getDescription(), newOrderStatus.getDescription()));
        }
        this.status = newOrderStatus;
    }

    @PrePersist
    private void generateCode() {
        setCode(UUID.randomUUID().toString());
    }
}
