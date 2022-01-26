package com.dunder.mifflin.customerapi.rest;

import com.dunder.mifflin.customerapi.model.CustomerModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Interface for Dunder Customer API.
 *
 */
@RestController
@RequestMapping(value = ICustomerAPI.ROOT)
@Api(tags = "Dunder Customer API")
public interface ICustomerAPI {
    String ROOT = "dunder/customers";

    @PostMapping("/")
    @ApiOperation(
            value = "Create new Customer Record.",
            notes = "This operation is for creating customer record for the first time.",
            response = String.class)
    ResponseEntity<String> createCustomer(@RequestBody CustomerModel customer);

    @GetMapping(value = "/")
    @ApiOperation(
            value = "Retrieve all registered Dunder customers.",
            notes = "Retrieves all previously registered customers and returns list of Customers.",
            response = CustomerModel.class)
    List<CustomerModel> getCustomers();

    @GetMapping("/{uuid}")
    @ApiOperation(
            value = "Get specific customer.",
            notes = "Retrieves a registered customer using provided customer unique id (uuid).",
            response = CustomerModel.class)
    ResponseEntity<CustomerModel> getCustomer(@PathVariable String uuid);

    @PutMapping("/")
    @ApiOperation(
            value = "Update existing Customer Record.",
            notes = "This operation is for updating existing customer record with new values. ID is not updatable.",
            response = String.class)
    ResponseEntity<String> updateCustomer(@RequestBody CustomerModel customer);

    @DeleteMapping("/{uuid}")
    @ApiOperation(
            value = "Delete specific customer.",
            notes = "Deletes existing customer using provided uuid, and returns status object.",
            response = String.class)
    ResponseEntity<String> deleteCustomer(@PathVariable String uuid);

}
