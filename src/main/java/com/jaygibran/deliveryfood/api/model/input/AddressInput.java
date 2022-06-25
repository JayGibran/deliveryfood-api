package com.jaygibran.deliveryfood.api.model.input;

import com.jaygibran.deliveryfood.api.model.CityResumeDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressInput {

    @NotBlank
    private String airCode;

    @NotBlank
    private String street;

    @NotBlank
    private String number;

    private String additional;

    @NotBlank
    private String neighborhood;

    @Valid
    @NotNull
    private CityIdInput city;
}
