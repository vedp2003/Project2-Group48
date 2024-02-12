package fitnessclub;

public class Basic extends Member {
    private static final double MONTHLY_FEE = 39.99;
    private static final double CLASS_OVERCHARGE = 10.0;
    private static final int FREE_CLASSES = 4;

    private int numClasses;

    public Basic(Profile profile, Date expire, Location homeStudio, int numClasses) {
        super(profile, expire, homeStudio);
        this.numClasses = numClasses;
    }

    public int getNumClasses() {
        return numClasses;
    }

    @Override
    public double bill() {
        double billAmount = MONTHLY_FEE;
        if (numClasses > FREE_CLASSES) {
            billAmount += (numClasses - FREE_CLASSES) * CLASS_OVERCHARGE;
        }
        return billAmount;
    }
}
