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

    public Location getHomeStudio() {
        return homeStudio;
    }

    public double bill() {
        return 0.0;

    } //return the next due amount

    public boolean isExpired() {
        if (expire.compareTo(new Date()) == -1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Member) {
            Member member = (Member) obj;
            return this.getProfile().equals(member.getProfile());
        }
        return false;
    }

    @Override
    public int compareTo(Member o) {
        return this.profile.compareTo(o.profile);
    }

    @Override
    public String toString() {
        String membershipExpiry = "Membership expires ";

        if (isExpired()) {
            membershipExpiry = "Membership expired ";
        }
        return this.profile + ", " + membershipExpiry + this.expire + ", Home Studio: " + homeStudio;
    }
}
