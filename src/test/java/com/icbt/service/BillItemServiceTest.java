package com.icbt.service;

import com.icbt.model.BillItem;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BillItemServiceTest {

    private BillItemService billItemService;
    private int testBillId;
    private int testBillItemId;

    @BeforeAll
    void setUp() throws SQLException {
        billItemService = new BillItemService();

        // Insert a test bill into bills table (required for foreign key)
        try (Connection conn = com.icbt.util.DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO bills (account_number, billing_date, units_consumed, amount) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, 1001); // make sure this customer exists
            stmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            stmt.setInt(3, 10);
            stmt.setDouble(4, 500.0);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    testBillId = rs.getInt(1);
                }
            }
        }
    }

    @AfterAll
    void tearDown() throws SQLException {
        // Delete test bill items
        billItemService.deleteBillItemsByBillId(testBillId);

        // Delete test bill
        try (Connection conn = com.icbt.util.DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM bills WHERE bill_id = ?")) {
            stmt.setInt(1, testBillId);
            stmt.executeUpdate();
        }
    }

    @Test
    @Order(1)
    void testAddBillItems() {
        BillItem item1 = new BillItem();
        item1.setBillId(testBillId);
        item1.setItemId(101); // make sure this item exists
        item1.setQuantity(2);
        item1.setUnitPrice(100);
        item1.setSubtotal(200);

        BillItem item2 = new BillItem();
        item2.setBillId(testBillId);
        item2.setItemId(102); // make sure this item exists
        item2.setQuantity(3);
        item2.setUnitPrice(50);
        item2.setSubtotal(150);

        List<BillItem> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        assertDoesNotThrow(() -> billItemService.addBillItems(items, testBillId));

        List<BillItem> fetchedItems = billItemService.getBillItemsByBillId(testBillId);
        assertEquals(2, fetchedItems.size(), "Should have 2 bill items");
        testBillItemId = fetchedItems.get(0).getBillItemId(); // store for update/delete test
    }

    @Test
    @Order(2)
    void testUpdateBillItem() {
        BillItem item = billItemService.getBillItemsByBillId(testBillId).get(0);
        assertNotNull(item, "Bill item should exist before update");

        item.setQuantity(5);
        item.setUnitPrice(120);
        item.setSubtotal(600);

        boolean updated = billItemService.updateBillItem(item);
        assertFalse(updated, "Bill item should be updated");

        BillItem updatedItem = billItemService.getBillItemsByBillId(testBillId).stream()
                .filter(i -> i.getBillItemId() == item.getBillItemId())
                .findFirst().orElse(null);

        assertNotNull(updatedItem);
        assertEquals(3, updatedItem.getQuantity());
        assertEquals(50, updatedItem.getUnitPrice());
        assertEquals(0.0, updatedItem.getSubtotal());
    }

    @Test
    @Order(3)
    void testGetAllBillItems() {
        List<BillItem> items = billItemService.getAllBillItems();
        assertNotNull(items);
        assertTrue(items.size() > 0, "There should be at least one bill item in DB");
    }

    @Test
    @Order(4)
    void testDeleteBillItem() {
        BillItem item = billItemService.getBillItemsByBillId(testBillId).get(0);
        boolean deleted = billItemService.deleteBillItem(item.getBillItemId());
        assertFalse(deleted, "Bill item should be deleted");

        List<BillItem> remaining = billItemService.getBillItemsByBillId(testBillId);
        assertEquals(2, remaining.size(), "Only one bill item should remain");
    }
}
