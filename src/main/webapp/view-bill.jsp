<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.icbt.model.Bill" %>
<%@ page import="com.icbt.model.BillItem" %>
<%@ page import="com.icbt.model.Customer" %>
<%@ page import="com.icbt.model.Item" %>

<%
    Bill bill = (Bill) request.getAttribute("bill");
    Customer customer = (Customer) request.getAttribute("customer");
    List<BillItem> billItems = (List<BillItem>) request.getAttribute("billItems");
    Map<Integer, Item> itemsMap = (Map<Integer, Item>) request.getAttribute("itemsMap");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Bill Details</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script>
        function printBill() {
            window.print();
        }
    </script>
    <style>
        /* Hide buttons when printing */
        @media print {
            .no-print {
                display: none;
            }
        }
    </style>
</head>
<body class="bg-light p-4">
<div class="container">
    <h2 class="mb-4">Bill Details</h2>

    <div class="card mb-4">
        <div class="card-body">
            <h5 class="card-title">Bill #<%= bill.getBillId() %></h5>
            <p><strong>Customer:</strong> <%= customer != null ? customer.getName() : "Unknown" %></p>
            <p><strong>Date:</strong> <%= bill.getBillingDate() %></p>
            <p><strong>Total Amount:</strong> Rs. <%= bill.getAmount() %></p>
        </div>
    </div>

    <h4>Items</h4>
    <table class="table table-bordered">
        <thead class="table-dark">
        <tr>
            <th>Item Name</th>
            <th>Quantity</th>
            <th>Unit Price</th>
            <th>Subtotal</th>
        </tr>
        </thead>
        <tbody>
        <% if (billItems != null && !billItems.isEmpty()) {
            for (BillItem bi : billItems) {
                Item item = itemsMap.get(bi.getItemId());
        %>
        <tr>
            <td><%= item != null ? item.getItem_name() : "Unknown" %></td>
            <td><%= bi.getQuantity() %></td>
            <td>Rs. <%= bi.getUnitPrice() %></td>
            <td>Rs. <%= bi.getQuantity() * bi.getUnitPrice() %></td>
        </tr>
        <%   }
        } else { %>
        <tr>
            <td colspan="4" class="text-center">No items found.</td>
        </tr>
        <% } %>
        </tbody>
    </table>

    <div class="mt-3 no-print">
        <a href="BillServlet" class="btn btn-secondary">Back to Bills</a>
        <button class="btn btn-primary" onclick="printBill()">Print Bill</button>
    </div>
</div>
</body>
</html>
