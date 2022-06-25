package com.jaygibran.deliveryfood.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityResumeDTO {

    private Long id;
    private String name;
    private String state;
}
