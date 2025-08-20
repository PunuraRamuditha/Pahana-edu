package com.icbt.service;

import com.icbt.model.User;
import com.icbt.util.DBConnection;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    private UserService userService;

    @BeforeAll
    void setUp() throws SQLException {
        userService = new UserService();

        // Insert a known test user
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO users (id, username, password) VALUES (?, ?, ?)")) {
            stmt.setInt(1, 9999); // test user ID
            stmt.setString(2, "testuser");
            stmt.setString(3, "testpass");
            stmt.executeUpdate();
        }
    }

    @AfterAll
    void tearDown() throws SQLException {
        // Clean up test user
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM users WHERE id = ?")) {
            stmt.setInt(1, 9999);
            stmt.executeUpdate();
        }
    }

    @Test
    void testLoginWithValidUser() {
        User user = userService.login("testuser", "testpass");

        assertNotNull(user, "User should not be null for valid credentials");
        assertEquals("testuser", user.getUsername(), "Username should match");
    }

    @Test
    void testLoginWithInvalidUser() {
        User user = userService.login("wrong", "wrong");

        assertNull(user, "User should be null for invalid credentials");
    }
}
