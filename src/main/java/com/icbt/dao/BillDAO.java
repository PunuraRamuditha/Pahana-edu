package com.icbt.dao;

import com.icbt.model.Bill;
import com.icbt.model.BillItem;
import com.icbt.util.DBConnection;

import java.sql.*;
import java.util.List;

public class BillDAO {

    public static int addBill(Bill bill) throws Exception {
        String insertBillSQL = "INSERT INTO bill(account_number) VALUES(?)";
        String insertBillItemSQL = "INSERT INTO bill_item(bill_id, item_code, quantity) VALUES (?, ?, ?)";

        Connection con = DBConnection.getConnection();
        PreparedStatement psBill = con.prepareStatement(insertBillSQL, Statement.RETURN_GENERATED_KEYS);

        psBill.setString(1, bill.getAccountNumber());
        psBill.executeUpdate();

        ResultSet rs = psBill.getGeneratedKeys();
        if (rs.next()) {
            int billId = rs.getInt(1);

            PreparedStatement psItem = con.prepareStatement(insertBillItemSQL);
            for (BillItem item : bill.getItems()) {
                psItem.setInt(1, billId);
                psItem.setString(2, item.getItemCode());
                psItem.setInt(3, item.getQuantity());
                psItem.addBatch();
            }
            psItem.executeBatch();

            return billId;
        }

        throw new Exception("Bill ID not generated");
    }
}
