package com.icbt.servlet;

import com.icbt.model.Bill;
import com.icbt.model.BillItem;
import com.icbt.model.Customer;
import com.icbt.model.Item;
import com.icbt.service.BillItemService;
import com.icbt.service.BillService;
import com.icbt.service.CustomerService;
import com.icbt.service.ItemService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/BillServlet")
public class BillServlet extends HttpServlet {
    private final BillService billService = new BillService();
    private final BillItemService billItemService = new BillItemService();
    private final CustomerService customerService = new CustomerService();
    private final ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action != null) {
                switch (action) {
                    case "new":
                        List<Item> items = itemService.getAllItems();
                        List<Customer> customers = customerService.getAllCustomers();
                        request.setAttribute("items", items);
                        request.setAttribute("customers", customers);
                        request.getRequestDispatcher("add-bill.jsp").forward(request, response);
                        break;

                    case "edit":
                        int editId = Integer.parseInt(request.getParameter("id"));
                        Bill billToEdit = billService.getBillById(editId);
                        List<BillItem> billItems = billItemService.getAllBillItems();
                        List<Item> allItems = itemService.getAllItems();
                        List<Customer> allCustomers = customerService.getAllCustomers();

                        request.setAttribute("bill", billToEdit);
                        request.setAttribute("billItems", billItems);
                        request.setAttribute("items", allItems);
                        request.setAttribute("customers", allCustomers);
                        request.getRequestDispatcher("edit-bill.jsp").forward(request, response);
                        break;

                    case "delete":
                        int deleteId = Integer.parseInt(request.getParameter("id"));
                        billItemService.deleteBillItemsByBillId(deleteId);
                        billService.deleteBill(deleteId);
                        response.sendRedirect("BillServlet");
                        break;
                    case "view":
                        int viewId = Integer.parseInt(request.getParameter("id"));
                        Bill billToView = billService.getBillById(viewId);
                        List<BillItem> itemsForBill = billItemService.getBillItemsByBillId(viewId);
                        Customer billCustomer = customerService.getCustomerById(billToView.getAccountNumber());

                        Map<Integer, Item> itemMap = new HashMap<>();
                        for (Item it : itemService.getAllItems()) {
                            itemMap.put(it.getItem_id(), it);
                        }

                        request.setAttribute("bill", billToView);
                        request.setAttribute("billItems", itemsForBill);
                        request.setAttribute("customer", billCustomer);
                        request.setAttribute("itemsMap", itemMap);

                        request.getRequestDispatcher("view-bill.jsp").forward(request, response);
                        break;

                    default:
                        response.sendRedirect("error.jsp");
                }
            } else {
                List<Bill> bills = billService.getAllBills();
                for (Bill bill : bills) {
                    List<BillItem> billItems = billItemService.getBillItemsByBillId(bill.getBillId());
                    bill.setBillItems(billItems);
                }
                request.setAttribute("bills", bills);
                request.setAttribute("customers", customerService.getAllCustomers());
                request.setAttribute("items", itemService.getAllItems());
                request.getRequestDispatcher("list-bills.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            String formAction = request.getParameter("formAction");

            int accountNumber = Integer.parseInt(request.getParameter("customer"));
            String[] itemIds = request.getParameterValues("itemIds");
            String[] quantities = request.getParameterValues("quantities");
            String[] prices = request.getParameterValues("prices");

            if (itemIds == null || quantities == null || prices == null
                    || itemIds.length != quantities.length || itemIds.length != prices.length) {
                response.sendRedirect("error.jsp");
                return;
            }

            List<BillItem> billItems = new ArrayList<>();
            double totalAmount = 0;

            for (int i = 0; i < itemIds.length-1; i++) {
                BillItem item = new BillItem();
                item.setItemId(Integer.parseInt(itemIds[i]));
                item.setQuantity(Integer.parseInt(quantities[i]));
                item.setUnitPrice(Double.parseDouble(prices[i]));
                totalAmount += item.getQuantity() * item.getUnitPrice();
                billItems.add(item);
            }

            if ("update".equals(formAction)) {
                int billId = Integer.parseInt(request.getParameter("billId"));
                Bill bill = new Bill();
                bill.setBillId(billId);
                bill.setAccountNumber(accountNumber);
                bill.setAmount(totalAmount);
                bill.setBillingDate(new Date());

                boolean updated = billService.updateBill(bill);
                if (updated) {
                    billItemService.getAllBillItems()
                            .forEach(item -> billItemService.deleteBillItem(item.getBillItemId()));

                    for (BillItem item : billItems) {
                        item.setBillId(billId);
                    }
                    billItemService.addBillItems(billItems, billId);

                    response.sendRedirect("BillServlet");
                } else {
                    response.sendRedirect("error.jsp");
                }
            } else {
                Bill bill = new Bill();
                bill.setAccountNumber(accountNumber);
                bill.setAmount(totalAmount);
                bill.setBillingDate(new Date());

                int billId = billService.createBill(bill);

                if (billId > 0) {
                    for (BillItem item : billItems) {
                        item.setBillId(billId);
                    }
                    billItemService.addBillItems(billItems, billId);

                    Bill newBill = billService.getBillById(billId);
                    List<BillItem> newBillItems = billItemService.getBillItemsByBillId(billId);
                    Customer customer = customerService.getCustomerById(accountNumber);
                    List<Item> items = itemService.getAllItems();

                    request.setAttribute("bill", newBill);
                    request.setAttribute("billItems", newBillItems);
                    request.setAttribute("customer", customer);
                    request.setAttribute("items", items);

                    response.sendRedirect("BillServlet");
                } else {
                    response.sendRedirect("error.jsp");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}


