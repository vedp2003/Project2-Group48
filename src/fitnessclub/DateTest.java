package fitnessclub;

import org.junit.Test;

import static org.junit.Assert.*;

public class DateTest {

    @Test
    public void testInvalidMonthRange(){
        Date dateCheck1_1 = new Date("15/21/2015");
        assertFalse(dateCheck1_1.isValid());
        Date dateCheck1_2 = new Date("0/21/2015");
        assertFalse(dateCheck1_2.isValid());
    }

    @Test
    public void testInvalid31DayRange(){
        Date dateCheck2_1 = new Date("1/32/1997");
        assertFalse(dateCheck2_1.isValid());
        Date dateCheck2_2 = new Date("1/0/1997");
        assertFalse(dateCheck2_2.isValid());
    }

    @Test
    public void testInvalid30DayRange(){
        Date dateCheck3_1 = new Date("6/31/2000");
        assertFalse(dateCheck3_1.isValid());
        Date dateCheck3_2 = new Date("6/0/2000");
        assertFalse(dateCheck3_2.isValid());
    }

    @Test
    public void testDaysInFeb_Nonleap(){
        Date dateCheck4 = new Date("2/29/2005");
        assertFalse(dateCheck4.isValid());
    }

    @Test
    public void testDaysInFeb_Leap(){
        Date dateCheck5 = new Date("2/30/2000");
        assertFalse(dateCheck5.isValid());
    }

    @Test
    public void testValidDayMonth(){
        Date dateCheck6 = new Date("1/27/2009");
        assertTrue(dateCheck6.isValid());
    }
    @Test
    public void testValidFebLeapDays(){
        Date dateCheck7 = new Date("2/29/2000");
        assertTrue(dateCheck7.isValid());
    }
}