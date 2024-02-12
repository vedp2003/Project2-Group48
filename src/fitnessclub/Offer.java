package fitnessclub;

public enum Offer {
    PILATES,
    SPINNING,
    CARDIO;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
