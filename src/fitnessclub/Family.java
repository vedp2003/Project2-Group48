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

    @Override
    public double bill() {
        return MONTHLY_FEE * BILLING_CYCLE_MONTHS;
    }
}
