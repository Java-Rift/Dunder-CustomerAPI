package com.dunder.mifflin.customerapi.repository;

import com.dunder.mifflin.customerapi.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository for Customer Entity, A data access layer abstracting all the boilerplate operations.
 *
 * @author Edom Mesfin
 * @since 2022
 */
@RepositoryRestResource
public interface CustomerRepository extends CrudRepository<Customer, String> {

}
