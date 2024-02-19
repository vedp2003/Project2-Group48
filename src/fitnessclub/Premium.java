package fitnessclub;

public class Premium extends Member {
    private static final double MONTHLY_FEE = 59.99;
    private static final int MONTHS_IN_YEAR = 12;
    private static final int FREE_MONTHS = 1;
    private static final int GUEST_PASSES = 3;


    private int guestPass;

    public Premium(Profile profile, Date expire, Location homeStudio, int guestPass) {
        super(profile, expire, homeStudio);
        this.guestPass = guestPass;
    }

    public int getGuestPass() {
        return guestPass;
    }

    public void useGuestPass() {
        //if(guestPass > 0) {
            guestPass--;
        //}
    }

    public void addGuestPass() {
        //if(guestPass > 0 && guestPass < GUEST_PASSES) {
            guestPass ++;
        //}
    }

    @Override
    public double bill() {
        return MONTHLY_FEE * (MONTHS_IN_YEAR - FREE_MONTHS);
    }

    @Override
    public String toString() {

        String result = "";
        String guestPassStatus = "" + this.getGuestPass();

        if (isExpired()) {
            guestPassStatus = "not eligible";
            result = ", (Premium) guest-pass remaining: " + guestPassStatus;
        }
        else{
            result = " (Premium) Guess-pass remaining: " + guestPassStatus;
        }

        return super.toString() + result;
    }
}
