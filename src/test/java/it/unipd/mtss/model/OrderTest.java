package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

public class OrderTest {

    private Order order;
    private User user;

    @Before
    public void setup() {

        this.user = new User("#", "Mario", "Rossi", LocalDate.now());

        this.order = new Order(
            this.user,
            LocalTime.of(0, 0),
            new Vector<EItem>()
        );

    }

    @Test
    public void testGetOrderTIme() {
        assertEquals(LocalTime.of(0, 0), this.order.getOrderTIme());
    }

    @Test
    public void testGetOrderedItems() {
        assertEquals(new Vector<EItem>(), this.order.getOrderedItems());

    }

    @Test
    public void testGetUser() {
        assertEquals(this.user, this.order.getUser());
    }

    @Test
    public void testGift() {
        this.order.gift();

        assertTrue(this.order.hasBeenGifted());
    }

    @Test
    public void testHasBeenGifted() {
        assertFalse(this.order.hasBeenGifted());
    }
}
