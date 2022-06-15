package com.jaygibran.deliveryfood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jaygibran.deliveryfood.core.validation.Groups;
import com.jaygibran.deliveryfood.domain.model.State;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;

public class CityMixin {

    @JsonIgnoreProperties(value = "name", allowGetters = true)
    private State state;
}
