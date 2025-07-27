package com.icbt.model;

import java.util.List;

public class Bill {
    private int billId;
    private String accountNumber;
    private List<BillItem> items;

    // Getters and setters
    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public List<BillItem> getItems() { return items; }
    public void setItems(List<BillItem> items) { this.items = items; }
}
