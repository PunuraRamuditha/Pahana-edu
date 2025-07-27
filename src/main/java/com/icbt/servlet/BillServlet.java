package com.icbt.servlet;

import com.icbt.model.Bill;
import com.icbt.model.BillItem;
import com.icbt.service.BillService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/BillServlet")
public class BillServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("customer");
        String[] itemCodes = request.getParameterValues("itemCodes");
        String[] quantities = request.getParameterValues("quantities");

        List<BillItem> items = new ArrayList<>();
        if (itemCodes != null && quantities != null) {
            for (int i = 0; i < itemCodes.length; i++) {
                BillItem item = new BillItem();
                item.setItemCode(itemCodes[i]);
                item.setQuantity(Integer.parseInt(quantities[i]));
                items.add(item);
            }
        }

        Bill bill = new Bill();
        bill.setAccountNumber(accountNumber);
        bill.setItems(items);

        try {
            int billId = BillService.saveBill(bill);
            request.setAttribute("message", "Bill created with ID: " + billId);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Error creating bill.");
        }

        request.getRequestDispatcher("bill-success.jsp").forward(request, response);
    }
}
