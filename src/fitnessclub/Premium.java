package fitnessclub;

public class Premium extends Member {
    private static final double MONTHLY_FEE = 59.99;
    private static final int MONTHS_IN_YEAR = 12;
    private static final int FREE_MONTHS = 1;

    private int guestPass;

    public Premium(Profile profile, Date expire, Location homeStudio, int guestPass) {
        super(profile, expire, homeStudio);
        this.guestPass = guestPass;
    }

    public int getGuestPass() {
        return guestPass;
    }

    @Override
    public double bill() {
        return MONTHLY_FEE * (MONTHS_IN_YEAR - FREE_MONTHS);
    }
}
