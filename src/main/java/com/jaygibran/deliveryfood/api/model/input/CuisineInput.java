package com.jaygibran.deliveryfood.api.model.input;

import com.jaygibran.deliveryfood.domain.model.Restaurant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CuisineInput {

    @NotBlank
    private String name;
}
