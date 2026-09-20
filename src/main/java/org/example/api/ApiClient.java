package org.example.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiClient {
    public Response get(String baseUri, String path) {
        return given().baseUri(baseUri).when().get(path);
    }

    public Response post(String baseUri, String path, Object body) {
        return given().baseUri(baseUri).contentType("application/json")
                .body(body).when().post(path);
    }

    public Response put(String baseUri, String path, Object body) {
        return given().baseUri(baseUri).contentType("application/json")
                .body(body).when().put(path);
    }

    public Response delete(String baseUri, String path) {
        return given().baseUri(baseUri).when().delete(path);
    }
}
