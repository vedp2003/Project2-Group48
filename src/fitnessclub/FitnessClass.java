package fitnessclub;

public class FitnessClass {
    private Offer classInfo;
    private Instructor instructor;
    private Location studio;
    private Time time;
    private MemberList members;
    private MemberList guests;

    public FitnessClass(Offer classInfo, Instructor instructor, Location studio, Time time) {
        this.classInfo = classInfo;
        this.instructor = instructor;
        this.studio = studio;
        this.time = time;
        members = new MemberList();
        guests = new MemberList();

    }

    public Offer getClassInfo() {
        return classInfo;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public Location getStudio() {
        return studio;
    }

    public Time getTime() {
        return time;
    }

    public MemberList getMembers() {
        return members;
    }

    public MemberList getGuests() {
        return guests;
    }

    public boolean addMember(Member member) {
        return this.members.add(member);
    }

    public boolean removeMember(Member member) {
        return this.members.remove(member);
    }

    public boolean addGuest(Member guest) {
        this.guests.add(guest);
        return true;
    }

    public boolean removeGuest(Member guest) {
        this.guests.remove(guest);
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FitnessClass) {
            FitnessClass fitnessClass = (FitnessClass) obj;
            return this.classInfo == fitnessClass.classInfo &&
                    this.instructor == fitnessClass.instructor &&
                    this.studio == fitnessClass.studio &&
                    this.time == fitnessClass.time;
        }
        return false;
    }

    @Override
    public String toString() {
        return classInfo + " - " + instructor + ", " + time + ", " + studio.getName();
    }

}
