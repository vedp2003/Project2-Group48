package fitnessclub;

public class Family extends Member {

    private static final double MONTHLY_FEE = 49.99;
    private static final int BILLING_CYCLE_MONTHS = 3;
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
        return MONTHLY_FEE * BILLING_CYCLE_MONTHS;
    }

    @Override
    public String toString() {

        String guestPassStatus = "";

        if(isGuest()){
            guestPassStatus = "1";
        }

        if (isExpired()) {
            guestPassStatus = "not eligible";
        }

        return super.toString() + ", (Family) guest-pass remaining: " + guestPassStatus;
    }
}
