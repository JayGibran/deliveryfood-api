package com.jaygibran.deliveryfood.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal total;

    private String note;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

    public void calculateItemTotal() {
        BigDecimal unitPrice = getUnitPrice();
        Integer quantity = this.getQuantity();

        if (unitPrice == null) {
            unitPrice = BigDecimal.ZERO;
        }

        if (quantity == null) {
            quantity = 0;
        }

        this.setTotal(unitPrice.multiply(new BigDecimal(quantity)));
    }
}
