package com.jaygibran.deliveryfood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jaygibran.deliveryfood.domain.model.Restaurant;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class CuisineMixin {

    @JsonIgnore
    private List<Restaurant> restaurants = new ArrayList<>();
}
