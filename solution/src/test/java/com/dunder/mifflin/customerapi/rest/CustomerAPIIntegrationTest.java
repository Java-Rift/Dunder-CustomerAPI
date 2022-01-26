package com.dunder.mifflin.customerapi.rest;

import com.dunder.mifflin.customerapi.DunderCustomerApiApplication;
import com.dunder.mifflin.customerapi.model.CustomerModel;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

/**
 * Integration test for the Customer API.
 * The purpose of this Integration test is to test all layers in the application.
 *
 * @author Edom Mesfin
 * @since 2022
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DunderCustomerApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CustomerAPIIntegrationTest {

    @LocalServerPort
    private int port; // Configure local dynamic port
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Test
    void testCreateCustomer() {
        // Create some mock customer to test with...
        CustomerModel mockCustomerModel = new CustomerModel("b42cceb8-cb9e-4a2a-b6c9-ace66a1d9143", "James", "Smith");

        HttpEntity<CustomerModel> entity = new HttpEntity<>(mockCustomerModel, headers); //HttpEntity converts 'mockCustomerModel' to JSON...
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/dunder/customers/"), HttpMethod.POST, entity, String.class);

        System.out.println("*** Response body: " + response.getBody());
        String actual = Objects.requireNonNull(response.getHeaders().get(HttpHeaders.LOCATION)).get(0);
        Assertions.assertTrue(actual.contains("dunder/customers/"));
    }

    @Test
    void testGetCustomer() throws JSONException {
        String uuid = "b42cceb8-cb9e-4a2a-b6c9-ace66a1d9143";

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/dunder/customers/" + uuid), HttpMethod.GET, entity, String.class);

        System.out.println("*** Response body: " + response.getBody());
        String expected = "{fname: James, lname: Smith, uuid: b42cceb8-cb9e-4a2a-b6c9-ace66a1d9143}";
        JSONAssert.assertEquals(expected, String.valueOf(response.getBody()), false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}