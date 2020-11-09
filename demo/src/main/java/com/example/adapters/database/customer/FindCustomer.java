package com.example.adapters.database.customer;

import com.example.core.domain.customer.Customer;
import com.example.core.domain.customer.FindCustomerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FindCustomer implements FindCustomerPort {

    private final CustomerRepository customerRepository;

    public FindCustomer(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }


    private static final Logger log = LoggerFactory.getLogger(FindCustomer.class);

    @Override
    public List<Customer> findCustomer(Map<String, String> customerSearchValues) {
        List<CustomerModel> listCustomersModel = new ArrayList<>();

        if (customerSearchValues.containsKey("firstname"))
            listCustomersModel = customerRepository.findByFirstName(Optional.of(customerSearchValues.get("firstname")));
        else if (customerSearchValues.containsKey("lastname"))
            listCustomersModel = customerRepository.findByLastName(Optional.of(customerSearchValues.get("lastname")));
        else if (customerSearchValues.containsKey("doctype") && customerSearchValues.containsKey("docnumber"))
            listCustomersModel = customerRepository.findByDocTypeAndDocNumber(Optional.of(customerSearchValues.get("doctype")), Optional.of(customerSearchValues.get("docnumber")));


        log.info("-----------------------");
        log.info("Dados informados: " + customerSearchValues.get("firstname") + " " + customerSearchValues.get("lastname"));
        log.info("-----------------------");


        log.info("Nomes da lista retornada:");
        for (CustomerModel lc : listCustomersModel) {
            log.info(lc.getFirstName() + " " + lc.getLastName());
        }

        log.info("-----------------------");

        Iterable<CustomerModel> customers = customerRepository.findAll();

        for (CustomerModel c : customers) {
            log.info("Cliente " + c.getId() + ": " + c.getFirstName() + " " + c.getLastName());
        }

        List<Customer> listCustomers = new ArrayList<>();

        for (CustomerModel c : customers) {
            Customer customer = new Customer();
            customer.setFirstName(c.getFirstName());
            customer.setLastName(c.getLastName());
            customer.setDocType(c.getDocType());
            customer.setDocNumber(c.getDocNumber());

            listCustomers.add(customer);
        }

        return listCustomers;
    }
}
