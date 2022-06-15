package com.jaygibran.deliveryfood;

import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.repository.CuisineRepository;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CuisineRegistryIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CuisineRepository cuisineRepository;

    private Cuisine brazilianCuisine;
    private String jsonChineseCuisine;
    private static final int CUISINE_ID_DOES_NOT_EXISTS = 5;
    private int quantity_cuisines_registered;

    @BeforeEach
    void setUp() {
        jsonChineseCuisine = ResourceUtils.getContentFromResource(
                "/json/chinese_cuisine.json");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cuisines";
        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    void shouldReturnStatus200WhenFetchCuisines() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void shouldQuantityCorrectOfCuisines() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", hasSize(quantity_cuisines_registered))
                .body("name", hasItems("Italian", "Brazilian"));
    }

    @Test
    void shouldReturnStatus201WhenSaveCuisine() {
        given().body(jsonChineseCuisine)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturnProperResponseAndStatusWhenCuisineExists() {
        given()
                .pathParam("cuisineId", brazilianCuisine.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{cuisineId}")
                .then().statusCode(HttpStatus.OK.value())
                .body("name", equalTo(brazilianCuisine.getName()));
    }

    @Test
    public void shouldReturnStatus404WhenCuisineDoesNotExists() {
        given()
                .pathParam("cuisineId", CUISINE_ID_DOES_NOT_EXISTS)
                .accept(ContentType.JSON)
                .when()
                .get("/{cuisineId}")
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepareData() {
        Cuisine cuisine1 = new Cuisine();
        cuisine1.setName("Italian");
        cuisineRepository.save(cuisine1);

        Cuisine cuisine2 = new Cuisine();
        cuisine2.setName("Brazilian");
        brazilianCuisine = cuisineRepository.save(cuisine2);

        quantity_cuisines_registered = (int) cuisineRepository.count();
    }

//    @Autowired
//    CuisineRegistryService cuisineRegistryService;
//
//    @Autowired
//    RestaurantRegistryService restaurantRegistryService;
//
//    @Test
//    public void saveCuisineSuccessful() {
//        // given
//        Cuisine cuisine = new Cuisine();
//        cuisine.setName("Brazilian");
//
//        // when
//        Cuisine savedCuisine = cuisineRegistryService.save(cuisine);
//
//        // then
//        assertThat(savedCuisine).isNotNull();
//        assertThat(savedCuisine.getId()).isNotNull();
//    }
//
//    @Test
//    public void shouldFailsWhenTrySaveCuisineWithoutName() {
//        // given
//        Cuisine cuisine = new Cuisine();
//
//        // when
//        ConstraintViolationException error =
//                assertThrows(ConstraintViolationException.class, () -> {
//                    cuisineRegistryService.save(cuisine);
//                });
//
//
//        // then
//        assertThat(error).isNotNull();
//    }
//
//    @Test
//    public void shouldFailsWhenTryDeleteCuisineInUse() {
//        // given
//        Cuisine cuisine = new Cuisine();
//        cuisine.setName("Brazilian");
//        Cuisine savedCuisine = cuisineRegistryService.save(cuisine);
//        Restaurant restaurant = new Restaurant();
//        restaurant.setCuisine(cuisine);
//        restaurant.setName("Brazilian Grill");
//        restaurant.setFeeDelivery(BigDecimal.valueOf(10));
//        restaurantRegistryService.save(restaurant);
//
//        // when
//        EntityInUseException entityInUseException = assertThrows(EntityInUseException.class, () -> {
//            cuisineRegistryService.delete(savedCuisine.getId());
//        });
//
//        // then
//        assertThat(entityInUseException).isNotNull();
//    }
//
//    @Test
//    public void shouldFailsWhenTryDeleteCuisineWhichDoesNotExists() {
//        // given
//        Cuisine cuisine = new Cuisine();
//        cuisine.setId(15645L);
//
//        // when
//        CuisineNotFoundException cuisineNotFoundException = assertThrows(CuisineNotFoundException.class, () -> {
//            cuisineRegistryService.delete(cuisine.getId());
//        });
//
//        // then
//        assertThat(cuisineNotFoundException).isNotNull();
//    }
}
