package com.jaygibran.deliveryfood.domain.model;

import com.jaygibran.deliveryfood.api.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class State {

    @NotNull(groups = Groups.StateId.class)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;
}
