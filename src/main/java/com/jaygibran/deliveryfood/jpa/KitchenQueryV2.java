package com.jaygibran.deliveryfood.jpa;

import com.jaygibran.deliveryfood.DeliveryfoodApiApplication;
import com.jaygibran.deliveryfood.domain.model.Kitchen;
import com.jaygibran.deliveryfood.domain.repository.KitchenRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class KitchenQueryV2 {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DeliveryfoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        KitchenRepository registryKitchen = applicationContext.getBean(KitchenRepository.class);

        Kitchen kitchen = registryKitchen.findById(1l);
        System.out.println("id: " + kitchen.getId() + " name: " + kitchen.getName());
    }
}
