package fitnessclub;

public enum Time {

    MORNING(9, 30),
    AFTERNOON(14, 0),
    EVENING(18, 30);

    private final int hour;
    private final int minute;

    Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public String toString() {
        return String.format("%01d:%02d", hour, minute);
    }
}
