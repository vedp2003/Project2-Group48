package fitnessclub;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
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
            try {
                memberList = new MemberList();
                memberList.load(new File("memberList.txt"));

                schedule = new Schedule();
                schedule.load(new File("classSchedule.txt"));
            } catch (NoSuchElementException e) {
                return;
            }
            catch (NumberFormatException e) {
                return;
            }
            catch (FileNotFoundException e) {
                return;
            }

            System.out.println("Studio Manager is up running...");

            Scanner scanner = new Scanner(System.in);
            String input;
            while (true) {
                input = scanner.nextLine();
                while (input.isEmpty()) {
                    input = scanner.nextLine();
                }
                if (input.equals("Q")) {
                    System.out.println("Studio Manager terminated.");
                    break;
                }
                processCommand(input);
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processCommand(String command) {
        String[] tokens = command.split("\\s");
        String cmd = tokens[0];

        try {
            switch (cmd) {
                case "AB":
                    addBasicMember(tokens);
                    break;
                case "AF":
                    addFamilyMember(tokens);
                    break;
                case "AP":
                    addPremiumMember(tokens);
                    break;
                case "C":
                    cancelMembership(tokens);
                    break;
                case "S":
                    displayClassSchedule();
                    break;
                case "PM":
                    memberList.printByMember();
                    break;
                case "PC":
                    memberList.printByCounty();
                    break;
                case "PF":
                    printMembershipFees();
                    break;
                case "R":
                    registerForMemberClass(tokens);
                    break;
                case "U":
                    unregisterMemberFromClass(tokens);
                    break;
                case "RG":
                    registerGuestForClass(tokens);
                    break;
                case "UG":
                    unregisterGuestFromClass(tokens);
                    break;
                default:
                    System.out.println(cmd + " is an invalid command!");
                    break;
            }
        } catch (Exception e) {
            System.out.println("The date contains characters.");
        }
    }

    private void addBasicMember(String[] tokens) {
        if (tokens.length != 5) {
            System.out.println("Missing data tokens.");
            return;
        }

        String firstName = tokens[1];
        String lastName = tokens[2];
        String dobStr = tokens[3];
        String studioStr = tokens[4];

        Date dob = new Date(dobStr);
        if (!dob.isValid()) {
            System.out.println("DOB " + dobStr + ": invalid calendar date!");
            return;
        }

        if (dob.isTodayOrFutureDate()) {
            System.out.println("DOB " + dobStr + ": cannot be today or a future date!");
            return;
        }

        if (dob.isLessThan18(dob)) {
            System.out.println("DOB " + dobStr + ": must be 18 or older to join!");
            return;
        }

        Location studio;
        try {
            studio = Location.valueOf(studioStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(studioStr + ": invalid studio location!");
            return;
        }

        Profile newProfile = new Profile(firstName, lastName, dob);

        if (memberList.sameProfile(newProfile)) {
            System.out.println(firstName + " " + lastName + " is already in the member database.");
            return;
        }

        Date expireDate = new Date().plusMonths(1);
        Member newMember = new Basic(newProfile, expireDate, studio, 0);
        memberList.add(newMember);
        System.out.println(firstName + " " + lastName + " added.");
    }

    private void addFamilyMember(String[] tokens) {
        if (tokens.length != 5) {
            System.out.println("Missing data tokens.");
            return;
        }

        String firstName = tokens[1];
        String lastName = tokens[2];
        String dobStr = tokens[3];
        String studioStr = tokens[4];

        Date dob = new Date(dobStr);
        if (!dob.isValid()) {
            System.out.println("DOB " + dobStr + ": invalid calendar date!");
            return;
        }

        if (dob.isTodayOrFutureDate()) {
            System.out.println("DOB " + dobStr + ": cannot be today or a future date!");
            return;
        }

        if (dob.isLessThan18(dob)) {
            System.out.println("DOB " + dobStr + ": must be 18 or older to join!");
            return;
        }

        Location studio;
        try {
            studio = Location.valueOf(studioStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(studioStr + ": invalid studio location!");
            return;
        }

        Profile newProfile = new Profile(firstName, lastName, dob);

        if (memberList.sameProfile(newProfile)) {
            System.out.println(firstName + " " + lastName + " is already in the member database.");
            return;
        }

        Date expireDate = new Date().plusMonths(3);
        Member newMember = new Family(newProfile, expireDate, studio, true);
        memberList.add(newMember);
        System.out.println(firstName + " " + lastName + " added.");

    }

    private void addPremiumMember(String[] tokens) {
        if (tokens.length != 5) {
            System.out.println("Missing data tokens.");
            return;
        }

        String firstName = tokens[1];
        String lastName = tokens[2];
        String dobStr = tokens[3];
        String studioStr = tokens[4];

        Date dob = new Date(dobStr);
        if (!dob.isValid()) {
            System.out.println("DOB " + dobStr + ": invalid calendar date!");
            return;
        }

        if (dob.isTodayOrFutureDate()) {
            System.out.println("DOB " + dobStr + ": cannot be today or a future date!");
            return;
        }

        if (dob.isLessThan18(dob)) {
            System.out.println("DOB " + dobStr + ": must be 18 or older to join!");
            return;
        }

        Location studio;
        try {
            studio = Location.valueOf(studioStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(studioStr + ": invalid studio location!");
            return;
        }

        Profile newProfile = new Profile(firstName, lastName, dob);

        if (memberList.sameProfile(newProfile)) {
            System.out.println(firstName + " " + lastName + " is already in the member database.");
            return;
        }

        Date expireDate = new Date().plusYears(1);
        Member newMember = new Premium(newProfile, expireDate, studio, 3);
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
        Date dob = new Date(tokens[3]);

        Profile cancelProfile = new Profile(firstName, lastName, dob);

        //if (memberList.sameProfile(newProfile)) {

        boolean foundAndRemoved = false;

        for (Member member : memberList.getMembers()) {
            if(member == null) {
                foundAndRemoved = false;
                break;
            }
            if (member.getProfile().equals(cancelProfile)) {
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
        // Logic to display the class schedule
        System.out.println("-Fitness classes-");
        for (FitnessClass fClass : schedule.getClasses()) {
            if (fClass != null) {
                System.out.println(fClass);
                if (fClass.getMembers().getSize() > 0) {
                    System.out.println("[Attendees]");
                    for (Member m : fClass.getMembers().getMembers()) {
                        if (m != null) {
                            System.out.println("   " + m.toString());
                        }
                    }
                }

                if (fClass.getGuests().getSize() > 0) {
                    System.out.println("[Guests]");
                    for (Member guest : fClass.getGuests().getMembers()) {
                        if (guest != null) {
                            System.out.println("   " + guest.toString());
                        }
                    }
                }
            }
        }
        System.out.println("-end of class list.");

    }

    private void printMembershipFees() {

        System.out.println("-list of members with next dues-");
        for (Member member : memberList.getMembers()) {
            if (member != null) {
                System.out.println(member.toString() + " [next due: $" + String.format("%.2f", member.bill()) + "]");
            }
        }
        System.out.println("-end of list-");

    }

    private void registerForMemberClass(String[] tokens) {
        if (tokens.length != 7) {
            System.out.println("Missing data tokens.");
            return;
        }
        String classTypeStr = tokens[1];
        String instructorStr = tokens[2];
        String studioStr = tokens[3];
        String firstName = tokens[4];
        String lastName = tokens[5];
        String dobStr = tokens[6];

        Offer classType;
        Instructor instructor;
        Location studio;
        try {
            classType = Offer.valueOf(classTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(classTypeStr + " - class name does not exist.");
            return;
        }
        try {
            instructor = Instructor.valueOf(instructorStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(instructorStr + " - instructor does not exist.");
            return;
        }
        try {
            studio = Location.valueOf(studioStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(studioStr + " - invalid studio location.");
            return;
        }

        Profile profile = new Profile(firstName, lastName, new Date(dobStr));
        if (!memberList.containsProf(profile)) {
            System.out.println(firstName + " " + lastName + " " + dobStr + " is not in the member database.");
            return;
        }

        Member member = memberList.getMember(profile);
        if (member.getExpire().isExpired()) {
            System.out.println(firstName + " " + lastName + " " + dobStr + " membership expired.");
            return;
        }

        if (member instanceof Basic && !member.getHomeStudio().equals(studio)) {
            System.out.println(firstName + " " + lastName + " is attending a class at " + studio.getName() + " - [BASIC] home studio at " + member.getHomeStudio().getName());
            return;
        }

        //Member member1 = new Member(profile, memberList.getMember(profile).getExpire(), studio);
        FitnessClass targetClass = schedule.findClass(classType, instructor, studio);


        if (targetClass == null) {
            System.out.println(classTypeStr + " by " + instructorStr + " does not exist at " + studioStr);
            return;
        }

        if (targetClass.getMembers().contains(member)) {
            System.out.println(member.getProfile().getFname() + " " + member.getProfile().getLname() + " is already in the class.");
            return;
        }

        for (FitnessClass fClass : schedule.getClasses()) {
            if (fClass != null && !fClass.equals(targetClass) && fClass.getTime().equals(targetClass.getTime()) && fClass.getMembers().contains(member)) {
                System.out.println("Time conflict - " + profile.getFname() + " " + profile.getLname() +
                        " is in another class held at " + fClass.getTime() + " - " +
                        instructor + ", " + fClass.getTime() + ", " + studio.getName());
                return;
            }
        }

        targetClass.addMember(member);
        if(member instanceof Basic) {
            ((Basic) member).addClass();
        }
        System.out.println(member.getProfile().getFname() + " " + member.getProfile().getLname() + " attendance recorded " +
                targetClass.getClassInfo() + " at " + targetClass.getStudio());

    }

    private void unregisterMemberFromClass(String[] tokens) {
        // Logic to unregister a member from a class

        if (tokens.length != 7) {
            System.out.println("Missing data tokens.");
            return;
        }

        String classTypeStr = tokens[1];
        String instructorStr = tokens[2];
        String locationStr = tokens[3];
        String fname = tokens[4];
        String lname = tokens[5];
        Date dobStr = new Date(tokens[6]);

        Profile profile = new Profile(fname, lname, dobStr); // Modify to match your actual constructor
        //Member member = new Member(profile, null, null); // Assuming a simplified Member constructor for matching

        Member member = memberList.getMember(profile);

        for (FitnessClass fClass : schedule.getClasses()) {
            if (fClass != null && fClass.getClassInfo().equals(Offer.valueOf(classTypeStr.toUpperCase())) &&
                    fClass.getInstructor().equals(Instructor.valueOf(instructorStr.toUpperCase())) &&
                    fClass.getStudio().equals(Location.valueOf(locationStr.toUpperCase()))) {

                boolean removed = fClass.removeMember(member);
                if (removed) {
                    System.out.println(profile.getFname() + " " + profile.getLname() + " is removed from " +
                            fClass.getInstructor() + ", " + fClass.getTime() + ", " +
                            fClass.getStudio());
                    return;
                }
                else {
                    System.out.println(profile.getFname() + " " + profile.getLname() + " is not in " +
                            fClass.getInstructor() + ", " + fClass.getTime() + ", " + fClass.getStudio());
                }
            }
        }

    }

    private void registerGuestForClass(String[] tokens) {
        if (tokens.length != 7) {
            System.out.println("Missing data tokens.");
            return;
        }
        String classTypeStr = tokens[1];
        String instructorStr = tokens[2];
        String locationStr = tokens[3];
        String fname = tokens[4];
        String lname = tokens[5];
        Date dobStr = new Date(tokens[6]);

        Profile profile = new Profile(fname, lname, dobStr);
        //Member guest = new Member(profile, null, null);

        //memberList.getMember(profile);

        Offer classType;
        Instructor instructor;
        Location studio;
        try {
            classType = Offer.valueOf(classTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(classTypeStr + " - class name does not exist.");
            return;
        }
        try {
            instructor = Instructor.valueOf(instructorStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(instructorStr + " - instructor does not exist.");
            return;
        }
        try {
            studio = Location.valueOf(locationStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(locationStr + " - invalid studio location.");
            return;
        }

        if (!memberList.containsProf(profile)) {
            System.out.println(fname + " " + lname + " " + dobStr + " is not in the member database.");
            return;
        }

        Member member = memberList.getMember(profile);
        if (member.getExpire().isExpired()) {
            System.out.println(fname + " " + lname + " " + dobStr + " membership expired.");
            return;
        }

        if (member instanceof Basic) {
            System.out.println(fname + " " + lname + " [BASIC] - no guest pass.");
            return;
        }

        if (!member.getHomeStudio().equals(studio)) {
            System.out.println(fname + " " + lname + " (guest) is attending a class at " + studio.getName() + " - home studio at " + member.getHomeStudio().getName());
            return;
        }

        if(member instanceof Premium && ((Premium) member).getGuestPass() <= 0) {
            System.out.println(fname + " " + lname + " guest pass not available.");
            return;
        }
        if(member instanceof Family && !((Family) member).isGuest()) {
            System.out.println(fname + " " + lname + " guest pass not available.");
            return;
        }


        for (FitnessClass fClass : schedule.getClasses()) {
            if (fClass.getClassInfo().equals(Offer.valueOf(classTypeStr.toUpperCase())) &&
                    fClass.getInstructor().equals(Instructor.valueOf(instructorStr.toUpperCase())) &&
                    fClass.getStudio().equals(Location.valueOf(locationStr.toUpperCase()))) {
                if (fClass.addGuest(member)) {
                    if(member instanceof Premium) {
                        ((Premium) member).useGuestPass();
                    }
                    if(member instanceof Family) {
                        ((Family) member).setGuest(false);
                    }
                    System.out.println(fname + " " + lname + " (guest) attendance recorded " +
                            classType + " at " +
                            fClass.getStudio());
                    return;
                }
            }
        }
        System.out.println("Could not register guest.");
    }

    private void unregisterGuestFromClass(String[] tokens) {
        if (tokens.length != 7) {
            System.out.println("Missing data tokens.");
            return;
        }

        String classTypeStr = tokens[1];
        String instructorStr = tokens[2];
        String locationStr = tokens[3];
        String fname = tokens[4];
        String lname = tokens[5];
        Date dobStr = new Date(tokens[6]);

        Profile profile = new Profile(fname, lname, dobStr);
        //Member guest = new Member(profile, null, null);


        Member guest = memberList.getMember(profile);


        Offer classType;
        try {
            classType = Offer.valueOf(classTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(classTypeStr + " - class name does not exist.");
            return;
        }


        for (FitnessClass fClass : schedule.getClasses()) {
            if (fClass.getClassInfo().equals(Offer.valueOf(tokens[1].toUpperCase())) &&
                    fClass.getInstructor().equals(Instructor.valueOf(tokens[2].toUpperCase())) &&
                    fClass.getStudio().equals(Location.valueOf(tokens[3].toUpperCase()))) {
                if (fClass.removeGuest(guest)) {
                    if(guest instanceof Premium) {
                        ((Premium) guest).addGuestPass();
                    }
                    if(guest instanceof Family) {
                        ((Family) guest).setGuest(true);
                    }
                    System.out.println(guest.getProfile().getFname() + " " + guest.getProfile().getLname() + " (guest) is removed from " +
                            fClass.getInstructor() + ", " + fClass.getTime() + ", " +
                            fClass.getStudio());
                    return;
                }
            }
        }
        //System.out.println("Could not remove guest.");
    }

}
