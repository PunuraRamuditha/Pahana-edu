package com.icbt.servlet;

import com.icbt.model.Customer;
import com.icbt.service.CustomerService;
import com.icbt.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/CustomerServlet")


public class CustomerServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Customer> customerList = customerService.getAllCustomers();
        request.setAttribute("customers", customerList);
        request.getRequestDispatcher("show-customer.jsp").forward(request, response);
    }

}