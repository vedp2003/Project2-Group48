package fitnessclub;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class StudioManager {

    private MemberList memberList;
    private Schedule schedule;

    public StudioManager() {
        memberList = new MemberList();
        schedule = new Schedule();
    }

    public void run() {
        try {
            memberList = new MemberList();
            memberList.load(new File("memberList.txt"));
            System.out.println("-list of members loaded-");
            printMembers();
            System.out.println("-end of list-\n\n");

            schedule = new Schedule();
            schedule.load(new File("classSchedule.txt"));
            System.out.println("-Fitness classes loaded-");
            printClasses();
            System.out.println("-end of class list.\n");

        } catch (IOException e) {
            return;
        }

        System.out.println("Studio Manager is up running...\n");

        Scanner scanner = new Scanner(System.in);
        String inputStr;
        while (true) {
            inputStr = scanner.nextLine();
            while (inputStr.isEmpty()) {
                inputStr = scanner.nextLine();
            }
            if (inputStr.equals("Q")) {
                System.out.println("Studio Manager terminated.");
                break;
            }
            processInputs(inputStr);
        }
        scanner.close();

    }

    private void printMembers() {
        for (int i = 0; i < memberList.getSize(); i++) {
            System.out.println(memberList.getMembers()[i]);
        }
    }

    private void printClasses() {
        for (int i = 0; i < schedule.getNumClasses(); i++) {
            System.out.println(schedule.getClasses()[i]);
        }
    }

    private void processInputs(String input) {
        String[] strSplit = input.split("\\s");
        if (strSplit[0].equals("AB")) {
            if (strSplit.length != 5) {
                System.out.println("Missing data tokens.");
                return;
            }
            addBasicMember(strSplit);
        } else if (strSplit[0].equals("AF")) {
            if (strSplit.length != 5) {
                System.out.println("Missing data tokens.");
                return;
            }
            addFamilyMember(strSplit);
        } else if (strSplit[0].equals("AP")) {
            if (strSplit.length != 5) {
                System.out.println("Missing data tokens.");
                return;
            }
            addPremiumMember(strSplit);
        } else if (strSplit[0].equals("C")) {
            cancelMembership(strSplit);
        } else if (strSplit[0].equals("S")) {
            displayClassSchedule();
        } else if (strSplit[0].equals("PM")) {
            memberList.printByMember();
        } else if (strSplit[0].equals("PC")) {
            memberList.printByCounty();
        } else if (strSplit[0].equals("PF")) {
            memberList.printFees();
        } else if (strSplit[0].equals("R")) {
            registerForMemberClass(strSplit);
        } else if (strSplit[0].equals("U")) {
            unregisterMemberFromClass(strSplit);
        } else if (strSplit[0].equals("RG")) {
            registerGuestForClass(strSplit);
        } else if (strSplit[0].equals("UG")) {
            unregisterGuestFromClass(strSplit);
        } else {
            System.out.println(strSplit[0] + " is an invalid command!");
        }
    }

    private void addBasicMember(String[] tokens) {
        String firstName = tokens[1];
        String lastName = tokens[2];
        Date dob;
        try {
            dob = new Date(tokens[3]);
        } catch (NumberFormatException e) {
            System.out.println("The date contains characters.");
            return;
        }
        String studioLocationString = tokens[4];
        if (!dob.isValid()) {
            System.out.println("DOB " + dob + ": invalid calendar date!");
            return;
        }
        if (dob.isTodayOrFutureDate()) {
            System.out.println("DOB " + dob + ": cannot be today or a future date!");
            return;
        }
        if (dob.isLessThan18(dob)) {
            System.out.println("DOB " + dob + ": must be 18 or older to join!");
            return;
        }
        Location studioLocation;
        try {
            studioLocation = Location.valueOf(studioLocationString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(studioLocationString + ": invalid studio location!");
            return;
        }
        Profile newProfile = new Profile(firstName, lastName, dob);
        if (memberList.containsProfile(newProfile)) {
            System.out.println(firstName + " " + lastName + " is already in the member database.");
            return;
        }
        Date expireDate = new Date().plusMonths(1);
        Member newMember = new Basic(newProfile, expireDate, studioLocation, 0);
        memberList.add(newMember);
        System.out.println(firstName + " " + lastName + " added.");
    }

    private void addFamilyMember(String[] tokens) {
        String firstName = tokens[1];
        String lastName = tokens[2];
        Date dob;
        try {
            dob = new Date(tokens[3]);
        } catch (NumberFormatException e) {
            System.out.println("The date contains characters.");
            return;
        }
        String studioLocationString = tokens[4];
        if (!dob.isValid()) {
            System.out.println("DOB " + dob + ": invalid calendar date!");
            return;
        }
        if (dob.isTodayOrFutureDate()) {
            System.out.println("DOB " + dob + ": cannot be today or a future date!");
            return;
        }
        if (dob.isLessThan18(dob)) {
            System.out.println("DOB " + dob + ": must be 18 or older to join!");
            return;
        }
        Location studioLocation;
        try {
            studioLocation = Location.valueOf(studioLocationString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(studioLocationString + ": invalid studio location!");
            return;
        }
        Profile newProfile = new Profile(firstName, lastName, dob);
        if (memberList.containsProfile(newProfile)) {
            System.out.println(firstName + " " + lastName + " is already in the member database.");
            return;
        }
        Date expireDate = new Date().plusMonths(3);
        Member newMember = new Family(newProfile, expireDate, studioLocation, true);
        memberList.add(newMember);
        System.out.println(firstName + " " + lastName + " added.");
    }

    private void addPremiumMember(String[] tokens) {
        String firstName = tokens[1];
        String lastName = tokens[2];
        Date dob;
        try {
            dob = new Date(tokens[3]);
        } catch (NumberFormatException e) {
            System.out.println("The date contains characters.");
            return;
        }
        String studioLocationString = tokens[4];
        if (!dob.isValid()) {
            System.out.println("DOB " + dob + ": invalid calendar date!");
            return;
        }
        if (dob.isTodayOrFutureDate()) {
            System.out.println("DOB " + dob + ": cannot be today or a future date!");
            return;
        }
        if (dob.isLessThan18(dob)) {
            System.out.println("DOB " + dob + ": must be 18 or older to join!");
            return;
        }
        Location studioLocation;
        try {
            studioLocation = Location.valueOf(studioLocationString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(studioLocationString + ": invalid studio location!");
            return;
        }
        Profile newProfile = new Profile(firstName, lastName, dob);
        if (memberList.containsProfile(newProfile)) {
            System.out.println(firstName + " " + lastName + " is already in the member database.");
            return;
        }
        Date expireDate = new Date().plusYears(1);
        Member newMember = new Premium(newProfile, expireDate, studioLocation, 3);
        memberList.add(newMember);
        System.out.println(firstName + " " + lastName + " added.");
    }

    private void cancelMembership(String[] tokens) {
        if (tokens.length != 4) {
            System.out.println("Missing data tokens.");
            return;
        }
        String firstName = tokens[1];
        String lastName = tokens[2];
        Date dob;
        try {
            dob = new Date(tokens[3]);
        } catch (NumberFormatException e) {
            System.out.println("The date contains characters.");
            return;
        }
        Profile cancelProfile = new Profile(firstName, lastName, dob);

        boolean foundAndRemoved = false;
        for (Member member : memberList.getMembers()) {
            if (member != null && member.getProfile().equals(cancelProfile)) {
                foundAndRemoved = memberList.remove(member);
                break;
            }
        }
        if (foundAndRemoved) {
            System.out.println(firstName + " " + lastName + " removed.");
        } else {
            System.out.println(firstName + " " + lastName + " is not in the member database.");
        }
    }

    private void displayClassSchedule() {
        System.out.println("-Fitness classes-");
        for (FitnessClass fitnessClass : schedule.getClasses()) {
            if (fitnessClass != null) {
                System.out.println(fitnessClass);
                if (fitnessClass.getMembers().getSize() > 0) {
                    System.out.println("[Attendees]");
                    for (Member member : fitnessClass.getMembers().getMembers()) {
                        if (member != null) {
                            System.out.println("   " + member);
                        }
                    }
                }
                if (fitnessClass.getGuests().getSize() > 0) {
                    System.out.println("[Guests]");
                    for (Member guest : fitnessClass.getGuests().getMembers()) {
                        if (guest != null) {
                            System.out.println("   " + guest);
                        }
                    }
                }
            }
        }
        System.out.println("-end of class list.\n");
    }

    private void registerForMemberClass(String[] tokens) {
        String classString = tokens[1];
        String instructorString = tokens[2];
        String studioString = tokens[3];
        String firstName = tokens[4];
        String lastName = tokens[5];
        Date dob = new Date(tokens[6]);
        if (!registerInputChecker(tokens, classString, instructorString, studioString, firstName, lastName, dob)) {
            return;
        }
        Offer classType = Offer.valueOf(classString.toUpperCase());

        Instructor instructor = Instructor.valueOf(instructorString.toUpperCase());
        Location studio = Location.valueOf(studioString.toUpperCase());
        Profile profile = new Profile(firstName, lastName, dob);
        Member member = memberList.getMemberFromProfile(profile);

        if (member instanceof Basic && !member.getHomeStudio().equals(studio)) {
            System.out.println(firstName + " " + lastName + " is attending a class at " + studio.getName()
                    + " - [BASIC] home studio at " + member.getHomeStudio().getName());
            return;
        }
        FitnessClass targetClass = schedule.findClass(classType, instructor, studio);
        if (targetClass == null) {
            System.out.println(classString + " by " + instructorString + " does not exist at " + studioString);
            return;
        }
        if (targetClass.getMembers().contains(member)) {
            System.out.println(member.getProfile().getFname() + " " + member.getProfile().getLname() + " is already in the class.");
            return;
        }
        if (timeConflictChecker(targetClass, profile, instructor, studio, member)) {
            return;
        }
        targetClass.addMember(member);
        if (member instanceof Basic) {
            ((Basic) member).addClass();
        }
        System.out.println(member.getProfile().getFname() + " " + member.getProfile().getLname() + " attendance recorded " +
                targetClass.getClassInfo() + " at " + targetClass.getStudio());
    }

    private boolean registerInputChecker(String[] tokens, String classString,
                                         String instructorString, String studioString,
                                         String firstName, String lastName, Date dob) {
        if (tokens.length != 7) {
            System.out.println("Missing data tokens.");
            return false;
        }
        Offer classType;
        Instructor instructor;
        Location studio;
        try {
            classType = Offer.valueOf(classString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(classString + " - class name does not exist.");
            return false;
        }
        try {
            instructor = Instructor.valueOf(instructorString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(instructorString + " - instructor does not exist.");
            return false;
        }
        try {
            studio = Location.valueOf(studioString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(studioString + " - invalid studio location.");
            return false;
        }
        Profile profile = new Profile(firstName, lastName, dob);
        if (!memberList.containsProfile(profile)) {
            System.out.println(firstName + " " + lastName + " " + dob + " is not in the member database.");
            return false;
        }
        Member member = memberList.getMemberFromProfile(profile);
        if (member.isExpired()) {
            System.out.println(firstName + " " + lastName + " " + dob + " membership expired.");
            return false;
        }
        return true;
    }

    private boolean timeConflictChecker(FitnessClass targetClass, Profile profile, Instructor instructor, Location studio, Member member) {
        for (FitnessClass fitnessClass : schedule.getClasses()) {
            if (fitnessClass != null && !fitnessClass.equals(targetClass) && fitnessClass.getTime().equals(targetClass.getTime())
                    && fitnessClass.getMembers().contains(member)) {

                System.out.println("Time conflict - " + profile.getFname() + " " + profile.getLname() +
                        " is in another class held at " + fitnessClass.getTime() + " - " +
                        instructor + ", " + fitnessClass.getTime() + ", " + studio.getName());
                return true;
            }
        }
        return false;
    }

    private void unregisterMemberFromClass(String[] tokens) {
        if (tokens.length != 7) {
            System.out.println("Missing data tokens.");
            return;
        }
        String classString = tokens[1];
        String instructorString = tokens[2];
        String studioString = tokens[3];
        String firstName = tokens[4];
        String lastName = tokens[5];
        Date dob = new Date(tokens[6]);
        Profile unregisterProfile = new Profile(firstName, lastName, dob);
        Member unregisterMember = memberList.getMemberFromProfile(unregisterProfile);
        for (FitnessClass fitnessClass : schedule.getClasses()) {
            if (fitnessClass.equals(schedule.findClass(Offer.valueOf(classString.toUpperCase()),
                    Instructor.valueOf(instructorString.toUpperCase()),
                    Location.valueOf(studioString.toUpperCase())))) {

                boolean removed = fitnessClass.removeMember(unregisterMember);
                if (removed) {
                    System.out.println(unregisterProfile.getFname() + " " +
                            unregisterProfile.getLname() + " is removed from " +
                            fitnessClass.getInstructor() + ", " + fitnessClass.getTime() + ", " +
                            fitnessClass.getStudio());
                    return;
                } else {
                    System.out.println(unregisterProfile.getFname() + " " +
                            unregisterProfile.getLname() + " is not in " +
                            fitnessClass.getInstructor() + ", " + fitnessClass.getTime() +
                            ", " + fitnessClass.getStudio());
                }
            }
        }
    }

    private void registerGuestForClass(String[] tokens) {
        String classString = tokens[1];
        String instructorString = tokens[2];
        String studioString = tokens[3];
        String firstName = tokens[4];
        String lastName = tokens[5];
        Date dob = new Date(tokens[6]);
        if (!registerInputChecker(tokens, classString, instructorString, studioString, firstName, lastName, dob)) {
            return;
        }
        Offer classType = Offer.valueOf(classString.toUpperCase());
        ;
        Instructor instructor = Instructor.valueOf(instructorString.toUpperCase());
        Location studio = Location.valueOf(studioString.toUpperCase());
        Profile profile = new Profile(firstName, lastName, dob);
        Member member = memberList.getMemberFromProfile(profile);
        if (member instanceof Basic) {
            System.out.println(firstName + " " + lastName + " [BASIC] - no guest pass.");
            return;
        }
        if (!member.getHomeStudio().equals(studio)) {
            System.out.println(firstName + " " + lastName + " (guest) is attending a class at " +
                    studio.getName() + " - home studio at " + member.getHomeStudio().getName());
            return;
        }
        if (member instanceof Premium && ((Premium) member).getGuestPass() <= 0) {
            System.out.println(firstName + " " + lastName + " guest pass not available.");
            return;
        }
        if (member instanceof Family && !((Family) member).isGuest()) {
            System.out.println(firstName + " " + lastName + " guest pass not available.");
            return;
        }
        guestAttendance(classType, instructor, studio, firstName, lastName, member);
    }

    private boolean guestAttendance(Offer classType, Instructor instructor, Location studio, String firstName, String lastName, Member member) {
        for (FitnessClass fitnessClass : schedule.getClasses()) {
            if (fitnessClass.equals(schedule.findClass(classType, instructor, studio))) {

                if (fitnessClass.addGuest(member)) {
                    if (member instanceof Premium) {
                        ((Premium) member).useGuestPass();
                    }
                    if (member instanceof Family) {
                        ((Family) member).setGuest(false);
                    }
                    System.out.println(firstName + " " + lastName + " (guest) attendance recorded " +
                            classType + " at " + fitnessClass.getStudio());
                    return true;
                }
            }
        }
        return false;

    }

    private void unregisterGuestFromClass(String[] tokens) {
        if (tokens.length != 7) {
            System.out.println("Missing data tokens.");
            return;
        }
        String classString = tokens[1];
        Instructor instructor = Instructor.valueOf(tokens[2].toUpperCase());
        Location studio = Location.valueOf(tokens[3].toUpperCase());
        String firstName = tokens[4];
        String lastName = tokens[5];
        Date dob = new Date(tokens[6]);
        Profile profile = new Profile(firstName, lastName, dob);
        Member unregisterGuest = memberList.getMemberFromProfile(profile);
        Offer classType;
        try {
            classType = Offer.valueOf(classString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(classString + " - class name does not exist.");
            return;
        }
        for (FitnessClass fitnessClass : schedule.getClasses()) {
            if (fitnessClass.equals(schedule.findClass(classType, instructor, studio))) {
                if (fitnessClass.removeGuest(unregisterGuest)) {
                    if (unregisterGuest instanceof Premium) {
                        ((Premium) unregisterGuest).addGuestPass();
                    }
                    if (unregisterGuest instanceof Family) {
                        ((Family) unregisterGuest).setGuest(true);
                    }
                    System.out.println(unregisterGuest.getProfile().getFname() + " " +
                            unregisterGuest.getProfile().getLname() + " (guest) is removed from " +
                            fitnessClass.getInstructor() + ", " + fitnessClass.getTime() + ", " + fitnessClass.getStudio());
                    return;
                }
            }
        }
    }
}
