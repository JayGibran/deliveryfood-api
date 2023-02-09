package com.jaygibran.deliveryfood.core.openapi;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jaygibran.deliveryfood.api.exceptionhandler.Problem;
import com.jaygibran.deliveryfood.api.v1.model.CuisineDTO;
import com.jaygibran.deliveryfood.api.v1.model.OrderSummarizedDTO;
import com.jaygibran.deliveryfood.api.v1.openapi.model.CuisinesModelOpenApi;
import com.jaygibran.deliveryfood.api.v1.openapi.model.OrderSummarizedOpenApi;
import com.jaygibran.deliveryfood.api.v1.openapi.model.PageableModelOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@EnableWebMvc
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    @Bean
    public Docket apiDocket() {

        var typeResolver = new TypeResolver();

        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jaygibran.deliveryfood.api"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
//                .globalRequestParameters(Collections.singletonList(new RequestParameterBuilder()
//                        .name("fields")
//                        .description("Property name to filter on response, separated by comma")
//                        .in(ParameterType.QUERY)
//                        .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//                        .build()))
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, CuisineDTO.class),
                        CuisinesModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, OrderSummarizedDTO.class),
                        OrderSummarizedOpenApi.class))
                .apiInfo(apiInfo())
                .tags(new Tag("Cities", "Management of cities"),
                        new Tag("Groups", "Management of user groups"),
                        new Tag("Cuisines", "Management of cuisines"),
                        new Tag("Payment Methods", "Management of payment methods"),
                        new Tag("Orders", "Management of orders"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("DeliveryFood API")
                .description("Open Api for clients and restaurants")
                .version("1")
                .contact(new Contact("DeliveryFood", "https://www.deliveryfood.com", "contato@deliveryfood.com"))
                .build();
    }


    private List<Response> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Internal Server Error")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getApiErrorModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Resource does not have representation that can be accepted by consumer")
                        .build()
        );
    }

    private List<Response> globalPostPutResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Invalid Request (client error)")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getApiErrorModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Internal Server Error")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getApiErrorModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Resource does not have representation that can be accepted by consumer")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                        .description("Request wast not accepted because body is in a invalid format")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getApiErrorModelReference())
                        .build()
        );
    }

    private Consumer<RepresentationBuilder> getApiErrorModelReference() {
        return r -> r.model(m -> m.name("Problem")
                .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(q -> q.name("Problem")
                        .namespace("com.jaygibran.deliveryfood.api.exceptionhandler")))));
    }

    private List<Response> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Invalid Request (client error)")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Internal Server Error")
                        .build()
        );
    }

    @Bean
    public JacksonModuleRegistrar springFoxJacksonConfig() {
        return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
    }
}
