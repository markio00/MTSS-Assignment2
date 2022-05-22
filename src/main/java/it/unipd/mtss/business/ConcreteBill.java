////////////////////////////////////////////////////////////////////
// Marco Marchiante 1222397
// Michele Bettin 1216735
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.ItemType;
import it.unipd.mtss.model.Order;
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
        if (itemsOrdered.size() > 30){
            throw new BillException(
            "Limite ordine ecceduto, massimo 30 articoli per ordine.");
        }
        
        double totalPrice = getTotalPrice(itemsOrdered);
        
        return totalPrice -
        getDiscount5Processors(itemsOrdered) -
        getDiscount10Mouse(itemsOrdered)-
        getDiscountKeyboardAndMouseQuantity(itemsOrdered) -
        get10PercentDiscountIfTotalOver1000(totalPrice) +
        get2EuroCommission(totalPrice);
    }

    public List<Order> ordersGiftLottery(List<Order> orders) {

        Vector<User> luckyUsers = new Vector<User>();
        int maxGifts = 10;

        Vector<Order> giftedOrders = new Vector<Order>();

        Collections.shuffle(orders);

        for(Order order : orders) {

            if(order.getUser().isUnderage() &&
                order.getOrderTIme().isAfter(
                    LocalTime.of(18,00,00,00).minusSeconds(1)) &&
                order.getOrderTIme().isBefore(
                    LocalTime.of(19,00,00,00).plusSeconds(1))) {

                if(!luckyUsers.contains(order.getUser()) && maxGifts-- > 0) {

                    luckyUsers.add(order.getUser());
    
                    order.gift();
                    giftedOrders.add(order);
                }
            }
        }
        
        return giftedOrders;

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
    
    private double get2EuroCommission(final double totalPrice) {
        if(totalPrice < 10  && totalPrice > 0) {
            return 2.0;
        }
        
        return 0;
    }
    
}