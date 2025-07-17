package com.icbt.service;

import com.icbt.dao.CustomerDAO;
import com.icbt.model.Customer;

public class CustomerService {
    private CustomerDAO customerDAO = new CustomerDAO();

    public boolean registerCustomer(Customer customer) {
        return customerDAO.addCustomer(customer);
    }
}
