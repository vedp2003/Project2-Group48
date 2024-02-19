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

//    public FitnessClass(Offer classInfo, Instructor instructor, Location studio) {
//        this.classInfo = classInfo;
//        this.instructor = instructor;
//        this.studio = studio;
//        members = new MemberList();
//        guests = new MemberList();
//    }

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
        if (this.members.contains(member)) {
            return false; // Member already exists in the class
        }
        this.members.add(member);
        return true;
    }

    public boolean removeMember(Member member) {
        return members.remove(member);
    }

    public boolean addGuest(Member guest) {
        this.guests.add(guest);
        return true;
    }

    public boolean removeGuest(Member guest) {
        return guests.remove(guest);
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FitnessClass)) return false;
        FitnessClass other = (FitnessClass) obj;
        return this.classInfo == other.classInfo &&
                this.instructor == other.instructor &&
                this.studio == other.studio &&
                this.time == other.time;
    }

    @Override
    public String toString() {
        return classInfo + " - " + instructor + ", " + time + ", " + studio.getName();
    }

}
