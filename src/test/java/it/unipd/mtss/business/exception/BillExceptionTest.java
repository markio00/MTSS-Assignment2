package it.unipd.mtss.business.exception;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BillExceptionTest {
    @Test
    public void billExceptionTest() {
        assertEquals("Exception thrown!",new BillException("Exception thrown!").getMessage());
    }
}
