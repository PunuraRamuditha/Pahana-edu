package com.icbt.model;

import java.util.Date;
import java.util.List;

public class Bill {
    private int billId;                 // bill_id
    private int accountNumber;          // account_number
    private Date billingDate;           // billing_date
    private int unitsConsumed;          // units_consumed
    private double amount;              // amount (total bill amount)
    private List<BillItem> billItems;   // related items in bill_items table

    public Bill() {}

    public Bill(int billId, int accountNumber, Date billingDate, int unitsConsumed, double amount, List<BillItem> billItems) {
        this.billId = billId;
        this.accountNumber = accountNumber;
        this.billingDate = billingDate;
        this.unitsConsumed = unitsConsumed;
        this.amount = amount;
        this.billItems = billItems;
    }

    // Getters & Setters
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    public int getUnitsConsumed() {
        return unitsConsumed;
    }

    public void setUnitsConsumed(int unitsConsumed) {
        this.unitsConsumed = unitsConsumed;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<BillItem> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }
}
