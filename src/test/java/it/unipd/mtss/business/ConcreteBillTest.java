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
        // Arrange
        bill = new ConcreteBill();
        list = new Vector<EItem>();
        user = new User("user1", "Mario", "Rossi", LocalDate.of(2000, 04, 19));
    }
    
    @Test
    public void testGetOrderPrice_EmptyList() {
        try {
            // Act
            double price = bill.getOrderPrice(list, user);

            // Assert
            assertEquals(0, price, 0);
            
        } catch (BillException ex) {
            fail();
        }
    }
    
    @Test(expected = BillException.class)
    public void testGetOrderPrice_NullList() throws BillException {
        // Act
        bill.getOrderPrice(null, user);

        // Assert
        fail();
    }
    
    @Test(expected = BillException.class)
    public void testGetOrderPrice_NullUser() throws BillException {
        // Act
        bill.getOrderPrice(list, null);

        // Assert
        fail();
    }
    
    @Test
    public void testGetOrderPrice_GeneralCase() {
        // Arrange
        list.add(new EItem(ItemType.Keyboard, "K70 MX Brown", 130.00));
        list.add(new EItem(ItemType.Processor, "Athlon", 45.99));
        
        try {
            // Act
            double price = bill.getOrderPrice(list, user);

            // Assert
            assertEquals(175.99, price, 0);
            
        } catch (BillException ex) {
            fail();
        }
    }
    
    @Test
    public void testGetOrderPrice_50percentDiscountOnCheapestProcessorIfMoreThan5() {
        // Arrange
        list.add(new EItem(ItemType.Keyboard, "K70 MX Brown", 5.00));
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
            assertEquals(112.00, price, 0);
            
        } catch (BillException ex) {
            fail();
        }
        
    }
    
    @Test
    public void testGetOrderPrice_No50percentDiscountOnCheapestProcessorIfLessThan6() {
        // Arrange
        list.add(new EItem(ItemType.Keyboard, "K70 MX Brown", 5.00));
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
            assertEquals(107.00, price, 0);
            
        } catch (BillException ex) {
            fail();
        }
    }
    
    @Test
    public void testGetOrderPrice_NoGiftCheapestMouseIfLessThan11() {
        // Arrange
        list.add(new EItem(ItemType.Mouse, "mouse1", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse2", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse3", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse4", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse5", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse6", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse7", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse8", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse9", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse10", 8.00));
        list.add(new EItem(ItemType.Processor, "Elder SKU", 5.00));
        
        try {
            // Act
            double price = bill.getOrderPrice(list, user);

            // Assert
            assertEquals(103.00, price, 0);
            
        } catch (BillException ex) {
            fail();
        }
    }
    
    @Test
    public void testGetOrderPrice_GiftCheapestMouseIfMoreThan10() {
        // Arrange
        list.add(new EItem(ItemType.Mouse, "mouse1", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse2", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse3", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse4", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse5", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse6", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse7", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse8", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse9", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse10", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse11", 8.00));
        list.add(new EItem(ItemType.Processor, "Elder SKU", 5.00));
        
        try {
            //Act
            double price = bill.getOrderPrice(list, user);
            
            // Assert
            assertEquals(105.00, price, 0);
            
        } catch (BillException ex) {
            fail();
        }
    }

    @Test
    public void testGetOrderPrice_GiftCheapestKeyboardOrMouseIfSameNumber(){
        // Arrange
        list.add(new EItem(ItemType.Motherboard, "X550 Pro M", 27.00));
        list.add(new EItem(ItemType.Processor, "Elder SKU", 3.00));
        list.add(new EItem(ItemType.Mouse, "mouse1", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse2", 8.00));
        list.add(new EItem(ItemType.Keyboard, "keyboard1", 6.00));
        list.add(new EItem(ItemType.Keyboard, "keyboard2", 4.00));
        
        try {
            // Act
            double price = bill.getOrderPrice(list, user);

            // Assert
            assertEquals(54, price, 0);
            
        } catch (BillException x) {
            fail();
        }   
    }
    
    @Test
    public void testGetOrderPrice_NoGiftCheapestKeyboardOrMouseIfDifferentNumber(){
        // Arrange
        list.add(new EItem(ItemType.Motherboard, "X550 Pro M", 27.00));
        list.add(new EItem(ItemType.Processor, "Elder SKU", 3.00));
        list.add(new EItem(ItemType.Mouse, "mouse1", 12.00));
        list.add(new EItem(ItemType.Mouse, "mouse2", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse2", 8.00));
        list.add(new EItem(ItemType.Keyboard, "keyboard1", 6.00));
        list.add(new EItem(ItemType.Keyboard, "keyboard2", 4.00));
        
        try {
            // Act
            double price = bill.getOrderPrice(list, user);

            // Assert
            assertEquals(70, price, 0);
            
        } catch (BillException ex) {
            fail();
        }   
    }

    @Test
    public void testGetOrderPrice_GiftCheapestKeyboardIfAlreadyGiftedCheapestMouseOf10AndSameNumberOfBoth() {
        // ArrangeIt
        list.add(new EItem(ItemType.Motherboard, "X550 Pro M", 25.00));
        list.add(new EItem(ItemType.Processor, "Elder SKU", 5.00));
        list.add(new EItem(ItemType.Mouse, "mouse1", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse2", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse3", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse4", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse5", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse6", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse7", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse8", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse9", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse10", 10.00));
        list.add(new EItem(ItemType.Mouse, "mouse11", 8.00));
        list.add(new EItem(ItemType.Keyboard, "keyboard1", 10.00));
        list.add(new EItem(ItemType.Keyboard, "keyboard2", 10.00));
        list.add(new EItem(ItemType.Keyboard, "keyboard3", 10.00));
        list.add(new EItem(ItemType.Keyboard, "keyboard4", 10.00));
        list.add(new EItem(ItemType.Keyboard, "keyboard5", 10.00));
        list.add(new EItem(ItemType.Keyboard, "keyboard6", 10.00));
        list.add(new EItem(ItemType.Keyboard, "keyboard7", 10.00));
        list.add(new EItem(ItemType.Keyboard, "keyboard8", 10.00));
        list.add(new EItem(ItemType.Keyboard, "keyboard9", 10.00));
        list.add(new EItem(ItemType.Keyboard, "keyboard10", 10.00));
        list.add(new EItem(ItemType.Keyboard, "keyboard11", 6.00));

        try {
            // Act
            double price = bill.getOrderPrice(list, user);


            // Assert
            assertEquals(230, price, 0);
            
        } catch (BillException ex) {
            fail();
        } 
    }
}
