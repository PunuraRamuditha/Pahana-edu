<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.icbt.model.Customer" %>
<%@ page import="com.icbt.model.Item" %>

<%
    List<Customer> customers = (List<Customer>) request.getAttribute("customers");
    List<Item> items = (List<Item>) request.getAttribute("items");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Multi-Item Billing</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f3f6f9;
            padding: 40px;
        }

        .container {
            max-width: 900px;
            margin: auto;
            background-color: #fff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0,0,0,0.05);
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }

        label {
            font-weight: bold;
            display: block;
            margin-bottom: 5px;
            margin-top: 15px;
        }

        select, input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
        }

        .actions a {
            text-decoration: none;
            padding: 10px 15px;
            background-color: #007bff;
            color: white;
            border-radius: 6px;
            transition: background-color 0.3s ease;
            margin-right: 10px;
        }

        .actions a:hover {
            background-color: #0056b3;
        }

        .form-group {
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        .btn-add {
            background-color: #28a745;
            color: white;
            padding: 10px 20px;
            border: none;
            margin-top: 10px;
            border-radius: 6px;
            cursor: pointer;
        }

        .btn-add:hover {
            background-color: #218838;
        }

        .btn-remove {
            background-color: #dc3545;
            color: white;
            padding: 5px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .btn-remove:hover {
            background-color: #b52a37;
        }

        input[type="submit"] {
            width: 100%;
            padding: 14px;
            margin-top: 30px;
            background-color: #007bff;
            color: white;
            font-size: 16px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
    <script>
        let itemCounter = 0;

        function addItem() {
            const itemSelect = document.getElementById("item");
            const quantity = document.getElementById("quantity").value;

            const itemCode = itemSelect.value;
            const itemName = itemSelect.options[itemSelect.selectedIndex].text;
            const qty = parseInt(quantity);

            if (!itemCode || qty <= 0) {
                alert("Please select a valid item and quantity.");
                return;
            }

            const table = document.getElementById("itemsTableBody");
            const row = table.insertRow();

            row.innerHTML = `
                <td>${itemName}</td>
                <td>${qty}</td>
                <td>
                    <input type="hidden" name="itemCodes" value="${itemCode}">
                    <input type="hidden" name="quantities" value="${qty}">
                    <button type="button" class="btn-remove" onclick="removeItem(this)">Remove</button>
                </td>
            `;

            // Reset item selector
            itemSelect.selectedIndex = 0;
            document.getElementById("quantity").value = "";
        }

        function removeItem(btn) {
            const row = btn.closest("tr");
            row.remove();
        }
    </script>
</head>
<body>

<div class="container">
    <h2>Create Bill</h2>

    <div class="actions">
        <a href="add-new-customer.jsp">+ Add Customer</a>
        <a href="CustomerServlet">👁 View Customers</a>
        <a href="add-item.jsp">+ Add Item</a>
    </div>

    <form action="BillServlet" method="post">

        <label for="customer">Select Customer:</label>
        <select name="customer" id="customer" required>
            <option value="">Select Customer</option>
            <% if (customers != null) {
                for (Customer c : customers) { %>
            <option value="<%= c.getAccountNumber() %>">
                <%= c.getName() %> - <%= c.getTelephone() %>
            </option>
            <% } } %>
        </select>

        <label for="item">Select Item:</label>
        <select id="item">
            <option value="">-- Select Item --</option>
            <% if (items != null) {
                for (Item i : items) { %>
<%--            <option value="<%= i.getItemCode() %>"><%= i.getName() %> (Rs. <%= i.getPrice() %>)</option>--%>
            <% } } %>
        </select>

        <label for="quantity">Quantity:</label>
        <input type="number" id="quantity" min="1">

        <button type="button" class="btn-add" onclick="addItem()">+ Add Item to Bill</button>

        <table>
            <thead>
            <tr>
                <th>Item</th>
                <th>Quantity</th>
                <th>Remove</th>
            </tr>
            </thead>
            <tbody id="itemsTableBody">
            <!-- Dynamically added rows -->
            </tbody>
        </table>

        <input type="submit" value="Submit Bill">
    </form>
</div>

</body>
</html>
