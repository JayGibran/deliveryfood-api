package com.jaygibran.deliveryfood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class OrderMixin {

    @JsonIgnore
    private LocalDateTime dateDelivered;
}
