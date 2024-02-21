package fitnessclub;

public class Premium extends Member {
    private static final double MONTH_FEE = 59.99;
    private static final int TOTAL_MONTHS = 12;
    private static final int FREE_MONTH = 1;

    private int guestPass;

    public Premium(Profile profile, Date expire, Location homeStudio, int guestPass) {
        super(profile, expire, homeStudio);
        this.guestPass = guestPass;
    }

    public int getGuestPass() {
        return guestPass;
    }

    public void useGuestPass() {
        guestPass--;
    }

    public void addGuestPass() {
        guestPass++;
    }

    @Override
    public double bill() {
        return MONTH_FEE * (TOTAL_MONTHS - FREE_MONTH);
    }

    @Override
    public String toString() {
        String result;

        if (isExpired()) {
            result = ", (Premium) guest-pass remaining: not eligible";
        } else {
            result = " (Premium) Guess-pass remaining: " + this.getGuestPass();
        }
        return super.toString() + result;
    }
}
