package com.icbt.dao;

import com.icbt.model.BillItem;
import com.icbt.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillItemDAO {

    // Save multiple bill items for a bill
    public void addBillItems(List<BillItem> items, int billId) throws Exception {
        String sql = "INSERT INTO bill_items (bill_id, item_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (BillItem item : items) {
                ps.setInt(1, billId);
                ps.setInt(2, item.getItemId());
                ps.setInt(3, item.getQuantity());
                ps.setDouble(4, item.getUnitPrice());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    // Get all bill items for a specific bill
    public List<BillItem> getBillItemsByBillId(int billId) throws Exception {
        List<BillItem> billItems = new ArrayList<>();
        String sql = "SELECT * FROM bill_items WHERE bill_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, billId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BillItem item = new BillItem();
                item.setBillId(rs.getInt("bill_id"));
                item.setItemId(rs.getInt("item_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("unit_price"));
                billItems.add(item);
            }
        }
        return billItems;
    }

    // Update bill item
    public boolean updateBillItem(BillItem item) {
        String sql = "UPDATE bill_items SET item_id = ?, quantity = ?, unit_price = ? WHERE bill_item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getItemId());
            ps.setInt(2, item.getQuantity());
            ps.setDouble(3, item.getUnitPrice());
            ps.setInt(4, item.getBillId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete bill item
    public boolean deleteBillItem(int billItemId) {
        String sql = "DELETE FROM bill_items WHERE bill_item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, billItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all bill items (for admin view)
    public List<BillItem> getAllBillItems() {
        List<BillItem> billItems = new ArrayList<>();
        String sql = "SELECT * FROM bill_items";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                BillItem item = new BillItem();
                item.setBillId(rs.getInt("bill_item_id"));
                item.setBillId(rs.getInt("bill_id"));
                item.setItemId(rs.getInt("item_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("unit_price"));
                billItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billItems;
    }

    public boolean deleteBillItemsByBillId(int billId) {
        String sql = "DELETE FROM bill_items WHERE bill_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, billId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
