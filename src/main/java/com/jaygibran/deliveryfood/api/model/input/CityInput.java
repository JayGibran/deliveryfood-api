package com.jaygibran.deliveryfood.api.model.input;

import com.jaygibran.deliveryfood.core.validation.Groups;
import com.jaygibran.deliveryfood.domain.model.State;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;

@Getter
@Setter
public class CityInput {

    @NotBlank
    private String name;

    @Valid
    @NotNull
    private StateIdInput state;
}
