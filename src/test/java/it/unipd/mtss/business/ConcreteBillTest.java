package it.unipd.mtss.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.ItemType;
import it.unipd.mtss.model.User;

public class ConcreteBillTest {
    
    private ConcreteBill bill;
    private User user;
    private Vector<EItem> list;
    
    @Before
    public void setupVariables() {
        this.bill = new ConcreteBill();
        this.list = new Vector<EItem>();
        this.user = new User("user1", "Mario", "Rossi", LocalDate.of(2000, 04, 19));
        
    }
    
    @Test
    public void testGetOrderPrice_EmptyList() {
        try {
            double price = bill.getOrderPrice(list, user);
            assertEquals(0, price, 0);
            
        } catch (BillException ex) {
            fail();
        }
    }
    
    @Test(expected = BillException.class)
    public void testGetOrderPrice_NullList() throws BillException {
        bill.getOrderPrice(null, user);
        fail();
    }
    
    @Test(expected = BillException.class)
    public void testGetOrderPrice_NullUser() throws BillException {
        bill.getOrderPrice(list, null);
        fail();
    }
    
    @Test
    public void testGetOrderPrice_GeneralCase() {
        list.add(new EItem(ItemType.Keyboard, "K70 MX Brown", 130.00));
        list.add(new EItem(ItemType.Processor, "Athlon", 45.99));
        
        try {
            double price = bill.getOrderPrice(list, user);
            assertEquals(175.99, price, 0);
            
        } catch (BillException ex) {
            fail();
        }
    }
    
    @Test
    public void testGetOrderPrice_50percentDiscountOnCheapestProcessorIfMoreThan5() {
        // Arrange
        list.add(new EItem(ItemType.Keyboard, "K70 MX Brown", 5.00));
        list.add(new EItem(ItemType.Mouse, "Athlon", 7.00));
        list.add(new EItem(ItemType.Motherboard, "X550 Pro M", 25.00));
        list.add(new EItem(ItemType.Processor, "cpu1", 15.00));
        list.add(new EItem(ItemType.Processor, "cpu2", 14.00));
        list.add(new EItem(ItemType.Processor, "cpu3", 13.00));
        list.add(new EItem(ItemType.Processor, "cpu4", 12.00));
        list.add(new EItem(ItemType.Processor, "cpu5", 11.00));
        list.add(new EItem(ItemType.Processor, "cpu6", 10.00));
        
        try {
            // Act
            double price = bill.getOrderPrice(list, user);
            
            // Assert
            assertEquals(107.00, price, 0);
            
        } catch (BillException ex) {
            fail();
        }
        
    }
    
    @Test
    public void testGetOrderPrice_No50percentDiscountOnCheapestProcessorIfLessThan6() {
        // Arrange
        list.add(new EItem(ItemType.Keyboard, "K70 MX Brown", 5.00));
        list.add(new EItem(ItemType.Mouse, "Athlon", 7.00));
        list.add(new EItem(ItemType.Motherboard, "X550 Pro M", 25.00));
        list.add(new EItem(ItemType.Processor, "cpu1", 15.00));
        list.add(new EItem(ItemType.Processor, "cpu2", 14.00));
        list.add(new EItem(ItemType.Processor, "cpu3", 13.00));
        list.add(new EItem(ItemType.Processor, "cpu4", 12.00));
        list.add(new EItem(ItemType.Processor, "cpu5", 11.00));
        
        try {
            // Act
            double price = bill.getOrderPrice(list, user);
            
            // Assert
            assertEquals(102.00, price, 0);
            
        } catch (BillException ex) {
            fail();
        }
    }
    
    @Test
    public void testGetOrderPrice_NoGiftCheapestMouseIfLessThan11() {
        
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 8.00));
        list.add(new EItem(ItemType.Processor, "Elder SKU", 5.00));
        
        try {
            double price = bill.getOrderPrice(list, user);
            assertEquals(103.00, price, 0);
            
        } catch (BillException ex) {
            fail();
        }
    }
    
    @Test
    public void testGetOrderPrice_GiftCheapestMouseIfMoreThan10() {
        
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        list.add(new EItem(ItemType.Mouse, "G502", 8.00));
        list.add(new EItem(ItemType.Processor, "Elder SKU", 5.00));
        list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        
        try {
            
            double price = bill.getOrderPrice(list, user);
            assertEquals(105.00, price, 0);
            
        } catch (BillException ex) {
            fail();
        }
    }
    
}
