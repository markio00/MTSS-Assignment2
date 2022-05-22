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

        double totalPrice = getTotalPrice(itemsOrdered);

        return totalPrice -
        getDiscount5Processors(itemsOrdered) -
        getDiscount10Mouse(itemsOrdered)-
        getDiscountKeyboardAndMouseQuantity(itemsOrdered) -
        get10PercentDiscountIfTotalOver1000(totalPrice);
    }
    
    private double getTotalPrice(final List<EItem> itemsOrdered) {
        
        double tot = 0;
        
        for (final EItem item : itemsOrdered) {
            tot += item.getPrice();
        }
        
        return tot;
    }
    
    private double getDiscount5Processors(final List<EItem> itemsOrdered) {
        int processorsFound = 0;
        double minPrice = Double.MAX_VALUE;
        
        for (final EItem item : itemsOrdered) {
            if (item.getItemType() == ItemType.Processor) {
                minPrice = Math.min(minPrice, item.getPrice());
                ++processorsFound;
            }
        }
        
        if (processorsFound > 5) {
            return minPrice / 2;
        }
        
        return 0;
    }
    
    private double getDiscount10Mouse(final List<EItem> itemsOrdered) {
        int mouseFound = 0;
        double minPrice = Double.MAX_VALUE;
        
        for (final EItem item : itemsOrdered) {
            if (item.getItemType() == ItemType.Mouse) {
                minPrice = Math.min(minPrice, item.getPrice());
                ++mouseFound;
            }
        }
        
        if (mouseFound > 10) {
            return minPrice;
        }
        
        return 0;
    }

    private double getDiscountKeyboardAndMouseQuantity
    (final List<EItem> itemsOrdered) {
        boolean trovato = false;
        int matchFound = 0;
        double minPriceMouse = Double.MAX_VALUE;
        double minPriceKeyboard = Double.MAX_VALUE;
        
        //Se già regaliamo un mouse con il metodo getDiscount10Mouses
        //Allora regaliamo la tastiera più economica

        
        for(final EItem item : itemsOrdered){
            if(item.getItemType() == ItemType.Mouse){
                minPriceMouse = Math.min(minPriceMouse, item.getPrice());
                ++matchFound;
                trovato = true;
            }
            if(item.getItemType() == ItemType.Keyboard){
                minPriceKeyboard = Math.min(minPriceKeyboard, item.getPrice());
                --matchFound;
                trovato = true;
            }
        }

        if(matchFound == 0 && trovato) {
            if(getDiscount10Mouse(itemsOrdered) == 0) {
                return Math.min(minPriceKeyboard, minPriceMouse);
            } else {
                return minPriceKeyboard;
            }
        }
        
        return 0;
    }

    private double get10PercentDiscountIfTotalOver1000
    (final double totalPrice)  {
        if(totalPrice > 1000) {
            return totalPrice/10;
        }
        
        return 0;
    }

}