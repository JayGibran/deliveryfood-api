package com.jaygibran.deliveryfood.api.model;

import com.jaygibran.deliveryfood.domain.model.City;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class AddressDTO {

    private String airCode;

    private String street;

    private String number;

    private String additional;

    private String neighborhood;

    private CityResumeDTO city;
}
