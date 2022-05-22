package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class EItemTest {

    private EItem eItem;

    @Before
    public void testEItemSetup() {
        // Arrange
        this.eItem = new EItem(ItemType.Processor, "Board", 69.420);
    }

    @Test
    public void testGetItemType() {
        // Act
        ItemType itemType = this.eItem.getItemType();

        // Assert
        assertEquals(ItemType.Processor, itemType);
    }

    @Test
    public void testGetName() {
        // Act
        String name = this.eItem.getName();

        // Assert
        assertEquals("Board", name);
    }

    @Test
    public void testGetPrice() {
        // Act
        double price = this.eItem.getPrice();

        // Assert
        assertEquals(69.420, price, 0);
    }
    
}
