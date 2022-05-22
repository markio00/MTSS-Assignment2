package it.unipd.mtss.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.ItemType;
import it.unipd.mtss.model.Order;
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
        // Arrange
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
    
    @Test
    public void testGetOrderPrice_Discount10percentIfTotalOver1000() {
        // Arrange
        list.add(new EItem(ItemType.Processor, "R9 5950X", 500.00));
        list.add(new EItem(ItemType.Processor, "i9 12900K", 500.00));
        
        try {
            // Act
            double price = bill.getOrderPrice(list, user);
            
            // Assert
            assertEquals(1000.00, price, 0);
            
        } catch(BillException ex) {
            fail();
        }     
    }
    
    @Test
    public void testGetOrderPrice_NoDiscount10percentIfTotalNotOver1000() {
        // Arrange
        list.add(new EItem(ItemType.Processor, "R9 5950X", 500.00));
        list.add(new EItem(ItemType.Processor, "i9 12900K", 500.00));
        list.add(new EItem(ItemType.Mouse, "Rotellina", 0.01));
        
        try {  
            // Act
            double price = bill.getOrderPrice(list, user);      
            
            // Assert   
            assertEquals(900.009, price, 0);
            
        } catch(BillException ex) {
            fail();
        }     
    }

    @Test
    public void testGetOrderPrice_NoErrorIfLessThan31Items() {
        // Arrange
        list.add(new EItem(ItemType.Motherboard, "article1", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article2", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article3", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article4", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article5", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article6", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article7", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article8", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article9", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article10", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article11", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article12", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article13", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article14", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article15", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article16", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article17", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article18", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article19", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article20", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article21", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article22", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article23", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article24", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article25", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article26", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article27", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article28", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article29", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article30", 1.00));

        try {  
            // Act
            double price = bill.getOrderPrice(list, user);      
            
            // Assert   
            assertEquals(30.00, price, 0);
            
        } catch(BillException ex) {
            fail();
        }   
    }

    @Test(expected = BillException.class)
    public void testGetOrderPrice_ErrorIfMoreThan30Items() throws BillException{
        // Arrange
        list.add(new EItem(ItemType.Motherboard, "article1", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article2", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article3", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article4", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article5", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article6", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article7", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article8", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article9", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article10", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article11", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article12", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article13", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article14", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article15", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article16", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article17", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article18", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article19", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article20", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article21", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article22", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article23", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article24", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article25", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article26", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article27", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article28", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article29", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article30", 1.00));
        list.add(new EItem(ItemType.Motherboard, "article31", 1.00));

        // Act        
        bill.getOrderPrice(list, user);
        
        // Assert
        fail();
    }

    @Test
    public void testGetOrderPrice_2EuroCommissionIfLessThan10EuroTotal() {

        this.list.add(new EItem(ItemType.Processor, "8086", 9.99));
        
        try {
            
            double price = bill.getOrderPrice(list, user);         
            assertEquals(11.99, price, 0);
            
        } catch(BillException ex) {
            fail();
        }     
    }

    @Test
    public void testGetOrderPrice_No2EuroCommissionIfNotLess10EuroTotal() {

        this.list.add(new EItem(ItemType.Processor, "8086", 9.99));
        this.list.add(new EItem(ItemType.Processor, "Fetta di processore", 0.01));
            
        try {

            double price = bill.getOrderPrice(list, user); 
            assertEquals(10.00, price, 0);
            
        } catch(BillException ex) {
            fail();
        }     
    }

    @Test(expected= NullPointerException.class)
    public void testOrdersGiftLottery_nullOrders() {

        this.bill.ordersGiftLottery(null).size();

        fail();
    }


    @Test
    public void testOrdersGiftLottery_noOrders() {

        int nGifts = this.bill.ordersGiftLottery(new Vector<Order>()).size();

        assertEquals(0, nGifts);
    }


    @Test(expected= NullPointerException.class)
    public void testOrdersGiftLottery_someNullOrders() {

        User underageUser = new User("under", "Under", "Age", LocalDate.of(2020, 01, 01));

        LocalTime inTime = LocalTime.of(18, 30);
        LocalTime notInTime = LocalTime.of(10, 00);

        EItem product = new EItem(ItemType.Processor, "Elder SKU", 10.55);

        Vector<Order> orders = new Vector<Order>();
        orders.add(new Order(underageUser, inTime, new Vector<EItem>(Arrays.asList(product))));
        orders.add(null);
        orders.add(null);
        orders.add(new Order(underageUser, notInTime, new Vector<EItem>(Arrays.asList(product))));

        this.bill.ordersGiftLottery(orders).size();

        fail();
    }

    @Test
    public void testOrdersGiftLottery_underageNotInTimeOrder() {

        User underageUser1 = new User("under1", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser2 = new User("under2", "Under", "Age", LocalDate.of(2020, 01, 01));

        EItem product = new EItem(ItemType.Processor, "Elder SKU", 10.55);

        LocalTime earlyTime = LocalTime.of(17, 59, 59);
        LocalTime lateTime = LocalTime.of(19, 00, 1);

        Vector<Order> orders = new Vector<Order>();
        orders.add(new Order(underageUser1, earlyTime, new Vector<EItem>(Arrays.asList(product))));   // FAIL: too early
        orders.add(new Order(underageUser2, lateTime, new Vector<EItem>(Arrays.asList(product))));    // FAIL: too late

        int nGifts = this.bill.ordersGiftLottery(orders).size();

        assertEquals(0, nGifts);
    }

    @Test
    public void testOrdersGiftLottery_underageInTimeOrder() {
        User underageUser1 = new User("under1", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser2 = new User("under2", "Under", "Age", LocalDate.of(2020, 01, 01));

        EItem product = new EItem(ItemType.Processor, "Elder SKU", 10.55);

        LocalTime lowerBoudTime = LocalTime.of(18, 00);
        LocalTime upperBoundTime = LocalTime.of(19, 00);

        Vector<Order> orders = new Vector<Order>();
        orders.add(new Order(underageUser1, lowerBoudTime, new Vector<EItem>(Arrays.asList(product))));
        orders.add(new Order(underageUser2, upperBoundTime, new Vector<EItem>(Arrays.asList(product))));

        int nGifts = this.bill.ordersGiftLottery(orders).size();

        assertEquals(2, nGifts);
    }


    @Test
    public void testOrdersGiftLottery_noGiftableOrders() {

        User underageUser = new User("under", "Under", "Age", LocalDate.of(2020, 01, 01));
        User overageUser = new User("over", "Over", "Age", LocalDate.of(2000, 01, 01));

        LocalTime inTime = LocalTime.of(18, 30);
        LocalTime notInTime = LocalTime.of(10, 00);

        EItem product = new EItem(ItemType.Processor, "Elder SKU", 10.55);

        Vector<Order> orders = new Vector<Order>();
        orders.add(new Order(underageUser, notInTime, new Vector<EItem>(Arrays.asList(product))));  // FAIL: underage user and order not in time
        orders.add(new Order(overageUser, inTime, new Vector<EItem>(Arrays.asList(product))));      // FAIL: overage user and order in time
        orders.add(new Order(overageUser, notInTime, new Vector<EItem>(Arrays.asList(product))));   // FAIL: overage user and order not in time

        int nGifts = this.bill.ordersGiftLottery(orders).size();

        assertEquals(0, nGifts);
    }

    @Test
    public void testOrdersGiftLottery_noSameUseGift() {

        User underageUser = new User("under", "Under", "Age", LocalDate.of(2020, 01, 01));

        LocalTime inTime = LocalTime.of(18, 30);

        EItem product = new EItem(ItemType.Processor, "Elder SKU", 10.55);

        Vector<Order> orders = new Vector<Order>();
        orders.add(new Order(underageUser, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser, inTime, new Vector<EItem>(Arrays.asList(product))));     // FAIL: underage user and order in time but unly one gift allowed per user

        int nGifts = this.bill.ordersGiftLottery(orders).size();

        assertEquals(1, nGifts);
    }


    @Test
    public void testOrdersGiftLottery_olyGiftableOrders() {

        User underageUser1 = new User("under1", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser2 = new User("under2", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser3 = new User("under3", "Under", "Age", LocalDate.of(2020, 01, 01));

        LocalTime inTime = LocalTime.of(18, 30);

        EItem product = new EItem(ItemType.Processor, "Elder SKU", 10.55);

        Vector<Order> orders = new Vector<Order>();
        orders.add(new Order(underageUser1, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser2, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser3, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time

        int nGifts = this.bill.ordersGiftLottery(orders).size();

        assertEquals(3, nGifts);
    }

    @Test
    public void testOrdersGiftLottery_lessThan10GiftableOrdersWithDifferentUsers() {

        User overageUser1 = new User("over1", "Over", "Age", LocalDate.of(2000, 01, 01));
        User overageUser2 = new User("over2", "Over", "Age", LocalDate.of(2000, 01, 01));
        User underageUser1 = new User("under1", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser2 = new User("under2", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser3 = new User("under3", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser4 = new User("under4", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser5 = new User("under5", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser6 = new User("under6", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser7 = new User("under7", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser8 = new User("under8", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser9 = new User("under9", "Under", "Age", LocalDate.of(2020, 01, 01));

        LocalTime inTime = LocalTime.of(18, 30);
        LocalTime notInTime = LocalTime.of(18, 30);

        EItem product = new EItem(ItemType.Processor, "Elder SKU", 10.55);

        Vector<Order> orders = new Vector<Order>();
        orders.add(new Order(overageUser1, inTime, new Vector<EItem>(Arrays.asList(product))));      // FAIL: overage user and order in time
        orders.add(new Order(overageUser2, notInTime, new Vector<EItem>(Arrays.asList(product))));   // FAIL: overage user and order not in time
        orders.add(new Order(underageUser1, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser2, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser3, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser4, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser5, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser6, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser7, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser8, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser9, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time

        int nGifts = this.bill.ordersGiftLottery(orders).size();

        assertEquals(9, nGifts);
        
    }

    @Test
    public void testOrdersGiftLottery_10GiftableOrdersWithDifferentUsers() {

        User overageUser1 = new User("over1", "Over", "Age", LocalDate.of(2000, 01, 01));
        User overageUser2 = new User("over2", "Over", "Age", LocalDate.of(2000, 01, 01));
        User underageUser1 = new User("under1", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser2 = new User("under2", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser3 = new User("under3", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser4 = new User("under4", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser5 = new User("under5", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser6 = new User("under6", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser7 = new User("under7", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser8 = new User("under8", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser9 = new User("under9", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser10 = new User("under10", "Under", "Age", LocalDate.of(2020, 01, 01));

        LocalTime inTime = LocalTime.of(18, 30);
        LocalTime notInTime = LocalTime.of(18, 30);

        EItem product = new EItem(ItemType.Processor, "Elder SKU", 10.55);

        Vector<Order> orders = new Vector<Order>();
        orders.add(new Order(overageUser1, inTime, new Vector<EItem>(Arrays.asList(product))));      // FAIL: overage user and order in time
        orders.add(new Order(overageUser2, notInTime, new Vector<EItem>(Arrays.asList(product))));   // FAIL: overage user and order not in time
        orders.add(new Order(underageUser1, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser2, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser3, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser4, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser5, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser6, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser7, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser8, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser9, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser10, inTime, new Vector<EItem>(Arrays.asList(product))));    // OK:   underage user and order in time

        int nGifts = this.bill.ordersGiftLottery(orders).size();

        assertEquals(10, nGifts);
    }

    @Test
    public void testOrdersGiftLottery_moreThan10GiftableOrdersWithDifferentUsers() {

        User overageUser1 = new User("over1", "Over", "Age", LocalDate.of(2000, 01, 01));
        User overageUser2 = new User("over2", "Over", "Age", LocalDate.of(2000, 01, 01));
        User underageUser1 = new User("under1", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser2 = new User("under2", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser3 = new User("under3", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser4 = new User("under4", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser5 = new User("under5", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser6 = new User("under6", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser7 = new User("under7", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser8 = new User("under8", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser9 = new User("under9", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser10 = new User("under10", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser11 = new User("under11", "Under", "Age", LocalDate.of(2020, 01, 01));

        LocalTime inTime = LocalTime.of(18, 30);
        LocalTime notInTime = LocalTime.of(18, 30);

        EItem product = new EItem(ItemType.Processor, "Elder SKU", 10.55);

        Vector<Order> orders = new Vector<Order>();
        orders.add(new Order(overageUser1, inTime, new Vector<EItem>(Arrays.asList(product))));      // FAIL: overage user and order in time
        orders.add(new Order(overageUser2, notInTime, new Vector<EItem>(Arrays.asList(product))));   // FAIL: overage user and order not in time
        orders.add(new Order(underageUser1, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser2, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser3, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser4, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser5, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser6, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser7, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser8, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser9, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser10, inTime, new Vector<EItem>(Arrays.asList(product))));    // OK:   underage user and order in time
        orders.add(new Order(underageUser11, inTime, new Vector<EItem>(Arrays.asList(product))));    // OK:   underage user and order in time

        int nGifts = this.bill.ordersGiftLottery(orders).size();

        assertEquals(10, nGifts);
    }


/*

    meno di 10 ordini regalabili con utenti diversi
    10 ordini regalabili con utenti diversi
    più di 10 ordini regalabili con utenti diversi

    meno di 10 ordini regalabili con utenti uguali
    10 ordini regalabili con utenti uguali
    più di 10 ordini regalabili con utenti uguali
    10 regali effettuati con utenti uguali

    nessun ordine regalabile
    nessun ordine
    solo ordini regalabili
    lista nulla
    qualche ordine nullo

giusto tempo



*/

    @Test
    public void testOrdersGiftLottery_10GiftableOrdersWithEqualUsers() {

        User overageUser1 = new User("over1", "Over", "Age", LocalDate.of(2000, 01, 01));
        User overageUser2 = new User("over2", "Over", "Age", LocalDate.of(2000, 01, 01));
        User underageUser1 = new User("under1", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser2 = new User("under2", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser3 = new User("under3", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser4 = new User("under4", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser5 = new User("under5", "Under", "Age", LocalDate.of(2020, 01, 01));

        LocalTime inTime = LocalTime.of(18, 30);
        LocalTime notInTime = LocalTime.of(18, 30);

        EItem product = new EItem(ItemType.Processor, "Elder SKU", 10.55);

        Vector<Order> orders = new Vector<Order>();
        orders.add(new Order(overageUser1, inTime, new Vector<EItem>(Arrays.asList(product))));      // FAIL: overage user and order in time
        orders.add(new Order(overageUser2, notInTime, new Vector<EItem>(Arrays.asList(product))));   // FAIL: overage user and order not in time
        orders.add(new Order(underageUser1, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser2, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser3, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser4, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser5, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser1, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser2, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser3, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser4, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser5, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time

        int nGifts = this.bill.ordersGiftLottery(orders).size();

        assertEquals(5, nGifts);
    }

    @Test
    public void testOrdersGiftLottery_10GiftedOrdersWithEqualUsers() {

        User overageUser1 = new User("over1", "Over", "Age", LocalDate.of(2000, 01, 01));
        User overageUser2 = new User("over2", "Over", "Age", LocalDate.of(2000, 01, 01));
        User underageUser1 = new User("under1", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser2 = new User("under2", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser3 = new User("under3", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser4 = new User("under4", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser5 = new User("under5", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser6 = new User("under6", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser7 = new User("under7", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser8 = new User("under8", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser9 = new User("under9", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser10 = new User("under10", "Under", "Age", LocalDate.of(2020, 01, 01));
        User underageUser11 = new User("under11", "Under", "Age", LocalDate.of(2020, 01, 01));

        LocalTime inTime = LocalTime.of(18, 30);
        LocalTime notInTime = LocalTime.of(18, 30);

        EItem product = new EItem(ItemType.Processor, "Elder SKU", 10.55);

        Vector<Order> orders = new Vector<Order>();
        orders.add(new Order(overageUser1, inTime, new Vector<EItem>(Arrays.asList(product))));      // FAIL: overage user and order in time
        orders.add(new Order(overageUser2, notInTime, new Vector<EItem>(Arrays.asList(product))));   // FAIL: overage user and order not in time
        orders.add(new Order(underageUser1, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser2, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser3, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser4, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser5, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser6, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser7, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser8, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser9, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser10, inTime, new Vector<EItem>(Arrays.asList(product))));    // OK:   underage user and order in time
        orders.add(new Order(underageUser11, inTime, new Vector<EItem>(Arrays.asList(product))));    // OK:   underage user and order in time
        orders.add(new Order(underageUser1, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser2, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser3, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time
        orders.add(new Order(underageUser4, inTime, new Vector<EItem>(Arrays.asList(product))));     // OK:   underage user and order in time

        int nGifts = this.bill.ordersGiftLottery(orders).size();

        assertEquals(10, nGifts);
    }

}
