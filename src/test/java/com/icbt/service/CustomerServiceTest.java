package com.icbt.service;

import com.icbt.model.Customer;
import com.icbt.util.DBConnection;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {

    private CustomerService customerService;
    private int testAccountNumber;

    @BeforeAll
    void setUp() throws SQLException {
        customerService = new CustomerService();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO customers (name, address, telephone) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, "Test Name");
            stmt.setString(2, "Test Address");
            stmt.setString(3, "1234567890");
            stmt.executeUpdate();

            // Capture the generated account number
            try (var rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    testAccountNumber = rs.getInt(1);
                }
            }
        }
    }

    @Test
    void testRegisterCustomer() {
        Customer newCustomer = new Customer("John Doe", "Colombo", "0771234567");

        boolean result = customerService.registerCustomer(newCustomer);

        assertTrue(result, "Customer should be registered successfully");
    }

    @Test
    void testUpdateCustomer() {
        Customer customer = customerService.getCustomerById(testAccountNumber);
        assertNotNull(customer, "Test customer should exist before update");

        customer.setName("Updated Name");
        customer.setAddress("Updated Address");
        customer.setTelephone("Updated Telephone");

        boolean updated = customerService.updateCustomer(customer);

        assertTrue(updated, "Customer update should be successful");

        Customer updatedCustomer = customerService.getCustomerById(testAccountNumber);
        assertEquals("Updated Name", updatedCustomer.getName());
        assertEquals("Updated Address", updatedCustomer.getAddress());
        assertEquals("Updated Telephone", updatedCustomer.getTelephone());
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        assertNotNull(customers, "Customer list should not be null");
        assertFalse(customers.isEmpty(), "Customer list should not be empty");
    }

    @Test
    void testGetCustomerById() {
        Customer customer = customerService.getCustomerById(testAccountNumber);
        assertNotNull(customer, "Customer should be found by account number");
        assertEquals(testAccountNumber, customer.getAccountNumber());
    }

    @AfterAll
    void tearDown() throws SQLException {
        // Clean up test customer
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM customers WHERE account_number = ?")) {
            stmt.setInt(1, testAccountNumber);
            stmt.executeUpdate();
        }
    }
}
