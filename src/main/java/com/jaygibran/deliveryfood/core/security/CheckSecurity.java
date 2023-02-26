package com.jaygibran.deliveryfood.core.security;

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
        @interface AllowEdit {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') && isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface AllowQuery {
        }

    }
}
