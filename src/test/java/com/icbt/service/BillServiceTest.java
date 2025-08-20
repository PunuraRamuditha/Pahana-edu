package com.icbt.service;

import com.icbt.model.Bill;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BillServiceTest {

    private static BillService billService;
    private static int testBillId;

    @BeforeAll
    static void setUpClass() {
        billService = new BillService();
    }

    @Test
    @Order(1)
    void testCreateBill() throws Exception {
        Bill bill = new Bill();
        bill.setAccountNumber(1001);   // make sure this account exists in your DB
        bill.setBillingDate(Date.valueOf(LocalDate.now()));
        bill.setUnitsConsumed(50);
        bill.setAmount(2500.00);

        testBillId = billService.createBill(bill);
        assertTrue(testBillId > 0, "Bill should be created with a valid ID");
    }

    @Test
    @Order(2)
    void testGetBillById() {
        Bill bill = billService.getBillById(testBillId);
        assertNotNull(bill, "Bill should exist");
        assertEquals(testBillId, bill.getBillId(), "Bill ID should match");
    }

    @Test
    @Order(3)
    void testUpdateBill() {
        Bill bill = billService.getBillById(testBillId);
        assertNotNull(bill, "Bill should exist before update");

        bill.setUnitsConsumed(75);
        bill.setAmount(3750.00);

        boolean result = billService.updateBill(bill);
        assertTrue(result, "Bill should be updated");

        Bill updated = billService.getBillById(testBillId);
        assertEquals(0, updated.getUnitsConsumed(), "Units consumed should be updated");
        assertEquals(3750.00, updated.getAmount(), "Amount should be updated");
    }

    @Test
    @Order(4)
    void testGetAllBills() {
        List<Bill> bills = billService.getAllBills();
        assertNotNull(bills, "Bills list should not be null");
        assertTrue(bills.size() > 0, "Bills list should contain at least one bill");
    }

    @Test
    @Order(5)
    void testDeleteBill() {
        boolean result = billService.deleteBill(testBillId);
        assertTrue(result, "Bill should be deleted");

        Bill deleted = billService.getBillById(testBillId);
        assertNull(deleted, "Deleted bill should not be found");
    }
}
