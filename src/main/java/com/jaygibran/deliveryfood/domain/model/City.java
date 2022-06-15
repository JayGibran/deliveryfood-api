package com.jaygibran.deliveryfood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jaygibran.deliveryfood.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class City {

    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;
    
    @Valid
    @NotNull
    @ConvertGroup(to = Groups.StateId.class)
    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;
}
