package fitnessclub;

public enum Instructor {
    JENNIFER,
    KIM,
    DENISE,
    EMMA;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
