package com.jaygibran.deliveryfood.api.model;

import com.jaygibran.deliveryfood.domain.model.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Getter
@Setter
public class ProductPhotoDTO {

    private Long id;
    private String name;
    private String description;
    private String contentType;
    private Long size;
}
