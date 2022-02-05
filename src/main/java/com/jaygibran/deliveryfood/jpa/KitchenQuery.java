package com.jaygibran.deliveryfood.jpa;

import com.jaygibran.deliveryfood.DeliveryfoodApiApplication;
import com.jaygibran.deliveryfood.domain.model.Kitchen;
import com.jaygibran.deliveryfood.domain.repository.KitchenRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class KitchenQuery {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DeliveryfoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        KitchenRepository registryKitchen = applicationContext.getBean(KitchenRepository.class);

        Kitchen kitchen1 = new Kitchen();
        kitchen1.setName("Brazilian");

        Kitchen kitchen2 = new Kitchen();
        kitchen2.setName("Japanese");

        registryKitchen.save(kitchen1);
        registryKitchen.save(kitchen2);

        List<Kitchen> kitchens = registryKitchen.all();
        kitchens.forEach(kitchen -> System.out.println("id: " + kitchen.getId() + " name: " + kitchen.getName()));
    }
}
