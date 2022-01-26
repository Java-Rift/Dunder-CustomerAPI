package com.dunder.mifflin.customerapi.rest;

import com.dunder.mifflin.customerapi.model.CustomerModel;
import com.dunder.mifflin.customerapi.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Unit test for the Customer REST Controller (CustomerAPIimpl.class).
 *
 * @author Edom Mesfin
 * @since 2022
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerAPIimpl.class)
class CustomerAPIUnitTest {

    @Autowired
    private MockMvc mocMvc; // wire mockMVC to fire a request...

    @MockBean
    private CustomerService cService; // use the mock service

    @Test
    public void testGetCustomer() throws Exception { // Get customer by UUID resource.
        String uuid = "b42cceb8-cb9e-4a2a-b6c9-ace66a1d9143";
        // Create some mock customer to test with...
        CustomerModel mockCustomerModel = new CustomerModel("b42cceb8-cb9e-4a2a-b6c9-ace66a1d9143", "James", "Smith");

        // When the 'getCustomerByuuid' method is called on the mock service, return the mock customer...
        Mockito.when(cService.getCustomerByuuid(Mockito.anyString())).thenReturn(mockCustomerModel);
        // Create a mock GET request builder, to be able to fire a request and accept a JSON result...
        RequestBuilder reqBuilder = MockMvcRequestBuilders
                .get("/dunder/customers/" + uuid)
                .accept(MediaType.APPLICATION_JSON);
        // Fire a request to the above url and get a response...
        MvcResult result = mocMvc.perform(reqBuilder).andReturn();
        System.out.println(result.getResponse().getContentAsString());

        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        String expected = "{fname: James, lname: Smith, uuid: b42cceb8-cb9e-4a2a-b6c9-ace66a1d9143}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT); // with any ordering (no strict ordering)...
    }


    @Test
    public void testCreateCustomer() throws Exception { // Create customer resource.
        String uuid = "b42cceb8-cb9e-4a2a-b6c9-ace66a1d9143";
        String customerJsonStr = "{" +
                "  \"fname\": \"James\"," +
                "  \"lname\": \"Smith\"," +
                "  \"uuid\": \"b42cceb8-cb9e-4a2a-b6c9-ace66a1d9143\"}";

        Mockito.when(cService.createOrUpdate(Mockito.any(CustomerModel.class))).thenReturn(uuid);
        RequestBuilder reqBuilder = MockMvcRequestBuilders
                .post("/dunder/customers/")
                .accept(MediaType.APPLICATION_JSON)
                .content(customerJsonStr)
                .contentType(MediaType.APPLICATION_JSON);

        // Fire a request to the above url and get a response...
        MvcResult result = mocMvc.perform(reqBuilder).andReturn();
        System.out.println(result.getResponse().getContentAsString());

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        Assertions.assertEquals("dunder/customers/b42cceb8-cb9e-4a2a-b6c9-ace66a1d9143", response.getHeader(HttpHeaders.LOCATION));
    }
}