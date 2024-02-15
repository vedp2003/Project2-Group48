package fitnessclub;

public class FitnessClass {
    private Offer classInfo;
    private Instructor instructor;
    private Location studio;
    private Time time;
    private MemberList members;
    private MemberList guests;

    public FitnessClass(Offer classInfo, Instructor instructor, Location studio, Time time, MemberList members, MemberList guests) {
        this.classInfo = classInfo;
        this.instructor = instructor;
        this.studio = studio;
        this.time = time;
        this.members = new MemberList();
        this.guests = new MemberList();
    }
    public FitnessClass(Offer classInfo, Instructor instructor, Location studio, Time time) {
        this.classInfo = classInfo;
        this.instructor = instructor;
        this.studio = studio;
        this.time = time;
    }

    public void setClassInfo(Offer classInfo) {
        this.classInfo = classInfo;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public void setStudio(Location studio) {
        this.studio = studio;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public boolean addMember(Member member) {
        return members.add(member);
    }

    public boolean removeMember(Member member) {
        return members.remove(member);
    }

    public boolean addGuest(Member guest) {
        return guests.add(guest);
    }

    public boolean removeGuest(Member guest) {

        return guests.remove(guest);
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



}
