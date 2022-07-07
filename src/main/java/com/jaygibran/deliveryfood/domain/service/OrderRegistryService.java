package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.BusinessException;
import com.jaygibran.deliveryfood.domain.exception.OrderNotFoundException;
import com.jaygibran.deliveryfood.domain.model.City;
import com.jaygibran.deliveryfood.domain.model.Order;
import com.jaygibran.deliveryfood.domain.model.OrderItem;
import com.jaygibran.deliveryfood.domain.model.OrderStatus;
import com.jaygibran.deliveryfood.domain.model.PaymentMethod;
import com.jaygibran.deliveryfood.domain.model.Product;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.model.User;
import com.jaygibran.deliveryfood.domain.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderRegistryService {

    private final OrderRepository orderRepository;
    private final RestaurantRegistryService restaurantRegistryService;
    private final PaymentMethodRegistryService paymentMethodRegistryService;
    private final CityRegistryService cityRegistryService;
    private final ProductRegistryService productRegistryService;
    private final UserRegistryService userRegistryService;

    public Order findOrFail(String orderCode) {
        return orderRepository
                .findByCode(orderCode)
                .orElseThrow(() -> new OrderNotFoundException(orderCode));
    }

    @Transactional
    public Order placeOrder(Order order) {

        validateOrder(order);

        validateItems(order);

        order.setFeeDelivery(order.getRestaurant().getFeeDelivery());
        order.calculateTotal();

        return orderRepository.save(order);
    }

    private void validateItems(Order order) {

        order.getOrderItems().forEach(item -> {
            Product product = productRegistryService.findOrFail(item.getProduct().getId(), order.getRestaurant().getId());
            item.setOrder(order);
            item.setProduct(product);
            item.setUnitPrice(product.getPrice());
        });
    }

    private void validateOrder(Order order) {
        Restaurant restaurant = restaurantRegistryService.findOrFail(order.getRestaurant().getId());

        PaymentMethod paymentMethod = paymentMethodRegistryService.findOrFail(order.getPaymentMethod().getId());

        City city = cityRegistryService.findOrFail(order.getAddress().getCity().getId());

        User user = userRegistryService.findOrFail(order.getUser().getId());

        order.setRestaurant(restaurant);
        order.setPaymentMethod(paymentMethod);
        order.getAddress().setCity(city);
        order.setUser(user);

        if (restaurant.doesNotAcceptPaymentMethod(paymentMethod)) {
            throw new BusinessException(String.format("Restaurant doesn't not accept payment method: %s", paymentMethod.getDescription()));
        }
    }
}
