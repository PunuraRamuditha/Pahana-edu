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
        body { font-family: Arial, sans-serif; background-color: #f3f6f9; padding: 40px; }
        .container { max-width: 900px; margin: auto; background-color: #fff; padding: 30px; border-radius: 12px; box-shadow: 0 0 10px rgba(0,0,0,0.05); }
        h2 { text-align: center; color: #333; margin-bottom: 30px; }
        label { font-weight: bold; display: block; margin-bottom: 5px; margin-top: 15px; }
        select, input[type="number"] { width: 100%; padding: 10px; margin-top: 5px; margin-bottom: 15px; border: 1px solid #ccc; border-radius: 6px; }
        .actions a { text-decoration: none; padding: 10px 15px; background-color: #007bff; color: white; border-radius: 6px; margin-right: 10px; }
        .actions a:hover { background-color: #0056b3; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; border: 1px solid #ddd; text-align: center; }
        th { background-color: #007bff; color: white; }
        .btn-add { background-color: #28a745; color: white; padding: 10px 20px; border: none; border-radius: 6px; cursor: pointer; }
        .btn-add:hover { background-color: #218838; }
        .btn-remove { background-color: #dc3545; color: white; padding: 5px 12px; border: none; border-radius: 4px; cursor: pointer; }
        .btn-remove:hover { background-color: #b52a37; }
        input[type="submit"] { width: 100%; padding: 14px; margin-top: 30px; background-color: #007bff; color: white; font-size: 16px; border: none; border-radius: 6px; cursor: pointer; }
        input[type="submit"]:hover { background-color: #0056b3; }
        .total-row td { font-weight: bold; background: #f8f9fa; }
    </style>
    <script>
        function addItem() {
            const itemSelect = document.getElementById("item");
            const quantityInput = document.getElementById("quantities");

            const itemCode = itemSelect.value;
            const itemName = itemSelect.options[itemSelect.selectedIndex].text;
            const itemPrice = parseFloat(itemSelect.options[itemSelect.selectedIndex].getAttribute("data-price"));
            const qty = parseInt(quantityInput.value);

            if (!itemCode || isNaN(qty) || qty <= 0) {
                alert("Please select a valid item and quantity.");
                return;
            }

            const subtotal = (itemPrice * qty).toFixed(2);

            const templateRow = document.getElementById("itemRowTemplate").cloneNode(true);
            templateRow.removeAttribute("id");
            templateRow.style.display = "";

            templateRow.querySelector(".itemName").textContent = itemName;
            templateRow.querySelector(".itemQty").textContent = qty;
            templateRow.querySelector(".itemPrice").textContent = itemPrice.toFixed(2);
            templateRow.querySelector(".itemTotal").textContent = subtotal;

            templateRow.querySelector(".itemIdInput").value = itemCode;
            templateRow.querySelector(".itemQtyInput").value = qty;
            templateRow.querySelector(".itemPriceInput").value = itemPrice.toFixed(2);

            document.getElementById("itemsContainer").appendChild(templateRow);

            updateTotal();

            itemSelect.selectedIndex = 0;
            quantityInput.value = "";
        }

        function removeRow(button) {
            button.closest("tr").remove();
            updateTotal();
        }

        function updateTotal() {
            let total = 0;
            document.querySelectorAll("#itemsContainer .itemTotal").forEach(cell => {
                total += parseFloat(cell.textContent);
            });
            document.getElementById("grandTotal").textContent = total.toFixed(2);
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
            <option value="<%= c.getAccountNumber() %>"><%= c.getName() %> - <%= c.getTelephone() %></option>
            <% } } %>
        </select>

        <label for="item">Select Item:</label>
        <select id="item">
            <option value="">-- Select Item --</option>
            <% if (items != null) {
                for (Item i : items) { %>
            <option value="<%= i.getItem_id() %>" data-price="<%= i.getUnit_price() %>">
                <%= i.getItem_name() %> (Rs. <%= i.getUnit_price() %>)
            </option>
            <% } } %>
        </select>

        <label for="quantity">Quantity:</label>
        <input type="number" id="quantities" min="1">

        <button type="button" class="btn-add" onclick="addItem()">+ Add Item to Bill</button>

        <table>
            <thead>
            <tr>
                <th>Item Name</th>
                <th>Qty</th>
                <th>Price (Rs.)</th>
                <th>Subtotal (Rs.)</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody id="itemsContainer"></tbody>
            <tfoot>
            <tr id="itemRowTemplate" style="display:none">
                <td class="itemName"></td>
                <td class="itemQty"></td>
                <td class="itemPrice"></td>
                <td class="itemTotal"></td>
                <td>
                    <input type="hidden" class="itemIdInput" name="itemIds">
                    <input type="hidden" class="itemQtyInput" name="quantities">
                    <input type="hidden" class="itemPriceInput" name="prices">
                    <button type="button" class="btn-remove" onclick="removeRow(this)">Remove</button>
                </td>
            </tr>
            <tr class="total-row">
                <td colspan="3" style="text-align:right;">Grand Total (Rs.)</td>
                <td id="grandTotal">0.00</td>
                <td></td>
            </tr>
            </tfoot>
        </table>

        <input type="submit" value="Submit Bill">
    </form>
</div>

</body>
</html>
