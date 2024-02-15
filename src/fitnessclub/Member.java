package fitnessclub;

import java.util.Calendar;

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
        //complete as needed
        return 0.0;

    } //return the next due amount

    public boolean isExpired() {
        if (expire.compareTo(new Date()) == -1) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Member o) {
        return this.profile.compareTo(o.profile);
    }

    @Override
    public String toString() {

        String memberStatus = "Membership expires";
        if (isExpired()) {
            memberStatus = "Membership expired";
        }

        return this.profile + ", " + memberStatus + " " + this.expire + ", Home Studio: " + homeStudio;

        //return this.profile + ", Membership expires " + this.expire + ", Home Studio: " + homeStudio;
    }
}
