package com.jaygibran.deliveryfood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableWebMvc
@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jaygibran.deliveryfood.api"))
                .paths(PathSelectors.any())
//                .paths(PathSelectors.ant("/restaurants/*"))
                .build().apiInfo(apiInfo());
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("DeliveryFood API")
                .description("Open Api for clients and restaurants")
                .version("1")
                .contact(new Contact("DeliveryFood", "https://www.deliveryfood.com", "contato@deliveryfood.com"))
                .build();
    }
}
