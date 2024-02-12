package fitnessclub;

public class Member implements Comparable<Member> {
    private Profile profile;
    private Date expire;
    private Location homeStudio;

    public double bill() {

        return 0.0;

    } //return the next due amount

    @Override
    public int compareTo(Member o) {
        return 0;
    }
}
