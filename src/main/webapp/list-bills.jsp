<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.icbt.model.Bill" %>
<%@ page import="com.icbt.model.BillItem" %>
<%@ page import="com.icbt.model.Customer" %>
<%@ page import="com.icbt.model.Item" %>

<%
    List<Bill> bills = (List<Bill>) request.getAttribute("bills");
    List<Customer> customers = (List<Customer>) request.getAttribute("customers");
    List<Item> items = (List<Item>) request.getAttribute("items");
%>
<%!
    String getCustomerName(int accountNumber, List<Customer> customersList) {
    for (Customer c : customersList) {
    if (c.getAccountNumber() == accountNumber) {
    return c.getName();
    }
    }
    return "Unknown";
    }

    String getItemName(int itemId, List<Item> itemsList) {
    for (Item it : itemsList) {
    if (it.getItem_id() == itemId) {
    return it.getItem_name();
    }
    }
    return "Unknown";
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Bill List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light p-4">
<div class="container">
    <h2 class="mb-4">All Bills</h2>

    <table class="table table-bordered table-hover">
        <thead class="table-dark">
        <tr>
            <th>Bill ID</th>
            <th>Customer</th>
            <th>Date</th>
            <th>Total Amount</th>
            <th>Items</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (bills != null && !bills.isEmpty()) {
            for (Bill bill : bills) {
        %>
        <tr>
            <td><%= bill.getBillId() %></td>
            <td><%= getCustomerName(bill.getAccountNumber(), customers) %></td>
            <td><%= bill.getBillingDate() %></td>
            <td>Rs. <%= bill.getAmount() %></td>
            <td>
                <ul class="mb-0">
                    <%
                        if (bill.getBillItems() != null) {
                        for (BillItem bi : bill.getBillItems()) {
                    %>
                    <li><%= getItemName(bi.getItemId(), items) %> - <%= bi.getQuantity() %> x Rs.<%= bi.getUnitPrice() %></li>
                    <%
                        }
                        }
                    %>
                </ul>
            </td>
            <td>
                <a href="BillServlet?action=view&id=<%= bill.getBillId() %>" class="btn btn-info btn-sm">View</a>
                <a href="BillServlet?action=delete&id=<%= bill.getBillId() %>"
                   class="btn btn-danger btn-sm"
                   onclick="return confirm('Are you sure you want to delete this bill?');">Delete</a>
            </td>
        </tr>
        <%
            }
            } else {
        %>
        <tr>
            <td colspan="6" class="text-center">No bills found.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
