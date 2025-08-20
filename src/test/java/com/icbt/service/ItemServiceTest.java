package com.icbt.service;

import com.icbt.model.Item;
import com.icbt.util.DBConnection;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ItemServiceTest {

    private ItemService itemService;
    private int testItemId;

    @BeforeAll
    void setUp() throws SQLException {
        itemService = new ItemService();

        // Insert a test item directly into DB
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO items (item_name, item_description, unit_price, stock_quantity) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, "Test Item");
            stmt.setString(2, "Test Description");
            stmt.setString(3, "100.50");
            stmt.setString(4, "10");
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    testItemId = rs.getInt(1); // capture generated id
                }
            }
        }
    }

    @AfterAll
    void tearDown() throws SQLException {
        if (testItemId > 0) {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "DELETE FROM items WHERE item_id = ?")) {
                stmt.setInt(1, testItemId);
                stmt.executeUpdate();
            }
        }
    }

    @Test
    void testRegisterItem() {
        Item newItem = new Item("Laptop", "Gaming Laptop", "2500.00", "5");

        boolean result = itemService.registerItem(newItem);

        assertTrue(result, "Item should be registered successfully");
    }

    @Test
    void testUpdateItem() {
        Item item = new Item();
        item.setItem_id(testItemId);
        item.setItem_name("Updated Item");
        item.setItem_description("Updated Description");
        item.setUnit_price("200.00");
        item.setStock_quantity("15");

        boolean updated = itemService.updateItem(item);

        assertTrue(updated, "Item update should be successful");

        // Verify directly via service
        List<Item> items = itemService.getAllItems();
        Item updatedItem = items.stream()
                .filter(i -> i.getItem_id() == testItemId)
                .findFirst()
                .orElse(null);

        assertNotNull(updatedItem, "Updated item should exist");
        assertEquals("Updated Item", updatedItem.getItem_name());
        assertEquals("Updated Description", updatedItem.getItem_description());
        assertEquals("200.00", updatedItem.getUnit_price());
        assertEquals("15", updatedItem.getStock_quantity());
    }

    @Test
    void testGetAllItems() {
        List<Item> items = itemService.getAllItems();
        assertNotNull(items, "Items list should not be null");
        assertFalse(items.isEmpty(), "Items list should not be empty");
    }
}
