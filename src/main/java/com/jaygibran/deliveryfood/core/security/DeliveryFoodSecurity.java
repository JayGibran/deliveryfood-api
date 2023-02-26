package com.jaygibran.deliveryfood.core.security;

import com.jaygibran.deliveryfood.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeliveryFoodSecurity {

    private final RestaurantRepository restaurantRepository;

    public Long getUserId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("user_id");
    }

    public boolean doesManageRestaurant(Long restaurantId) {
        return restaurantRepository.existsResponsible(restaurantId, getUserId());
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
