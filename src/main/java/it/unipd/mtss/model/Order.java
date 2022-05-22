////////////////////////////////////////////////////////////////////
// Marco Marchiante 1222397
// Michele Bettin 1216735
////////////////////////////////////////////////////////////////////


package it.unipd.mtss.model;

import java.time.LocalTime;
import java.util.List;

public class Order{
    
    User user;
    LocalTime orderTIme;
    List<EItem> orderedItems;
    boolean gifted;


    public Order(User user, LocalTime time,
    List<EItem> orderedItems) {
        this.user = user;
        this.orderTIme = time;
        this.orderedItems = orderedItems;
        this.gifted = false;
    }


    public User getUser() {
        return user;
    }


    public LocalTime getOrderTIme() {
        return orderTIme;
    }


    public List<EItem> getOrderedItems() {
        return orderedItems;
    }


    public void gift() {
        this.gifted = true;
    }


    public boolean hasBeenGifted() {
        return this.gifted;
    }

    


}/*
@Test(timeout=100) //ms

@Test(expected=Exception.class)
public void test() {
    String data = "";
    bill.qualcosa(data);
    fail();
}

test<nomemetoido>_<casistica> 

        oppure 

test<nomemetodo> + assertMessage(<casistica>)

*/