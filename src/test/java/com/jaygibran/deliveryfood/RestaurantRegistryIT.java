package com.jaygibran.deliveryfood;

import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.CuisineRepository;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepository;
import com.jaygibran.deliveryfood.util.DatabaseCleaner;
import com.jaygibran.deliveryfood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RestaurantRegistryIT {

    private static final String BUSINESS_RULE_PROBLEM_TYPE = "Business exception";

    private static final String INVALID_DATA_PROBLEM_TITLE = "Invalid data";

    private static final int RESTAURANT_ID_NON_EXISTENT = 100;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CuisineRepository cuisineRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private String jsonRestaurant;
    private String jsonRestaurantWithoutFee;
    private String jsonRestaurantWithoutCuisine;
    private String jsonRestaurantWithCuisineNonExistent;

    private Restaurant topRestaurantBurger;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurants";

        jsonRestaurant = ResourceUtils.getContentFromResource(
                "/json/restaurant-new-york-barbecue.json");

        jsonRestaurantWithoutFee = ResourceUtils.getContentFromResource(
                "/json/restaurant-new-york-barbecue-without-fee-delivery.json");

        jsonRestaurantWithoutCuisine = ResourceUtils.getContentFromResource(
                "/json/restaurant-without-cuisine.json");

        jsonRestaurantWithCuisineNonExistent = ResourceUtils.getContentFromResource(
                "/json/restaurant-new-york-barbecue-nonexistent.json");

        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    public void shouldReturnStatus200WhenFetchRestaurants() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturnStatus201WhenRegistryRestaurant() {
        given()
                .body(jsonRestaurant)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturnStatus400WhenRegistryRestaurantWithoutFeeDelivery() {
        given()
                .body(jsonRestaurantWithoutFee)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(INVALID_DATA_PROBLEM_TITLE));
    }

    @Test
    public void shouldReturnStatus400WhenRegistryRestaurantWithoutCuisine() {
        given()
                .body(jsonRestaurantWithoutCuisine)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(INVALID_DATA_PROBLEM_TITLE));
    }

    @Test
    public void shouldReturnStatus400WhenRegistryRestaurantWithNonExistentCuisine() {
        given()
                .body(jsonRestaurantWithCuisineNonExistent)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(BUSINESS_RULE_PROBLEM_TYPE));
    }

    @Test
    public void shouldReturnStatus200WhenSearchByRestaurant() {
        given()
                .pathParam("restaurantId", topRestaurantBurger.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{restaurantId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(topRestaurantBurger.getName()));
    }

    @Test
    public void shouldReturnStatus404WhenSearchByNonExistentRestaurant() {
        given()
                .pathParam("restaurantId", RESTAURANT_ID_NON_EXISTENT)
                .accept(ContentType.JSON)
                .when()
                .get("/{restaurantId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepareData() {
        Cuisine brazilianCuisine = new Cuisine();
        brazilianCuisine.setName("Brasileira");
        cuisineRepository.save(brazilianCuisine);

        Cuisine americanCuisine = new Cuisine();
        americanCuisine.setName("Americana");
        cuisineRepository.save(americanCuisine);

        topRestaurantBurger = new Restaurant();
        topRestaurantBurger.setName("Burger Top");
        topRestaurantBurger.setFeeDelivery(new BigDecimal(10));
        topRestaurantBurger.setCuisine(americanCuisine);
        restaurantRepository.save(topRestaurantBurger);

        Restaurant comidaMineiraRestaurante = new Restaurant();
        comidaMineiraRestaurante.setName("Comida Mineira");
        comidaMineiraRestaurante.setFeeDelivery(new BigDecimal(10));
        comidaMineiraRestaurante.setCuisine(brazilianCuisine);
        restaurantRepository.save(comidaMineiraRestaurante);
    }

}
