package com.jaygibran.deliveryfood.core.security;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {

    @interface Cuisines {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') && hasAuthority('EDIT_CUISINES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowEdit {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') && isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowQuery {
        }
    }

    @interface Restaurants {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') && hasAuthority('EDIT_RESTAURANTS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowManageRegistry {
        }

        @PreAuthorize("(hasAuthority('SCOPE_WRITE') && hasAuthority('EDIT_RESTAURANTS')) || @deliveryFoodSecurity.doesManageRestaurant(#restaurantId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowManageFunctioning {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') && isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowQuery {
        }
    }

    @interface Orders {

        @PreAuthorize("hasAuthority('SCOPE_READ') && isAuthenticated()")
        @PostAuthorize("hasAuthority('QUERY_ORDERS') || " +
                "@deliveryFoodSecurity.getUserId() == returnObject.user.id  || " +
                "@deliveryFoodSecurity.doesManageRestaurant(returnObject.restaurant.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowSearch {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') && isAuthenticated()")
        @PostAuthorize("hasAuthority('QUERY_ORDERS') || " +
                "@deliveryFoodSecurity.getUserId() == returnObject.user.id  || " +
                "@deliveryFoodSecurity.doesManageRestaurant(returnObject.restaurant.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowQuery {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') && isAuthenticated()")
        @PostAuthorize("hasAuthority('MANAGE_ORDERS') || " +
                "@deliveryFoodSecurity.doesManageRestaurantOfOrder(#orderCode)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowManageOrders {
        }
    }

    @interface PaymentMethod {

        @PreAuthorize("hasAuthority('SCOPE_READ') && isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowQuery {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') && isAuthenticated()")
        @PostAuthorize("hasAuthority('EDIT_PAYMENT_METHODS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowEdit {
        }
    }

    @interface City {

        @PreAuthorize("hasAuthority('SCOPE_READ') && isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowQuery {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') && isAuthenticated()")
        @PostAuthorize("hasAuthority('EDIT_CITIES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowEdit {
        }
    }

    @interface State {

        @PreAuthorize("hasAuthority('SCOPE_READ') && isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowQuery {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') && isAuthenticated()")
        @PostAuthorize("hasAuthority('EDIT_STATES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowEdit {
        }
    }

    @interface UsersGroupsPermissions {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
                + "@deliveryFoodSecurity.getUserId() == #id")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowEditOwnPassword {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDIT_USERS_GROUPS_PERMISSIONS') or "
                + "@deliveryFoodSecurity.getUserId() == #id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowEditUser {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_USERS_GROUPS_PERMISSIONS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowEdit {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('QUERY_USERS_GROUPS_PERMISSIONS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowQuery {
        }
    }

    @interface Statistic {

        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('GENERATE_REPORTS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface AllowQuery {
        }
    }
}
