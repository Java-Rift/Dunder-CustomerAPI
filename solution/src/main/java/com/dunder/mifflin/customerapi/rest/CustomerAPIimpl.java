package com.dunder.mifflin.customerapi.rest;

import com.dunder.mifflin.customerapi.model.CustomerModel;
import com.dunder.mifflin.customerapi.service.CustomerService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Implements the Customer API end-points.
 *
 * @author Edom Mesfin
 * @since 2022
 */
@Service("customerAPIimpl")
public class CustomerAPIimpl implements ICustomerAPI {
    private static final Logger LOG = getLogger(ICustomerAPI.class);

    @Autowired
    private CustomerService cService;

    /**
     * Post resource for creating customer.
     *
     * @param mCustomer JASON param.
     * @return operation Status.
     */
    @Override
    public ResponseEntity<String> createCustomer(CustomerModel mCustomer) {
        if (mCustomer == null || StringUtils.isBlank(mCustomer.getFName()) || StringUtils.isBlank(mCustomer.getLName())) {
            String errMsg = "Proper customer detail is expected.";
            LOG.debug(errMsg + ", Invalid or null customer object - createCustomer");

            return ResponseEntity.badRequest().body(errMsg);
        }

        // Persist data
        String uuid = cService.createOrUpdate(mCustomer);

        String msg = "Customer successfully created. unique ID: " + uuid;
        LOG.info(msg);
        return ResponseEntity.created(URI.create(ROOT + "/" + uuid)).body(msg);
    }

    /**
     * Get resource, for retrieving all customer records.
     *
     * @return List of Customers.
     */
    @Override
    public List<CustomerModel> getCustomers() {
        return cService.getAllCustomers();
    }

    /**
     * Get resource based on provided customer uuid.
     *
     * @param uuid unique id representing existing customer record.
     * @return Customer JSON.
     */
    @Override
    public ResponseEntity<CustomerModel> getCustomer(String uuid) {
        CustomerModel mCustomer = cService.getCustomerByuuid(uuid);

        HttpHeaders headers = new HttpHeaders();
        headers.add("UUID", uuid);
        if (mCustomer != null) {
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType(String.valueOf(MediaType.APPLICATION_JSON)))
                    .body(mCustomer);
        }

        String errMsg = "The customer detail could not be found (uuid: " + uuid + ")";
        LOG.debug(errMsg);
        headers.add("ErrorMsg", errMsg);
        return ResponseEntity.notFound().headers(headers).build();
    }

    /**
     * Update resource, for updating existing mCustomer record based on new mCustomer record.
     *
     * @param mCustomer new mCustomer record for updating existing record.
     * @return updated status.
     */
    @Override
    public ResponseEntity<String> updateCustomer(CustomerModel mCustomer) {
        if (mCustomer == null ||
            StringUtils.isBlank(mCustomer.getUuid()) || StringUtils.isBlank(mCustomer.getFName()) || StringUtils.isBlank(mCustomer.getLName())) {
            String errMsg = "Proper customer detail is expected.";
            LOG.debug(errMsg + ", Invalid or null Customer object - updateCustomer");

            return ResponseEntity.badRequest().body(errMsg);
        }

        // Update data
        boolean isUpdated = cService.updateCustomer(mCustomer);
        if (isUpdated) {
            String msg = "Customer successfully updated. unique ID: " + mCustomer.getUuid();
            LOG.info(msg);
            return ResponseEntity.ok().body(msg);
        }

        String errMsg = "The customer detail could not be found (uuid: " + mCustomer.getUuid() + ")";
        LOG.debug(errMsg);
        return ResponseEntity.notFound().header("ErrorMsg", errMsg).build();
    }

    /**
     * Delete resource, for removing existing customer recurd using uuid.
     *
     * @param uuid unique customer id
     * @return operation status.
     */
    @Override
    public ResponseEntity<String> deleteCustomer(String uuid) {
        if (cService.deleteCustomerByuuid(uuid)) {
            String msg = "Customer successfully removed. unique ID: " + uuid;
            LOG.info(msg);
            return ResponseEntity.ok().body(msg);
        }

        String errMsg = "The customer detail could not be found (uuid: " + uuid + ")";
        LOG.debug(errMsg);
        return ResponseEntity.notFound().header("ErrorMsg", errMsg).build();
    }
}
