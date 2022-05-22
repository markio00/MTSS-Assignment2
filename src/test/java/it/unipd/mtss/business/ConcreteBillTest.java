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
            ex.printStackTrace();
            fail();
        }
    }
    
}
