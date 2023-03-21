package com.jaygibran.deliveryfood.core.security;

import com.jaygibran.deliveryfood.domain.repository.OrderRepository;
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
    private final OrderRepository orderRepository;

    public Long getUserId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("user_id");
    }

    public boolean doesManageRestaurant(Long restaurantId) {
        if (restaurantId == null) {
            return false;
        }
        return restaurantRepository.existsResponsible(restaurantId, getUserId());
    }

    public boolean doesManageRestaurantOfOrder(String code) {
        return orderRepository.isOrderManagedBy(code, getUserId());
    }

    public boolean authenticatedUserEquals(Long userId) {
        return getUserId() != null && userId != null && getUserId().equals(userId);
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
