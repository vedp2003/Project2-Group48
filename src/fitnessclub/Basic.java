package fitnessclub;

public class Basic extends Member {
    private static final double MONTH_FEE = 39.99;
    private static final double ADDITIONAL_CHARGE = 10.0;
    private static final int CLASSES_LIMIT = 4;

    private int numClasses;

    public Basic(Profile profile, Date expire, Location homeStudio, int numClasses) {
        super(profile, expire, homeStudio);
        this.numClasses = numClasses;
    }

    public int getNumClasses() {
        return numClasses;
    }


    public void addClass() {
        numClasses++;
    }

    @Override
    public double bill() {
        double billAmount = MONTH_FEE;
        if (numClasses > CLASSES_LIMIT) {
            billAmount += ADDITIONAL_CHARGE * (numClasses - CLASSES_LIMIT);
        }
        return billAmount;
    }

    @Override
    public String toString() {
        return super.toString() + ", (Basic) number of classes attended: " + this.getNumClasses();
    }
}
