package fitnessclub;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * This class creates and defines the properties of a Date object
 *
 * @author Ved Patel, Vivek Manthri
 */
public class Date implements Comparable<Date> {
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    public static final int NUMBER_MONTHS = 12;
    public static final int BIG_MONTH_DAYS = 31;
    public static final int SMALL_MONTH_DAYS = 30;
    public static final int LEAP_FEB_DAYS = 29;
    public static final int REG_FEB_DAYS = 28;
    public static final int MIN_YEARS = 0;
    public static final int MIN_MONTHS = 1;
    public static final int MIN_DAYS = 1;
    public static final int FEB = 2;
    public static final int APR = 4;
    public static final int JUN = 6;
    public static final int SEP = 9;
    public static final int NOV = 11;

    private int year;
    private int month;
    private int day;

    /**
     * Default constructor/no-argument constructor
     */
    public Date() {

        Calendar calendarInstance = Calendar.getInstance();
        int currentYear = calendarInstance.get(Calendar.YEAR);
        int currentMonth = calendarInstance.get(Calendar.MONTH) + 1; //since Calendar numbers months from 0 to 11
        int currentDay = calendarInstance.get(Calendar.DAY_OF_MONTH);

        this.year = currentYear;
        this.month = currentMonth;
        this.day = currentDay;

    }

    /**
     * Parameterized constructor requires 1 parameter to create a Date object
     *
     * @param date a string of a date in the format mm/dd/yyyy
     */
    public Date(String date) {
        StringTokenizer stringTokenizer = new StringTokenizer(date);
        this.month = Integer.parseInt(stringTokenizer.nextToken("/"));
        this.day = Integer.parseInt(stringTokenizer.nextToken("/"));
        this.year = Integer.parseInt(stringTokenizer.nextToken());
    }

    /**
     * Helper method that checks if the given year is a leap year
     *
     * @param year the year to check
     * @return true if the given year is a leap year; false otherwise
     */
    private boolean isLeap(int year) {
        return (year % QUADRENNIAL == 0 && (year % CENTENNIAL != 0
                || year % QUATERCENTENNIAL == 0));
    }

    /**
     * Checks if a date is a valid calendar date
     *
     * @return true if the date is valid; false otherwise
     */
    public boolean isValid() {
        if (this.year < MIN_YEARS || this.month > NUMBER_MONTHS || this.month < MIN_MONTHS ||
                this.day > BIG_MONTH_DAYS || this.day < MIN_DAYS) {
            return false;
        }
        if (this.month == APR || this.month == JUN || this.month == SEP || this.month == NOV) {
            if (this.day > SMALL_MONTH_DAYS) {
                return false;
            }
        } else if (this.month == FEB) {
            if (!isLeap(this.year) && this.day > REG_FEB_DAYS) {
                return false;
            }
            if (isLeap(this.year) && this.day > LEAP_FEB_DAYS) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if the date is either today or in the future compared to the current date
     *
     * @return true if the date is today or in the future; false otherwise
     */
    public boolean isTodayOrFutureDate() {
        Calendar calendarInstance = Calendar.getInstance();
        int currentYear = calendarInstance.get(Calendar.YEAR);
        int currentMonth = calendarInstance.get(Calendar.MONTH) + 1; //since Calendar numbers months from 0 to 11
        int currentDay = calendarInstance.get(Calendar.DAY_OF_MONTH);

        return (this.year > currentYear) || (this.year == currentYear && this.month > currentMonth) ||
                (this.year == currentYear && this.month == currentMonth && this.day > currentDay) ||
                ((this.year == currentYear && this.month == currentMonth && this.day == currentDay));

    }

    /**
     * Checks if the date is less than 18 years from today's date
     * @return true if the date was more than 18 years ago, false otherwise
     */
    public boolean isLessThan18(Date dob){


        Calendar calendarInstance = Calendar.getInstance();
        int currentYear = calendarInstance.get(Calendar.YEAR);
        int currentMonth = calendarInstance.get(Calendar.MONTH) + 1; //since Calendar numbers months from 0 to 11

        if ((currentYear - dob.year) < 18) {
            return true;
        }
        else if ((currentYear - dob.year) == 18) {
            return dob.month > currentMonth;
        }
        return false;
    }

    public Date plusMonths(int months) {
        month += months;

        //Calendar calendar = Calendar.getInstance();
        //calendar.set(year, month - 1, day); // Calendar months are 0-based.
        //calendar.add(Calendar.MONTH, months);
        return new Date(month + "/" + day + "/" + year);
    }

    public Date plusYears(int years) {

        year += years;

        //Calendar calendar = Calendar.getInstance();
        //calendar.set(year, month - 1, day);
        //calendar.add(Calendar.YEAR, years);
        return new Date(month + "/" + day + "/" + year);
    }
    public boolean isExpired() {
        Calendar calendarInstance = Calendar.getInstance();
        int currentYear = calendarInstance.get(Calendar.YEAR);
        int currentMonth = calendarInstance.get(Calendar.MONTH) + 1;
        int currentDay = calendarInstance.get(Calendar.DAY_OF_MONTH);

        return (this.year < currentYear) || (this.year == currentYear && this.month < currentMonth) ||
                (this.year == currentYear && this.month == currentMonth && this.day < currentDay);

    }

    /**
     * Compare two Date objects based on year, month, and day
     *
     * @param o the date object to be compared
     * @return a negative one, zero, or a positive one depending on if this date is
     * less than, equal to, or greater than the specified date
     */
    @Override
    public int compareTo(Date o) {
        if (this.year != o.year && this.year - o.year > 0) {
            return 1;
        } else if (this.year != o.year && this.year - o.year < 0) {
            return -1;
        }

        if (this.month != o.month && this.month - o.month > 0) {
            return 1;
        } else if (this.month != o.month && this.month - o.month < 0) {
            return -1;
        }

        if (this.day != o.day && this.day - o.day > 0) {
            return 1;
        } else if (this.day != o.day && this.day - o.day < 0) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            Date date = (Date) obj;
            return (this.day == date.day && this.month == date.month && this.year == date.year);
        }
        return false;
    }

    /**
     * Return a textual representation of a Date object
     *
     * @return a string of the format mm/dd/yyyy
     */
    @Override
    public String toString() {
        return this.month + "/" + this.day + "/" + this.year;
    }

    /**
     * Testbed main() for Date class, which tests the isValid() method.
     * Tests five invalid and two valid test cases
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Date one = new Date();
        String currentDate = one.toString();
        System.out.println("C " +currentDate);

        Date dateCheck1_1 = new Date("15/21/2015");
        System.out.println("Test Case 1_1 for whether " + dateCheck1_1
                + " is valid : " + dateCheck1_1.isValid());
        Date dateCheck1_2 = new Date("0/21/2015");
        System.out.println("Test Case 1_2 for whether " + dateCheck1_2
                + " is valid : " + dateCheck1_2.isValid());

        Date dateCheck2_1 = new Date("1/32/1997");
        System.out.println("Test Case 2_1 for whether " + dateCheck2_1
                + " is valid : " + dateCheck2_1.isValid());
        Date dateCheck2_2 = new Date("1/0/1997");
        System.out.println("Test Case 2_2 for whether " + dateCheck2_2
                + " is valid : " + dateCheck2_2.isValid());

        Date dateCheck3_1 = new Date("6/31/2000");
        System.out.println("Test Case 3_1 for whether " + dateCheck3_1
                + " is valid : " + dateCheck3_1.isValid());
        Date dateCheck3_2 = new Date("6/0/2000");
        System.out.println("Test Case 3_2 for whether " + dateCheck3_2
                + " is valid : " + dateCheck3_2.isValid());

        Date dateCheck4 = new Date("2/29/2005");
        System.out.println("Test Case 4 for whether " + dateCheck4
                + " is valid : " + dateCheck4.isValid());

        Date dateCheck5 = new Date("2/30/2000");
        System.out.println("Test Case 5 for whether " + dateCheck5
                + " is valid : " + dateCheck5.isValid());

        Date dateCheck6 = new Date("1/27/2009");
        System.out.println("Test Case 6 for whether " + dateCheck6
                + " is valid : " + dateCheck6.isValid());

        Date dateCheck7 = new Date("2/29/2000");
        System.out.println("Test Case 7 for whether " + dateCheck7
                + " is valid : " + dateCheck7.isValid());
    }
}

