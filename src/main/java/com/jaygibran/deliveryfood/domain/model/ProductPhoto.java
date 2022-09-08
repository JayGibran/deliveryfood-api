package com.jaygibran.deliveryfood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class ProductPhoto {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "product_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Product product;

    private String name;
    private String description;
    private String contentType;
    private Long size;

    public Long getRestaurantId() {
        if (getProduct() == null) {
            return null;
        }
        return product.getRestaurant().getId();
    }
}
