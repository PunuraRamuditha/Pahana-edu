package com.icbt.model;

public class BillItem {
    private int billId;
    private String itemCode;
    private int quantity;

    // Getters and setters
    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

    public String getItemCode() { return itemCode; }
    public void setItemCode(String itemCode) { this.itemCode = itemCode; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
