package com.icbt.servlet;

import com.icbt.model.Customer;
import com.icbt.service.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/AddNewCustomerServlet")

public class AddNewCustomerServlet extends HttpServlet {
    private CustomerService customerService = new CustomerService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");

        Customer customer = new Customer(name, address, telephone);
        boolean success = customerService.registerCustomer(customer);

        if (success) {
            response.sendRedirect("index.jsp"); // Create a success page
        } else {
            response.sendRedirect("error.jsp"); // Create an error page
        }
    }
}