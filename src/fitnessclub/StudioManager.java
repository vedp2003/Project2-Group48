package fitnessclub;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
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
                memberList.load(new File("memberList.txt"));

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
        String[] tokens = command.split(" ");
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
                    System.out.println("RRR reached");
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

        //Profile newProfile = new Profile(firstName, lastName, dob);

        //if (memberList.sameProfile(newProfile)) {

        // Iterate over the member list to find and remove the member.
        boolean foundAndRemoved = false;

        for (Member member : memberList.getMembers()) {
            if(member == null) {
                foundAndRemoved = false;
                break;
            }
            if (member.getProfile().getFname().equalsIgnoreCase(firstName) && member.getProfile().getLname().equalsIgnoreCase(lastName)
            && member.getProfile().getDob().equals(dob)) {
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
        //THis method is not tested yet
        System.out.println("-Fitness classes-");
        for (FitnessClass fitnessClass : schedule.getClasses()) {
            if (fitnessClass == null)
                continue;

            // Display class info
            System.out.println(fitnessClass.getClassInfo() + " - " + fitnessClass.getInstructor() + ", " + fitnessClass.getTime() + ", " + fitnessClass.getStudio());

            // Display attendees, leveraging Member's toString() for detailed info
            System.out.println("[Attendees]");
            for (Member member : fitnessClass.getMembers().getMembers()) {
                if (member != null) {
                    System.out.println("   " + member.toString());
                }
            }
            for (Member guest : fitnessClass.getGuests().getMembers()) {
                if (guest != null) {
                    System.out.println("   " + guest.toString());
                }
            }
        }
        System.out.println("-end of class list.");
    }

    private void printMembershipFees() {
        // Logic to print membership fees
        //THis method is not tested yet

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

        FitnessClass fitnessClass = new FitnessClass(classType, instructor, studio);
        if (!schedule.contains(fitnessClass)) {
            System.out.println(classTypeStr + " by " + instructorStr + " does not exist at " + studioStr);
            return;
        }

        //CHECK THIS: figure out
        //Time conflict – member is currently in a class held at the same time.
        //Member is already added to the class schedule.

        //Then, add the member to the class

    }

    private void unregisterMemberFromClass(String[] tokens) {
        // Logic to unregister a member from a class
    }

    private void registerGuestForClass(String[] tokens) {
        // Logic to register a guest for a class
    }

    private void unregisterGuestFromClass(String[] tokens) {
        // Logic to unregister a guest from a class
    }

}
