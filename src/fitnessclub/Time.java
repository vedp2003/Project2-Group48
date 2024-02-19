package fitnessclub;

public enum Time {

    MORNING(9, 30),
    AFTERNOON(14, 00),
    EVENING(18, 30);

    private final int hour;
    private final int minute;

    Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean equalsTime(Time time) {
        if(this.hour == time.hour && this.minute == time.minute){
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return String.format("%01d:%02d", hour, minute);
    }

//    @Override
//    public String toString() {
//        switch (this) {
//            case MORNING:
//                return this.getHour() + ":" + this.getMinute();
//            case AFTERNOON:
//                return this.getHour() + ":" + this.getMinute();
//            case EVENING:
//                return this.getHour() + ":" + this.getMinute();
//            default:
//                throw new IllegalArgumentException();
//        }
//    }
}
