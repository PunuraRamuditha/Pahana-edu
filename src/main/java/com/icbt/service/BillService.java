package com.icbt.service;

import com.icbt.dao.BillDAO;
import com.icbt.model.Bill;

import java.util.List;

public class BillService {
    private BillDAO billDAO = new BillDAO();

    public int createBill(Bill bill) throws Exception {
        return billDAO.saveBill(bill);
    }
    public int generateBill(int accountNumber, double totalAmount) {
        Bill bill = new Bill();
        bill.setAccountNumber(accountNumber);
        bill.setAmount(totalAmount);
        return billDAO.addBill(bill);
    }

    public boolean updateBill(Bill bill) {
        return billDAO.updateBill(bill);
    }

    public boolean deleteBill(int billId) {
        return billDAO.deleteBill(billId);
    }

    public Bill getBillById(int billId) {
        return billDAO.getBillById(billId);
    }

    public List<Bill> getAllBills() {
        return billDAO.getAllBills();
    }
}
