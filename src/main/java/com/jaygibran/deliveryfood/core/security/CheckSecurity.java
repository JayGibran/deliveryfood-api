package com.jaygibran.deliveryfood.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    @interface Cuisines {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') && hasAuthority('EDIT_CUISINES')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface AllowEdit {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') && isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface AllowQuery {
        }
    }

    @interface Restaurants {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') && hasAuthority('EDIT_RESTAURANTS')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface AllowManageRegistry {
        }

        @PreAuthorize("(hasAuthority('SCOPE_WRITE') && hasAuthority('EDIT_RESTAURANTS')) || @deliveryFoodSecurity.doesManageRestaurant(#restaurantId)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface AllowManageFunctioning {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') && isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface AllowQuery {
        }
    }

    @interface Orders {

        @PreAuthorize("hasAuthority('SCOPE_READ') && isAuthenticated()")
        @PostAuthorize("hasAuthority('QUERY_ORDERS') || " +
                "@deliveryFoodSecurity.getUserId() == returnObject.user.id  || " +
                "@deliveryFoodSecurity.doesManageRestaurant(returnObject.restaurant.id)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface AllowSearch {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') && isAuthenticated()")
        @PostAuthorize("hasAuthority('QUERY_ORDERS') || " +
                "@deliveryFoodSecurity.getUserId() == returnObject.user.id  || " +
                "@deliveryFoodSecurity.doesManageRestaurant(returnObject.restaurant.id)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface AllowQuery {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') && isAuthenticated()")
        @PostAuthorize("hasAuthority('MANAGE_ORDERS') || " +
                "@deliveryFoodSecurity.doesManageRestaurantOfOrder(#orderCode)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface AllowManageOrders {
        }
    }

    @interface PaymentMethod {

        @PreAuthorize("hasAuthority('SCOPE_READ') && isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface AllowQuery {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') && isAuthenticated()")
        @PostAuthorize("hasAuthority('EDIT_PAYMENT_METHODS')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface AllowEdit {
        }
    }
}
