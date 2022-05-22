////////////////////////////////////////////////////////////////////
// Marco Marchiante 1222397
// Michele Bettin 1216735
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import java.util.List;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.ItemType;
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
        
        return getTotalPrice(itemsOrdered) -
            getDiscount5Processors(itemsOrdered);
    }
    
    
    private double getTotalPrice(final List<EItem> itemsOrdered) {
        
        double tot = 0;
        
        for(final EItem item : itemsOrdered) {
            tot += item.getPrice();
        }
        
        return tot;
    }
    
    
    private double getDiscount5Processors(final List<EItem> itemsOrdered) {
        int processorsFound = 0;
        double minPrice = Double.MAX_VALUE;
        
        for(final EItem item : itemsOrdered){
            if(item.getItemType() == ItemType.Processor){
                minPrice = Math.min(minPrice, item.getPrice());
                ++processorsFound;
            }
        }
        
        if(processorsFound > 5){
            return minPrice/2;
        }
        
        return 0;
    }

}