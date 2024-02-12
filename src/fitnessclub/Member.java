package fitnessclub;

public class Member implements Comparable<Member> {
    private Profile profile;
    private Date expire;
    private Location homeStudio;

    public Member(Profile profile, Date expire, Location homeStudio) {
        this.profile = profile;
        this.expire = expire;
        this.homeStudio = homeStudio;
    }

    public Profile getProfile() {
        return profile;
    }

    public Date getExpire() {
        return expire;
    }

    public Location getHomeStudio() {
        return homeStudio;
    }

    public double bill() {
        return 0.0;

    } //return the next due amount

    @Override
    public int compareTo(Member o) {
        return this.profile.compareTo(o.profile);
    }
}
