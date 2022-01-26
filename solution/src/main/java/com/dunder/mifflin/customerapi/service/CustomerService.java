package com.dunder.mifflin.customerapi.service;

import com.dunder.mifflin.customerapi.entity.Customer;
import com.dunder.mifflin.customerapi.model.CustomerModel;
import com.dunder.mifflin.customerapi.repository.CustomerRepository;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service for Customer related operations.
 *
 * @author Edom Mesfin
 * @since 2022
 */
@Service
public class CustomerService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepo;
    private final ModelMapper mapper = new ModelMapper();

    public String createOrUpdate(CustomerModel mCustomer) {
        // Transfer data to data object - helps hiding implementation details of domain objects (aka. entities)
        String uuid = StringUtils.isBlank(mCustomer.getUuid()) ? UUID.randomUUID().toString() : mCustomer.getUuid();
        Customer cEntity = new Customer();
        cEntity.setUuid(uuid);
        cEntity.setFName(mCustomer.getFName());
        cEntity.setLName(mCustomer.getLName());

        Date now = new Date();
        cEntity.setCreatedDate(now);
        cEntity.setUpdatedDate(now);

        customerRepo.save(cEntity);
        return uuid;
    }

    public CustomerModel getCustomerByuuid(String uuid) {
        Customer cEntity = customerRepo.findById(uuid).orElse(null); // From, Optional<Customer>
        return (cEntity != null) ? mapper.map(cEntity, CustomerModel.class) : null;
    }

    public List<CustomerModel> getAllCustomers() { /* Using the embedded H2 database data entry */
        List<CustomerModel> customers = new ArrayList<>();
        customerRepo.findAll().forEach(customer -> customers.add(mapper.map(customer, CustomerModel.class)));
        return customers;
    }

    public boolean updateCustomer(CustomerModel mCustomer) {
        Optional<Customer> customer = customerRepo.findById(mCustomer.getUuid());
        if (customer.isPresent()) {
            Customer cEntity = customer.get();
            cEntity.setFName(mCustomer.getFName());
            cEntity.setLName(mCustomer.getLName());
            cEntity.setUpdatedDate(new Date());

            customerRepo.save(cEntity);

            return true;
        }
        return false;
    }

    public boolean deleteCustomerByuuid(String uuid) {
        Optional<Customer> customer = customerRepo.findById(uuid);
        boolean present = customer.isPresent();

        if (present) customerRepo.deleteById(uuid);
        return present;
    }

}
