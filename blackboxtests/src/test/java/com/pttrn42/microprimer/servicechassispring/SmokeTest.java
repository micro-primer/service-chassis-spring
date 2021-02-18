package com.pttrn42.microprimer.servicechassispring;

import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

@Slf4j
public class SmokeTest {
    private static final String TEST_NAME_PER_SERVICE_INSTANCE = RepeatedTest.DISPLAY_NAME_PLACEHOLDER + " service instance " + RepeatedTest.CURRENT_REPETITION_PLACEHOLDER;

    @RegisterExtension
    public static SystemTestEnvironment environment = new SystemTestEnvironment();

    @RepeatedTest(value = SystemTestEnvironment.SERVICE_NUM_INSTANCES, name = TEST_NAME_PER_SERVICE_INSTANCE)
    void pingPongs(RepetitionInfo repetitionInfo) {
        given()
                .baseUri(environment.serviceUrl(repetitionInfo.getCurrentRepetition())) //example how to get url of the specific instance
                .when()
                .get("/actuator/ping")
                .then()
                .body(containsString("pong"))
                .statusCode(200);
    }

    @Test
    void shouldBeHealthy() {
        given()
                .baseUri(environment.serviceUrl()) //example how to get url of the random instance
                .when()
                .get("/actuator/health")
                .then()
                .statusCode(200);
    }

    @Test
    void shouldExposeJmxControls() {
        given()
                .baseUri(environment.serviceUrl()) //example how to get url of the random instance
                .queryParam("mimeType", "application/json")
                .when()
                .get("/actuator/jolokia")
                .then()
                .body("status", equalTo(200))
                .body("request.type", equalTo("version"))
                .statusCode(200);
    }

    @Disabled
    @Test
    void shouldExposeHawtioUi() {
        given()
                .baseUri(environment.serviceUrl()) //example how to get url of the random instance
                .when()
                .get("/actuator/hawtio")
                .then()
                .body(containsString("<title>Hawtio</title>"))
                .statusCode(200);
    }

    @Test
    void shouldExposeThreadDump() {
        given()
                .baseUri(environment.serviceUrl()) //example how to get url of the random instance
                .when()
                .get("/actuator/threaddump")
                .then()
                .statusCode(200);
    }

    @Test
    void shouldExposePrometheusMetrics() {
        given()
                .baseUri(environment.serviceUrl()) //example how to get url of the random instance
                .accept(ContentType.TEXT)
                .when()
                .get("/actuator/prometheus")
                .then()
                .body(containsString("app=\"service-chassis-spring\""))
                .statusCode(200);
    }

    @Test
    void shouldExposeSwaggerApi() {
        given()
                .baseUri(environment.serviceUrl()) //example how to get url of the random instance
                .accept(ContentType.JSON)
                .when()
                .get("/v2/api-docs")
                .then()
                .body("swagger", equalTo("2.0"))
                .statusCode(200);
    }

    @Test
    void shouldExposeSwaggerUI() {
        given()
                .baseUri(environment.serviceUrl()) //example how to get url of the random instance
                .when()
                .get("/actuator/swagger-ui/index.html")
                .then()
                .body(containsString("<title>Swagger UI</title>"))
                .statusCode(200);
    }

}
