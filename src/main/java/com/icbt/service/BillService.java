package com.icbt.service;

import com.icbt.dao.BillDAO;
import com.icbt.model.Bill;

public class BillService {
    public static int saveBill(Bill bill) throws Exception {
        return BillDAO.addBill(bill);
    }
}
