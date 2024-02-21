package fitnessclub;

public class Family extends Member {
    private static final double MONTH_FEE = 49.99;
    private static final int BILL_MONTHS = 3;
    private boolean guest;

    public Family(Profile profile, Date expire, Location homeStudio, boolean guest) {
        super(profile, expire, homeStudio);
        this.guest = guest;
    }

    public boolean isGuest() {
        return guest;
    }

    public void setGuest(boolean guest) {
        this.guest = guest;
    }

    @Override
    public double bill() {
        return MONTH_FEE * BILL_MONTHS;
    }

    @Override
    public String toString() {

        String guestNumber = "0";

        if (isGuest()) {
            guestNumber = "1";
        }

        if (isExpired()) {
            guestNumber = "not eligible";
        }
        return super.toString() + ", (Family) guest-pass remaining: " + guestNumber;
    }
}
