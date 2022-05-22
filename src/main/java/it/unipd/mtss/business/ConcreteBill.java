////////////////////////////////////////////////////////////////////
// Marco Marchiante 1222397
// Michele Bettin 1216735
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import java.util.List;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;

import it.unipd.mtss.model.User;

public class ConcreteBill implements Bill {
    
    public double getOrderPrice(final List<EItem> itemsOrdered,
    final User user) throws BillException {
        
        if (user == null) {
            throw new BillException("Utente nullo");
        }
        if (itemsOrdered == null) {
            throw new BillException("Lista nulla");
        }
        
        double tot = 0;
        
        for (final EItem item : itemsOrdered) {
            tot += item.getPrice();
        }
        
        return tot;
    }
    
}